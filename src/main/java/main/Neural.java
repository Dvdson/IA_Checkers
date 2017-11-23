package main;

import java.util.ArrayList;


public class Neural {
	
	ArrayList<Double> pesos = new ArrayList<Double>();
	ArrayList<Integer> entradas;
	boolean isHyperbolic;
	
	public Neural(boolean isHyperbolic) {
		this.isHyperbolic = isHyperbolic;
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
		double coefic = 0.5;
		double tolerancia = 0.01;
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
	
	//Retorna a saida da rede para o padrao de entrada:
	double exec() {
		
		double soma = soma();
		double y = funcaoAtivacao(soma);
		
		return y;
	}

}
