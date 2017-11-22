package main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class Table {

	
	ArrayList<Square> squares;
	
	public Table() {
	
		squares = new ArrayList<Square>();
		// TODO Auto-generated constructor stub
	}
	
	public void initialize(JPanel panel){
		
		squares= new ArrayList<Square>();
		squares.ensureCapacity(64);
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if((i+j)%2 == 1) {
					squares.add(new Square(i, j,"B_square.png ", "W_square.png"));
				
					
				}else{
					squares.add(new Square(i, j,"W_square.png", "B_square.png "));
				}
				panel.add(squares.get(i*8+j).label, "cell "+Integer.toString(i)+" "+Integer.toString(j)+"");
			}
		}
		
		
	}
	
}
