package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import twitter.TwitterApp;



public class TwitterEditor {

	private JFrame frame;

	public TwitterEditor(TwitterApp twitter) {
		frame = new JFrame("Editor");
		frame.setLayout(new BorderLayout());

		JPanel panel1 = new JPanel(new GridLayout(1, 1));
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();

		JTextArea tweet = new JTextArea();
		tweet.setEditable(true);
		tweet.setLineWrap(true);
		tweet.setWrapStyleWord(true);

		JButton tweetar = new JButton(new ImageIcon(getClass().getResource("/send.png")));

		panel1.add(tweet);

		panel2.add(tweetar);
		
		tweetar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				twitter.postTweet(tweet.getText());
				
			}
		});

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



}
