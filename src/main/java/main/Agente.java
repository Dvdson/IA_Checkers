package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
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

public class Agente {
	
	Neural net;
	Integer side;
	

	Agente( Integer side) {
		
		this.side = side;
		ArrayList<Double> pesos = new ArrayList<Double>();
		
		JFileChooser json_Opener = new JFileChooser(System.getProperty("user.dir"));
		String player;
		
		if (side == 1) player = "Jogador";
		else player = "Adiversário";
		
		json_Opener.setDialogTitle("Escolha seu "+ player +" para a partida");
		
		json_Opener.showOpenDialog(null); 

		net = new Neural();
		save();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public Integer sensor( Table table ){
		
		net.entradas = table.pieceTable(side);
		net.entradas.add(1); //bias
		
		Integer saida = rounding(net.exec());
		
		
		
		return saida;
	}
	
	public void save(){
		net.save();
	}
	
	public ArrayList<Integer> pieceToMove(Table table, Integer choice){
		
		ArrayList<Integer> qt_moves = new ArrayList<Integer>(
				Arrays.asList(100,100,100,100,100,100,100,100,
							  100,100,100,100,100,100,100,100,
							  100,100,100,100,100,100,100,100,
							  100,100,100,100,100,100,100,100));
		
		ArrayList<Integer> previous = new ArrayList<Integer>(
				Arrays.asList(-1,-1,-1,-1,-1,-1,-1,-1,
						  	  -1,-1,-1,-1,-1,-1,-1,-1,
						  	  -1,-1,-1,-1,-1,-1,-1,-1,
						  	  -1,-1,-1,-1,-1,-1,-1,-1));
		
		ArrayList<Integer> to_analise = new ArrayList<Integer>();
		ArrayList<Integer> myPieces = new ArrayList<Integer>();
		
		qt_moves.set(choice, 0); qt_moves.set(choice, choice); to_analise.add(choice);
		
		//Algoritmo de Djikstra
		for (int i = 0; i < to_analise.size(); i++) {
			
			for (int j : table.B_squares.get(i).sq_around) {
				if(j >= 0){
					if(!to_analise.contains(j)){
						qt_moves.set(j, i+1);
						previous.set(j, to_analise.get(i));
						if(table.B_squares.get(j).piece == 0){
							to_analise.add(j);
						}else if(table.B_squares.get(j).piece*side > 0){
							myPieces.add(j);
						}
					}
				}
			}
		}
		
		ArrayList<Integer> minor = new ArrayList<Integer>(Arrays.asList(100,100,100));
		
		if(!myPieces.isEmpty()){
			for (Integer myPiece : myPieces) {
				if(myPiece*side == 1){//caso seja normal
					if((table.B_squares.get(previous.get(myPiece)).Y - table.B_squares.get(myPiece).Y)*side < 0){}
					else if(qt_moves.get(myPiece) < minor.get(0)){
						minor.set(0, qt_moves.get(myPiece));
						minor.set(1, myPiece);
						minor.set(2, previous.get(myPiece));
					}
				}else if (myPiece*side == 2){//caso seja uma rainha
					ArrayList<Integer> moves = diagonal(table.B_squares, myPiece);
					if(moves.contains(choice)){
						minor.set(0, 1);
						minor.set(1, myPiece);
						minor.set(2, choice);
					}else{//caso precise de duas rodadas
						boolean notFound = true;
						for (Integer nextPos : moves) {
							if(notFound){
								ArrayList<Integer> moves2 = diagonal(table.B_squares, nextPos);
								if(moves2.contains(choice)){
									minor.set(0, 2);
									minor.set(1, myPiece);
									minor.set(2, nextPos);
								}
							}
						}
					}
				}
			}				
		}

		if(minor.equals(new ArrayList<Integer>(Arrays.asList(100,100,100)))){
			
			for (int i=0 ; i < table.B_squares.size(); ++i) {
				
				if(table.B_squares.get(i).piece*side > 0){
					
					Integer u_left = table.B_squares.get(i).sq_around.get(0);
					Integer u_right = table.B_squares.get(i).sq_around.get(1);
					Integer d_left = table.B_squares.get(i).sq_around.get(2);
					Integer d_right = table.B_squares.get(i).sq_around.get(3);

					
					if ((side == -1 || table.B_squares.get(i).piece*side == 2)) {
						if(u_left>=0){
							if(table.B_squares.get(u_left).piece == 0){
								minor.set(0, 1);
								minor.set(1, i);
								minor.set(2, u_left);
								break;
							}
							if(table.B_squares.get(u_left).piece*side < 0){
								if(table.B_squares.get(u_left).sq_around.get(0)!=-1){
									if(table.B_squares.get(table.B_squares.get(u_left).sq_around.get(0)).piece == 0){
										minor.set(0, 1);
										minor.set(1, i);
										minor.set(2, u_left);
										break;
									}
								}
							}
						}
						if(u_right>=0){
							if(table.B_squares.get(u_right).piece == 0){
								minor.set(0, 1);
								minor.set(1, i);
								minor.set(2, u_right);
								break;
							}
							if(table.B_squares.get(u_right).piece*side < 0){
								if(table.B_squares.get(u_right).sq_around.get(1)!=-1){
									if(table.B_squares.get(table.B_squares.get(u_right).sq_around.get(1)).piece == 0){
										minor.set(0, 1);
										minor.set(1, i);
										minor.set(2, u_right);
										break;
									}
								}
							}
						}
					}
					if ((side == 1 || table.B_squares.get(i).piece*side == 2)) {
						if(d_left>=0){
							if(table.B_squares.get(d_left).piece == 0){
								minor.set(0, 1);
								minor.set(1, i);
								minor.set(2, d_left);
								break;
							}
							if(table.B_squares.get(d_left).piece*side < 0){
								if(table.B_squares.get(d_left).sq_around.get(2)!=-1){
									if(table.B_squares.get(table.B_squares.get(d_left).sq_around.get(2)).piece == 0){
										minor.set(0, 1);
										minor.set(1, i);
										minor.set(2, d_left);
										break;
									}
								}
							}
						}
						if(d_right>=0){
							if(table.B_squares.get(d_right).piece == 0){
								minor.set(0, 1);
								minor.set(1, i);
								minor.set(2, d_right);
								break;
							}
							if(table.B_squares.get(d_right).piece*side < 0){
								if(table.B_squares.get(d_right).sq_around.get(3)!=-1){
									if(table.B_squares.get(table.B_squares.get(d_right).sq_around.get(3)).piece == 0){
										minor.set(0, 1);
										minor.set(1, i);
										minor.set(2, d_right);
										break;
									}
								}
							}
						}
					}
						
				}
			}
		}
		
		return minor;
		
	}
	
	ArrayList<Integer> diagonal(ArrayList<Square> tb,Integer x){
		ArrayList<Integer> dig_pos = new ArrayList<Integer>();
		for (int i = 0; i < 4; i++) {
			Square aux = tb.get(x);
			
			while(aux.sq_around.get(i) != -1 && aux.piece*side == 0){
				if(tb.get(aux.sq_around.get(i)).piece < 0){
					if (tb.get(aux.sq_around.get(i)).sq_around.get(i) == 0) {
						dig_pos.add(aux.sq_around.get(i));						
					}
				}else dig_pos.add(aux.sq_around.get(i));
			}
		}
		return dig_pos;
	}
	
	int rounding(double number){
		
		int x = (int) number;
		
		if(number - x < 0.5) return x;
		return x+1;
		
	}
	
}
