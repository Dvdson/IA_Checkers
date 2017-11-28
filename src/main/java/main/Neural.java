package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFileChooser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class Neural {
	
	ArrayList<Double> pesos = new ArrayList<Double>();
	ArrayList<Integer> entradas;
	boolean isHyperbolic;
	File save_data;
	JsonObject data;
	
	public Neural(File json_file) {
		
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();			
		JsonParser parser = new JsonParser();
		
		
		try {			
			data = parser.parse(new FileReader(json_file)).getAsJsonObject();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e){
			
		}
		
		if(JsonChecker.isOK(data)){
			pesos = gson.fromJson(data.get("pesos"), ArrayList.class);
		}else{
			data.add("memory", new JsonArray());
			data.add("pesos", new JsonArray(33));
			for (int i = 0; i < 33; i++) {
				Random r = new Random();
				pesos.add(r.nextDouble());
				data.get("pesos").getAsJsonArray().add(pesos.get(i));
			}
			
		}
		
		save_data = json_file;
		
		
	}
	

	
	double soma(ArrayList<Integer> entradas) {
		double soma = 0;
		
		for(int i = 0; i < pesos.size(); i++) {
			soma += pesos.get(i)*((double)entradas.get(i));
		}
		
		return soma;
	}
	
	double funcaoAtivacao(double soma) {
		if(isHyperbolic)
			return Math.tanh(soma);
		
		return soma;	
	}
	
	double derF(double soma) {
		if(isHyperbolic)
			return 1 - Math.tanh(soma);
		
		return 1;
	}
	
	//Treina a rede para um padrao dado uma saida desejada:
	void execTreinamento(Integer saidaDesejada) {
		double coefic = 0.3;
		double tolerancia = 0.5;
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		JsonObject aux = new JsonObject();
		
		aux.add("input", new JsonArray());
		for (int i = 0; i < entradas.size(); i++) {
			aux.get("input").getAsJsonArray().add(entradas.get(i));
		}
		aux.addProperty("output", saidaDesejada);
		
		Integer input_pos = JsonChecker.hasInput(data.get("memory").getAsJsonArray(), aux);
		
		if(input_pos < data.get("memory").getAsJsonArray().size()){
			data.get("memory").getAsJsonArray().remove(input_pos);
		}
		
		data.get("memory").getAsJsonArray().add(aux);
		
		Integer trainning_size = data.get("memory").getAsJsonArray().size();

		for (int count = trainning_size - 1; count < trainning_size;) {
			ArrayList<Integer> entrada = gson.fromJson(data.get("memory").getAsJsonArray().get(count).getAsJsonObject().get("input").getAsJsonArray(),ArrayList.class);
			double soma = soma(entrada);
			double y = funcaoAtivacao(soma);
			
			boolean not_expected = false;
			
			while(y >= saidaDesejada + tolerancia || y <= saidaDesejada - tolerancia) {
				
				//TODO realizar treinamento para todas as entradas em data.get("memory").
				//Se a saida quantizada for diferente da desejada, atualiza os pesos:
				for(int i = 0; i < pesos.size(); i++) {
					pesos.set(i, pesos.get(i) + coefic*(saidaDesejada - y)*entradas.get(i)*derF(soma));
				}
				
				soma = soma(entrada);
				y = funcaoAtivacao(soma);
				System.out.println("treinando");
				not_expected = true;
				
			}
			count++;
			
			if (not_expected) count = 0;
			
			
		}
		
		
	}
	
	public void save(){
		try {
			
			PrintWriter file = new PrintWriter(save_data);
			file.print(new GsonBuilder().setPrettyPrinting().create().toJson(data));
			file.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Retorna a saida da rede para o padrao de entrada:
	double exec() {
		
		double soma = soma(this.entradas);
		double y = funcaoAtivacao(soma);
		
		return y;
	}


}
