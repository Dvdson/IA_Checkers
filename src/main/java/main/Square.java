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
	ArrayList<Integer> sq_around;
	JLabel label;
	Icon base_image;
	Icon alter_image;
	Table table;
	
	public Square(int x, int y, String img1, String img2, final Table table){
		this.table = table;
		piece = 0;
		X = x;
		Y = y;
		sq_around = new ArrayList<Integer>();
		
		if(x-1 >= 0 && y-1 >= 0) sq_around.add((x-1)/2 + (y-1)*4); else sq_around.add(-1);
		if(x+1 < 8 && y-1 >= 0) sq_around.add((x+1)/2 + (y-1)*4); else sq_around.add(-1);
		if(x-1 >= 0 && y+1 < 8) sq_around.add((x-1)/2 + (y+1)*4); else sq_around.add(-1);
		if(x+1 < 8 && y+1 < 8) sq_around.add((x+1)/2 + (y+1)*4); else sq_around.add(-1);
		
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
					table.chosed =(int) X*4 - Y/2;
			}
		});
	}
	
	public void swap(Square sq){
	
		Integer aux_piece = sq.piece;
		sq.piece = piece;
		piece = aux_piece;
		
		Icon aux_base = sq.base_image;
		sq.base_image = base_image;
		base_image = aux_base;
		
		Icon aux_alter = sq.alter_image;
		sq.alter_image = alter_image;
		alter_image = aux_alter;
		
	}
	
	public void setPiece(Integer p){
		piece = p;
		if(p == 1){
			base_image = new ImageIcon("BP_B_square.png");
			alter_image = new ImageIcon("WP_B_square.png");
			label.setIcon(base_image);
		}
		if(p == 2){
			base_image = new ImageIcon("BQ_B_square.png");
			alter_image = new ImageIcon("WQ_B_square.png");
			label.setIcon(base_image);
		}
		if(p == -1){
			base_image = new ImageIcon("WP_B_square.png");
			alter_image = new ImageIcon("BP_B_square.png");
			label.setIcon(base_image);
		}
		if(p == -2){
			base_image = new ImageIcon("WQ_B_square.png");
			alter_image = new ImageIcon("BQ_B_square.png");
			label.setIcon(base_image);
		}
		if(p == 0){
			base_image = new ImageIcon("B_square.png");
			alter_image = new ImageIcon("W_square.png");
			label.setIcon(base_image);
		}
		 label.revalidate();
		 label.repaint();
	}
	
}
