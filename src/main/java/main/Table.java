package main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class Table extends Thread{

	
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
		
		ArrayList<Integer> pieces_to_erase = new ArrayList<Integer>();
		
		int atual_pos = next;
		int piece_a = this.B_squares.get(pos).piece;
		int piece_b = this.B_squares.get(next).piece;
		
		pieces_to_erase.add(pos);
	
		
		if(piece_b*piece_a < 0){
			int diag = diagDir(pos, next);
			int back_diag = counterDiag(diag);
			pieces_to_erase.add(next);
			atual_pos = B_squares.get(atual_pos).sq_around.get(diag);
			boolean can_move = true;
			
			while(can_move && atual_pos!=-1){
				int count = 0;
				for (Integer i : B_squares.get(atual_pos).sq_around) {
					
					if(i!=-1 && count != back_diag){
						if(B_squares.get(i).piece*piece_a < 0 && B_squares.get(i).sq_around.get(count) != -1){
							Integer may_pos = B_squares.get(i).sq_around.get(count);
							if(B_squares.get(may_pos).piece == 0){
								atual_pos = B_squares.get(i).sq_around.get(count);
								diag = count;
								back_diag = counterDiag(count);
								pieces_to_erase.add(i);
								break;
							}
						}
					}
					count++;
				}
				if(count>=4) can_move = false;
			}
			
		}
		else if((piece_b > 0 && piece_a > 0) || (piece_b < 0 && piece_a < 0)){}//movimento illegal
		
		erasing(pieces_to_erase);
		if(piece_a == 1 && atual_pos >= 28)B_squares.get(atual_pos).setPiece(2);
		else if(piece_a == -1 && atual_pos <= 3)B_squares.get(atual_pos).setPiece(-2);
		else B_squares.get(atual_pos).setPiece(piece_a);
	}
	
	private void erasing(ArrayList<Integer> pieces_to_erase){
		for (Integer piece : pieces_to_erase) {
			B_squares.get(piece).setPiece(0);
		}
	}
	
	private int counterDiag(int diag){
		if(diag == 0) return 3;
		if(diag == 1) return 2;
		if(diag == 2) return 1;
		if(diag == 3) return 0;
		return -1;
	}
	
	private int diagDir(int A, int B){
		
		Square aux = B_squares.get(A);
		
		for (int i = 0; i < 4; i++) {
			int next = aux.sq_around.get(i);
			while(next != -1){
				if(B_squares.get(next).piece ==  0) {	
					aux = B_squares.get(next);
					next = B_squares.get(next).sq_around.get(i);
				}
				else if(aux.sq_around.get(i) == B) return i;
				else next = -1;
			}
			aux = B_squares.get(A);
		}
		
		return -1;
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
			if(square.piece > 0) p_a = 1;
			if(square.piece < 0) p_b = -1;
		}
		
		return p_a + p_b;
	}
	
	public void randomDelete(int side){
		for (Square square : B_squares) {
			if(square.piece*side == 1){
				square.setPiece(0);
				return;
			}
		}
		for (Square square : B_squares) {
			if(square.piece*side == 2){
				square.setPiece(0);
				return;
			}
		}
	}
}
