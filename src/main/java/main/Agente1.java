package main;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Agente1 {
	
	Neural net;

	Agente1(Neural net) {
		this.net = net;
		// TODO Auto-generated constructor stub
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
