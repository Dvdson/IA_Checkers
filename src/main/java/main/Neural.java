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
import com.google.gson.JsonArray;
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
		JsonObject W = new JsonObject();
		try {			
			W = parser.parse(new FileReader(json_file)).getAsJsonObject();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e){
			
		}
		
		if(JsonChecker.isOK(W)){
			pesos = gson.fromJson(W, ArrayList.class);
		}else{
			for (int i = 0; i < 33; i++) {
				Random r = new Random();
				pesos.add(r.nextDouble());
			}
			
		}
		
		save_data = json_file;
		
		
	}
	

	
	double soma() {
		double soma = 0;
		
		for(int i = 0; i < pesos.size(); i++) {
			soma += pesos.get(i)*entradas.get(i);
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
	void execTreinamento(double saidaDesejada) {
		double coefic = 0.3;
		double tolerancia = 0.5;
		double soma = soma();
		double y = funcaoAtivacao(soma);
		
		while(y >= saidaDesejada + tolerancia || y <= saidaDesejada - tolerancia) {
			
			//Se a saida quantizada for diferente da desejada, atualiza os pesos:
			for(int i = 0; i < pesos.size(); i++) {
				pesos.set(i, pesos.get(i) + coefic*(saidaDesejada - y)*entradas.get(i)*derF(soma));
			}
			
			soma = soma();
			y = funcaoAtivacao(soma);
			System.out.println("treinando");

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
		
		double soma = soma();
		double y = funcaoAtivacao(soma);
		
		return y;
	}


}
