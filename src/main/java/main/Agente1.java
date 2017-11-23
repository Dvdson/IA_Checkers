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
	
	public double sensor( Table table ){
		ArrayList entradas = table.getEntradas();
		net.entradas = entradas;
		
		double saida = net.exec();
		
		return saida;
	}
	
	
	
}
