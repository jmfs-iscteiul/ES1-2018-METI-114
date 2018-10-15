package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

public class Interface {
	
	private JFrame frame;
	
	public Interface() {
		
		frame = new JFrame("Bom Dia Academia");
		frame.setLayout(new BorderLayout());
		
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		
		
		panel1.setLayout(new GridLayout());
		frame.add(panel1, BorderLayout.CENTER);
		frame.add(panel2, BorderLayout.SOUTH);
		
		JTextArea timeline = new JTextArea();
		JTextArea viewPost = new JTextArea();
		
		JScrollPane scrollPane1 = new JScrollPane(timeline);
		JScrollPane scrollPane2 = new JScrollPane(viewPost);
		
		panel1.add(scrollPane1);
		panel1.add(scrollPane2);
		
		timeline.setEditable(false);
		viewPost.setEditable(false);
		
		frame.setSize(800, 400);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	
	}
	
	private void open() {
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	
	public static void main(String[] args) {
		Interface i = new Interface();
		i.open();
	}

}
