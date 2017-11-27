package main;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import net.miginfocom.swing.MigLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Main {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 590, 518);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		final Table table = new Table();
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 400, 400);
		panel.setLayout(new MigLayout("insets 0 0 0 0, gap 0 0", "[][][][][][][][]", "[][][][][][][][]"));
		
		table.initialize(panel);
		
		frame.getContentPane().add(panel);
		
		JButton btnPlay = new JButton("Play");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				play(table, new Agente(1), new Agente(-1));
			}
		});
		btnPlay.setBounds(444, 36, 89, 23);
		frame.getContentPane().add(btnPlay);
		
		JButton btnTrainng = new JButton("Training");
		btnTrainng.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Trainnig(table, new Agente(1), new Agente(-1));
			}
		});
		btnTrainng.setBounds(444, 96, 89, 23);
		frame.getContentPane().add(btnTrainng);
		
	
		
		
	}
	
	public void play(Table table,Agente player_a, Agente player_b){
		table.loadGame();
		
		
	}
	
	public void Trainnig(Table table,Agente player_a, Agente player_b){
		table.loadGame();
	}
	
}
