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
					B_squares.add(new Square(i, j,"B_square.png ", "W_square.png"));
					panel.add(B_squares.get(B_squares.size()-1).label, "cell "+Integer.toString(i)+" "+Integer.toString(j)+"");
				}else{
					W_squares.add(new JLabel());
					W_squares.get(W_squares.size()-1).setIcon(new ImageIcon("W_square.png"));
					panel.add(W_squares.get(W_squares.size()-1),"cell "+Integer.toString(i)+" "+Integer.toString(j));
				}
				
			}
		}
		
		
	}
	
	public ArrayList<Integer> pieceTable(){
		ArrayList<Integer> P_table = new ArrayList<Integer>();
		for (int i = 0; i < B_squares.size(); i++) {
			P_table.add(B_squares.get(i).piece);
		}
		return P_table;
	}
	
}
