package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFileChooser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

public class Agente1 {
	
	Neural net;

	Agente1() {
		ArrayList<Double> pesos = new ArrayList<Double>();
		
		JFileChooser json_Opener = new JFileChooser(System.getProperty("user.dir"));
		json_Opener.showOpenDialog(null);
		Gson json_file = new GsonBuilder().setPrettyPrinting().create();			
		JsonParser parser = new JsonParser();
		JsonArray W = new JsonArray();
		try {
			W = parser.parse(new FileReader(json_Opener.getSelectedFile())).getAsJsonArray();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		if(isOK(W)){
			pesos = json_file.fromJson(W, ArrayList.class);
		}else{
			for (int i = 0; i < 33; i++) {
				Random r = new Random();
				pesos.add(r.nextDouble());
			}
		}

		net = new Neural(false, pesos);
		// TODO Auto-generated constructor stub
	}
	
	public boolean isOK(JsonArray W){
		if(W.size() != 33) return false;
		
		for (int i = 0; i < W.size(); i++) {
			if(!W.get(i).getAsJsonPrimitive().isNumber()) return false;
		}
		
		return true;
	}
	
	public Integer sensor( Table table ){
		net.entradas = table.pieceTable();
		
		Integer saida = rounding(net.exec());
		
		return saida;
	}
	
	int rounding(double number){
		
		int x = (int) number;
		
		if(number - x < 0.5) return x;
		return x+1;
		
	}
	
}
