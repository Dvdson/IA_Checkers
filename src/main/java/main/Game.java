package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JButton;

public class Game implements Runnable {

	Table table;
	Agente A;
	Agente B;
	JButton btn1;
	JButton btn2;
	MouseClickDetection MCD;
	boolean isTrainning;
	Object lock;
	
	public Game(Table table, Agente a, Agente b, boolean trainning, JButton btn1, JButton btn2) {
		super();
		this.table = table;
		this.A = a;
		this.B = b;
		this.btn1 = btn1;
		this.btn2 = btn2;
		isTrainning = trainning;
		lock = new Object();
		//this.lock = true;
	}
	
//	public void unlock() {
//		this.lock = false;
//	}
//	
//	public void lock() {
//		this.lock = true;
//	}
	
	public void run() {
		
		if(isTrainning) trainning();
		else play();
		
		btn1.setEnabled(true);
		btn2.setEnabled(true);
	}
	
	public void play(){
			
		table.loadGame();
		
		int player = 0;
		while(true){
			if(player%2 == 0){
				ArrayList<Integer> play  = A.play(table);
				System.out.println(play.toString());
				if(play.equals(new ArrayList<Integer>(Arrays.asList(100,100,100)))){
					table.randomDelete(A.side);
					if(table.winner() == 1) break;
				}else{
					table.movePiece(play.get(1), play.get(2));
					if(table.winner() == -1) break;
				}
				
			}else{
				ArrayList<Integer> play  = B.play(table);
				System.out.println(play.toString());
				if(play.equals(new ArrayList<Integer>(Arrays.asList(100,100,100)))){
					table.randomDelete(B.side);
				}else{
					table.movePiece(play.get(1), play.get(2));
				}
				
			}
			
			++player;
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
	}
	
	public void trainning(){
		table.loadGame();
		
		int player = 0;
		while(true){
			System.out.println("antes");
			

			if(player%2 == 0){
				
				synchronized(this) {
					System.out.println("aaa");
					try {
						this.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//while(lock);
					System.out.println("bbb");

				}
				
				//TODO fazer a thread esperar clickar em um quadrado
				ArrayList<Integer> play  = A.train(table);
				System.out.println(play.toString());
				if(play.equals(new ArrayList<Integer>(Arrays.asList(100,100,100)))){
					table.randomDelete(A.side);

				}else{
					table.movePiece(play.get(1), play.get(2));

				}

			}else{
				ArrayList<Integer> play  = B.play(table);
				System.out.println(play.toString());
				if(play.equals(new ArrayList<Integer>(Arrays.asList(100,100,100)))){
					table.randomDelete(B.side);
				}else{
					table.movePiece(play.get(1), play.get(2));
				}
				
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			++player;
			//this.lock();

			
		}
		
	}

}
