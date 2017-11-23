package main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Square {

	Integer piece;
	Integer X;
	Integer Y;
	JLabel label;
	Icon base_image;
	Icon alter_image;
	
	public Square(int x, int y, String img1, String img2){
		X = x;
		Y = y;
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
		});
	}
	
	
	
}
