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
	
	public void movePiece(int X, int Y, int X1, int Y1){
		
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
