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
		
		JFileChooser json_Opener = new JFileChooser(System.getProperty("user.dir"));
		String player;
		
		if (side == 1) player = "Jogador";
		else player = "Adiversário";
		
		json_Opener.setDialogTitle("Escolha seu "+ player +" para a partida");
		
		json_Opener.showOpenDialog(null); 

		net = new Neural(json_Opener.getSelectedFile());
		save();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public ArrayList<Integer> play( Table table ){
		
		net.entradas = table.pieceTable(side);
		net.entradas.add(1); //bias
		
		Integer saida = rounding(net.exec());
		
		return pieceToMove(table,saida);
		
		
	}
	
	public ArrayList<Integer> train( Table table ){
		
		net.entradas = table.pieceTable(side);
		net.entradas.add(1); //bias
		
		//TODO escolher quadrado aqui aqui
		net.execTreinamento(table.chosed);
		
		return pieceToMove(table,table.chosed);
		
		
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
		ArrayList<Integer> my_pieces_to_move = new ArrayList<Integer>();
		
		qt_moves.set(choice, 0); qt_moves.set(choice, choice); to_analise.add(choice);
		
		//Algoritmo de Djikstra
		for (int i = 0; i < to_analise.size(); i++) {
			
			for (int j : table.B_squares.get(to_analise.get(i)).sq_around) {
				if(j >= 0){
					if(!to_analise.contains(j)){	
						previous.set(j, to_analise.get(i));
						if(table.B_squares.get(j).piece == 0){
							qt_moves.set(j, (i+1)*10);
							to_analise.add(j);
						}else if(table.B_squares.get(j).piece*side > 0){
							qt_moves.set(j, (i+1)*10);
							my_pieces_to_move.add(j);
						}else{
							int next_move_around = table.B_squares.get(i).sq_around.indexOf(j);
							boolean can_move;
							
							if(next_move_around >= 0) can_move = ( table.B_squares.get(j).sq_around.get(next_move_around) > 0 ) ;
							else can_move = false;
							
							if(can_move){
								qt_moves.set(j, 1);
								int next_around = table.B_squares.get(j).sq_around.get(next_move_around);
								if (table.B_squares.get(next_around).piece == 0) {
									my_pieces_to_move.add(j);									
								}
							}
						}
					}
				}
			}
		}
		
//		System.out.println(qt_moves);
//		System.out.println(previous);
		
		ArrayList<Integer> minor_move = new ArrayList<Integer>(Arrays.asList(100,100,100));
		
		if(!my_pieces_to_move.isEmpty()){
			for (Integer myPiece : my_pieces_to_move) {
				//caso seja normal
				if(table.B_squares.get(myPiece).piece*side == 1){
					
					boolean isBehind = ((table.B_squares.get(previous.get(myPiece)).Y - table.B_squares.get(myPiece).Y)*side) > 0;
					boolean same_piece = table.B_squares.get(previous.get(myPiece)).piece*table.B_squares.get(myPiece).piece > 0;
					
					if(!same_piece && isBehind && qt_moves.get(myPiece) < minor_move.get(0)){
						
						setMinor(minor_move, qt_moves.get(myPiece), myPiece, previous.get(myPiece));
						
					}
				//caso seja uma rainha
				}else if (table.B_squares.get(myPiece).piece*side == 2){
					ArrayList<Integer> moves = diagonal_queen(table.B_squares, myPiece);
					
					if (moves.contains(-side) || moves.contains(-side*2)) {
						
						Integer enemy1 = moves.indexOf(-side);
						Integer enemy2 = moves.indexOf(-side);
						
						if(enemy2 >= 0){
							setMinor(minor_move, 1, myPiece, enemy2);
						}else{
							setMinor(minor_move, 1, myPiece, enemy1);
						}
						
					}else if(moves.contains(choice)){
						
						setMinor(minor_move, 10, myPiece, choice);
						
					//caso precise de duas rodadas
					}else{
						boolean notFound = true;
						for (Integer nextPos : moves) {
							if(notFound){
								ArrayList<Integer> moves2 = diagonal_queen(table.B_squares, nextPos);
								if(moves2.contains(choice)){
									setMinor(minor_move, 20, myPiece, nextPos);
								}
							}
						}
					}
				}
			}				
		}

		if(minor_move.equals(new ArrayList<Integer>(Arrays.asList(100,100,100)))){
			

			for (int i=0 ; i < table.B_squares.size(); ++i) {
				
				if(table.B_squares.get(i).piece*side == 1){
					for (int j = 0; j < 4; j++) {
						Integer diag_square = table.B_squares.get(i).sq_around.get(j);
						
						if((diag_square >= 0) && ( ((diag_square - i)*side > 0) ) ){
							if(isnt_same_team(table, i, diag_square) ){
								
								Integer next_diag_square = table.B_squares.get(diag_square).sq_around.get(j);
								boolean vrfy_square = next_diag_square >= 0;
								
								if(vrfy_square)next_diag_square = table.B_squares.get(next_diag_square).piece;
								
								if(next_diag_square == 0 && vrfy_square){
									setMinor(minor_move, 1, i, diag_square);
									//in_loop = false;
								}
							}else if(table.B_squares.get(diag_square).piece == 0 && minor_move.get(0) > 1){
								setMinor(minor_move, 10, i, diag_square);
								//in_loop = false;
							}
						}
					}
				}else if(table.B_squares.get(i).piece*side == 2){
					ArrayList<Integer> moves = diagonal_queen(table.B_squares, i);
					
					for (Integer move : moves) {
						
						if(table.B_squares.get(move).piece*side < 0){
							setMinor(minor_move, 1, i, move);
						}else if(minor_move.get(0)>10){
							setMinor(minor_move, 20, i, move);
						}
					}
					
									
				}
			}
		}
		
		return minor_move;
		
	}
	
	private boolean isnt_same_team(Table table, Integer A, Integer B){
		return table.B_squares.get(A).piece*table.B_squares.get(B).piece < 0;
	}
	
	private void setMinor(ArrayList<Integer> minor, Integer qt, Integer atual, Integer next){
		minor.set(0, qt);
		minor.set(1, atual);
		minor.set(2, next);
	}
	
	ArrayList<Integer> diagonal_queen(ArrayList<Square> tb,Integer x){
		ArrayList<Integer> dig_pos = new ArrayList<Integer>();
		for (int i = 0; i < 4; i++) {
			Square aux = tb.get(x);
			boolean first = true;
			
			while(aux.sq_around.get(i) != -1 && (aux.piece == 0 || first)){
				if(tb.get(aux.sq_around.get(i)).piece*side < 0){
					int next_pos = tb.get(aux.sq_around.get(i)).sq_around.get(i);
					if ( next_pos >= 0 ) {
						if ( tb.get(next_pos).piece == 0 ) {
							dig_pos.add(aux.sq_around.get(i));	
						}
					}
				}else if(tb.get(aux.sq_around.get(i)).piece*side == 0) 
					dig_pos.add(aux.sq_around.get(i));
				
				aux = tb.get(aux.sq_around.get(i));
				first = false;
			}
		}
		return dig_pos;
	}
	
	
	int rounding(double number){
		if(number > 31) number = 31;
		if(number < 0) number = 0;
		
		int x = (int) number;
		
		if(number - x < 0.5) return x;
		return x+1;
		
	}
	
}
