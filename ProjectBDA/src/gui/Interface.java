package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import common.standardInfoStruct;
import facebook.Timeline;

/**
 * A classe interface tem como objetivo criar a base gráfica do projeto.
 * Esta JFrame é constituida por dois JScrollPane com JTextArea incluidas que posteriormente serão alteradas para 
 * JLists de modo a termos a timeline. O outro JScrollPane irá ser utilizado para abrir os respetivos posts, tweets
 * e mails.
 * Para além do dito anteriormente existe também três botões que quando premidos vão criar cada um uma janela para
 * escrever posts, tweets e mails para serem enviados.
 * @author jose_santos
 *
 */

public class Interface {
	
	private JFrame frame;
	private Timeline timelineList;
	private JList<standardInfoStruct> face;
	private JTextArea viewPost;
	
	public Interface() {
		
		frame = new JFrame("Bom Dia Academia");
		frame.setLayout(new BorderLayout());
		
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		
		panel1.setLayout(new GridLayout());
		frame.add(panel1, BorderLayout.CENTER);
		frame.add(panel2, BorderLayout.SOUTH);
		
		timelineList = new Timeline("EAAEdPLJA8d0BAKBpufqqEP96zJusMI6EhV9ErThejmx0ZBgEhFnyhZCTCZADRdWV3WIsPgzeUwyBbd17ucBcITE3sCZBdXbP1n0pUUZBDHPXE1BqqZCHz6sFvpTOZBhb3Wiy6M4RoAYHP1Acul3SaM3NK0SvLkAqBmIcYcEZBYOMFwZDZD");
		face = new JList<>(getFaceList());
		
		viewPost = new JTextArea();
		viewPost.setLineWrap(true);
		viewPost.setWrapStyleWord(true);
		
		readFacePosts();
		
		JScrollPane scrollPane1 = new JScrollPane(face);
		JScrollPane scrollPane2 = new JScrollPane(viewPost);
		
		panel1.add(scrollPane1);
		panel1.add(scrollPane2);
		
		JButton faceButton = new JButton("Post");
		JButton tweetButton = new JButton("Tweet");
		JButton sendEmail = new JButton("Email");
		
		panel2.add(faceButton);
		panel2.add(tweetButton);
		panel2.add(sendEmail);
		
		viewPost.setEditable(false);
		
		frame.setSize(800, 400);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	
	}
	
	private DefaultListModel<standardInfoStruct> getFaceList() {
		
		ArrayList<standardInfoStruct> faceList = timelineList.getTimeline();
		DefaultListModel<standardInfoStruct> model = new DefaultListModel<>();
		
		for(standardInfoStruct info : faceList) 
			model.addElement(info);
		
		return model;
		
	}
	
	private void readFacePosts() {
		face.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(!e.getValueIsAdjusting()) {
					standardInfoStruct selectedValue = face.getSelectedValue();
					if(selectedValue != null) {
						viewPost.setText(selectedValue.getDate() + "\n" + "\n");
						viewPost.append(selectedValue.getTitle());
					}
				}
			}

		});
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
