package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

public class faceEditor {
	
	private JFrame frame;
	
	public faceEditor() {
		
		frame = new JFrame("Editor");
		frame.setLayout(new BorderLayout());
		
		JPanel panel1 = new JPanel(new GridLayout(1, 1));
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		
		
		JTextArea post = new JTextArea();
		post.setEditable(true);
		post.setLineWrap(true);
		post.setWrapStyleWord(true);
		
		JButton postar = new JButton("Send");
		JButton archieve = new JButton("Add archive");
		
		JComboBox<String> groups = new JComboBox<>();
		
		panel1.add(groups);
		panel1.add(post);
		
		panel2.add(postar);
		panel2.add(archieve);
		
		panel3.add(groups);
		
		frame.add(panel1, BorderLayout.CENTER);
		frame.add(panel2, BorderLayout.SOUTH);
		frame.add(panel3, BorderLayout.NORTH);
		
		frame.setSize(800, 400);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
	}
	
	public void open() {
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}	
	
//	public static void main(String[] args) {
//		faceEditor fe = new faceEditor();
//		fe.open();
//	}

}
