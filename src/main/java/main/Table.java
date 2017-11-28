package main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class Table {

	
	ArrayList<Square> B_squares;
	ArrayList<JLabel> W_squares;
	Integer chosed = -1;
	
	public Table() {
	
		B_squares= new ArrayList<Square>();
		W_squares= new ArrayList<JLabel>();
		// TODO Auto-generated constructor stub
	}
	
	public void initialize(JPanel panel){
		
		
		B_squares.ensureCapacity(64);
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if((i+j)%2 == 1) {
					B_squares.add(new Square(j, i,"B_square.png ", "W_square.png", this));
					panel.add(B_squares.get(B_squares.size()-1).label, "cell "+Integer.toString(j)+" "+Integer.toString(i)+"");
				}else{
					W_squares.add(new JLabel());
					W_squares.get(W_squares.size()-1).setIcon(new ImageIcon("W_square.png"));
					panel.add(W_squares.get(W_squares.size()-1),"cell "+Integer.toString(j)+" "+Integer.toString(i));
				}
				
			}
		}
		
		
	}
	
	public void movePiece(int pos, int next){
		int piece = this.B_squares.get(pos).piece;
		ArrayList<Integer> sq_around = this.B_squares.get(pos).sq_around;
		this.B_squares.get(pos).setPiece(0);
		this.B_squares.get(next).setPiece(piece);
		//atualiza a posicao atual da peca
		pos = next;
		boolean hasSq_around = true;
		
		//Se e a vez do adversario
		if(piece == -1) {
			
			while(hasSq_around) {
				hasSq_around = false;
				//percorre as posicoes que o adversario pode se mover
				for(int i = 2; i < sq_around.size(); i++) {
					//se tem peca do jogador come a peca
					if(sq_around.get(i) > 0) {
						this.B_squares.get(pos).setPiece(0);
						this.B_squares.get(i).setPiece(piece);
						//atualiza a posicao atual da peca
						pos = i;
						hasSq_around = true;
					}
					
				}
			}
		}
		//Se e a vez do adversario e e uma rainha
		else if(piece == -2) {
			
			while(hasSq_around) {
				hasSq_around = false;
				//percorre as posicoes que o adversario pode se mover
				for(int i = 0; i < sq_around.size(); i++) {
					//se tem peca do jogador come a peca
					if(sq_around.get(i) > 0) {
						this.B_squares.get(pos).setPiece(0);
						this.B_squares.get(i).setPiece(piece);
						//atualiza a posicao atual da peca
						pos = i;
						hasSq_around = true;
					}
					
				}
			}
		}
		//Se e a vez do jogador
		else if (piece == 1) {
			
			while(hasSq_around) {
				hasSq_around = false;
				//percorre as posicoes que o jogador pode se mover
				for(int i = 1; i >= 0; i--) {
					//se tem peca do adversario come a peca
					if(sq_around.get(i) < 0) {
						this.B_squares.get(pos).setPiece(0);
						this.B_squares.get(i).setPiece(piece);
						//atualiza a posicao atual da peca
						pos = i;
						hasSq_around = true;
					}
					
				}
			}
		}
		//Se e a vez do jogador e e uma rainha
		else if (piece == 2) {
			
			while(hasSq_around) {
				hasSq_around = false;
				//percorre as posicoes que o jogador pode se mover
				for(int i = 0; i < sq_around.size(); i++) {
					//se tem peca do adversario come a peca
					if(sq_around.get(i) < 0) {
						this.B_squares.get(pos).setPiece(0);
						this.B_squares.get(i).setPiece(piece);
						//atualiza a posicao atual da peca
						pos = i;
						hasSq_around = true;
					}
					
				}
			}
		}

	}
	
	public ArrayList<Integer> pieceTable(int side){
		ArrayList<Integer> P_table = new ArrayList<Integer>();
		for (int i = 0; i < B_squares.size(); i++) {
			P_table.add(B_squares.get(i).piece*side);
		}
		return P_table;
	}
	
	public void move(Integer piece, Integer move ){
		B_squares.get(piece).swap(B_squares.get(move));
	}
	
	void loadGame(){
		for (int i = 0; i < 32; i++) {
			if(i < 12)B_squares.get(i).setPiece(1);
			if(i >= 12 && i < 20)B_squares.get(i).setPiece(0);
			if(i >= 20)B_squares.get(i).setPiece(-1);
		}
	}
	
	public int winner(){
		int p_a = 0, p_b = 0;
		for (Square square : B_squares) {
			if(square.piece == 1) p_a = 1;
			if(square.piece == -1) p_b = -1;
		}
		
		return p_a + p_b;
	}
	
}
