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
		frame.setBounds(100, 100, 651, 445);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		final Table table = new Table();
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 400, 400);
		panel.setLayout(new MigLayout("insets 0 0 0 0, gap 0 0", "[][][][][][][][]", "[][][][][][][][]"));
		
		table.initialize(panel);
		
		frame.getContentPane().add(panel);
		
		final JButton btnPlay = new JButton("Play");
		btnPlay.setEnabled(false);
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				play(table, new Agente(1), new Agente(-1));
			}
		});
		btnPlay.setBounds(440, 125, 164, 44);
		frame.getContentPane().add(btnPlay);
		
		JButton btnTrainng = new JButton("Training");
		btnTrainng.setEnabled(false);
		btnTrainng.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Trainnig(table, new Agente(1), new Agente(-1));
			}
		});
		btnTrainng.setBounds(440, 200, 164, 44);
		frame.getContentPane().add(btnTrainng);
		
		JButton btnNewGame = new JButton("New Game");
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnNewGame.setBounds(469, 28, 103, 23);
		frame.getContentPane().add(btnNewGame);
		
		JButton btnNewTrain = new JButton("New Trainning");
		btnNewTrain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewTrain.setBounds(469, 62, 103, 23);
		frame.getContentPane().add(btnNewTrain);
		
		
	
		
		
	}
	
	public void play(Table table,Agente player_a, Agente player_b){
		
		
		table.loadGame();
		int player = 0;
		while(table.winner() == 0){
			if(player%2 == 0){
				
				player_a.play(table);
				
			}else{
				player_b.play(table);
			}
			
			++player;
		}
		
	}
	
	public void Trainnig(Table table,Agente player_a, Agente player_b){
		table.loadGame();
	}
}
