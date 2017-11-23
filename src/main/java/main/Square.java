package main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Square {

	Integer piece;
	Integer X;
	Integer Y;
	static boolean to_chose;
	ArrayList<Integer> sq_around;
	JLabel label;
	Icon base_image;
	Icon alter_image;
	Table table;
	
	public Square(int x, int y, String img1, String img2, final Table table){
		this.table = table;
		to_chose=false;
		piece = 0;
		X = x;
		Y = y;
		
		if(x-1 >= 0 && y-1 >= 0) sq_around.add((x-1)*4 + (y-1)/2);
		if(x-1 >= 0 && y+1 < 8) sq_around.add((x-1)*4 + (y+1)/2);
		if(x+1 < 8 && y-1 >= 0) sq_around.add((x+1)*4 + (y-1)/2);
		if(x+1 < 8 && y+1 < 8) sq_around.add((x+1)*4 + (y+1)/2);
		
		label = new JLabel();
		base_image = new ImageIcon(img1);
		alter_image = new ImageIcon(img2);
		label.setIcon(base_image);
		
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				label.setIcon(alter_image);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				label.setIcon(base_image);
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(to_chose){
					table.chosed =(int) X*4 - Y/2;
				}
			}
		});
	}
	
	
	
}
