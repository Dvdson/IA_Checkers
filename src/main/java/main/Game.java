package main;

import java.util.ArrayList;
import java.util.Arrays;

public class Game implements Runnable {

	Table table;
	Agente A;
	Agente B;
	boolean isTrainning;
	
	public Game(Table table, Agente a, Agente b, boolean trainning) {
		super();
		this.table = table;
		this.A = a;
		this.B = b;
		isTrainning = trainning;
	}
	
	public void run() {
		
		if(isTrainning) trainning();
		else play();
		
	}
	
	public void play(){
		
		
		table.loadGame();
		
		int player = 0;
		while(table.winner() == 0){
			if(player%2 == 0){
				ArrayList<Integer> play  = A.play(table);
				if(play.equals(new ArrayList<Integer>(Arrays.asList(100,100,100)))){
					table.randomDelete(A.side);
				}else{
					table.movePiece(play.get(1), play.get(2));
				}
				System.out.println(play.toString());
			}else{
				ArrayList<Integer> play  = B.play(table);
				if(play.equals(new ArrayList<Integer>(Arrays.asList(100,100,100)))){
					table.randomDelete(B.side);
				}else{
					table.movePiece(play.get(1), play.get(2));
				}
				System.out.println(play.toString());
			}
			
			++player;
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
	}
	
	public void trainning(){
		table.loadGame();
		
		int player = 0;
		while(table.winner() == 0){
			if(player%2 == 0){
				A.play(table);
				
			}else{
				B.play(table);
			}
			
			++player;
		}
	}
	
}
