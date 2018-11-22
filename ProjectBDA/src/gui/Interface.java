package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

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
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import mail.Email;
import mail.MailInfoStruct;
import twitter.TwitterApp;

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
	private JList<standardInfoStruct> allLists;
	private JTextArea viewPost;
	private Email email;
	private JScrollPane scrollPane2;
	private JFXPanel webPanel;
	private WebView webView;
	private JButton faceButton;

	public Interface(Email email) {

		frame = new JFrame("Bom Dia Academia");
		frame.setLayout(new BorderLayout());

		this.email = email;

		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();

		panel1.setLayout(new GridLayout());
		frame.add(panel1, BorderLayout.CENTER);
		frame.add(panel2, BorderLayout.SOUTH);

		allLists = new JList<>();
		allLists.setCellRenderer(new IconListRenderer());
		allLists.setModel(getLists());

		viewPost = new JTextArea();
		viewPost.setLineWrap(true);
		viewPost.setWrapStyleWord(true);

		readElements();

		webPanel = new JFXPanel();
		Platform.runLater(()-> {
			Group root = new Group();
			Scene scene = new Scene(root);
			webView = new WebView();
			webView.autosize();
			root.getChildren().add(webView);
			webPanel.setScene(scene);
		});
		Platform.setImplicitExit(false);

		JScrollPane scrollPane1 = new JScrollPane(allLists);
		scrollPane2 = new JScrollPane(viewPost);

		panel1.add(scrollPane1);
		panel1.add(scrollPane2);

		faceButton = new JButton("Post");
		JButton tweetButton = new JButton("Tweet");
		JButton sendEmail = new JButton("Email");

		panel2.add(faceButton);
		panel2.add(tweetButton);
		panel2.add(sendEmail);
		
		editPosts();

		viewPost.setEditable(false);

		frame.setSize(800, 400);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	}


	private DefaultListModel<standardInfoStruct> getLists() {

		Timeline timelineList = new Timeline("EAAEdPLJA8d0BAKBpufqqEP96zJusMI6EhV9ErThejmx0ZBgEhFnyhZCTCZADRdWV3WIsPgzeUwyBbd17ucBcITE3sCZBdXbP1n0pUUZBDHPXE1BqqZCHz6sFvpTOZBhb3Wiy6M4RoAYHP1Acul3SaM3NK0SvLkAqBmIcYcEZBYOMFwZDZD");
		ArrayList<standardInfoStruct> faceList = timelineList.getTimeline();

		TwitterApp twitter = new TwitterApp();
		twitter.authenticateMyAccount();

		ArrayList<standardInfoStruct> tweetList = twitter.fetchTimeline();

		List<MailInfoStruct> emailList = email.receberEmail();

		DefaultListModel<standardInfoStruct> model = new DefaultListModel<>();

		for(standardInfoStruct fInfo : faceList) 
			model.addElement(fInfo);


		for(standardInfoStruct tInfo : tweetList)
			model.addElement(tInfo);

		for(MailInfoStruct mInfo : emailList)
			model.addElement(mInfo);


		return model;


	}


	private void readElements() {
		allLists.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(!e.getValueIsAdjusting()) {
					standardInfoStruct selectedValue = allLists.getSelectedValue();
					if(selectedValue != null) {
						if(selectedValue instanceof MailInfoStruct) {
							Platform.runLater(()-> {
								webView.getEngine().loadContent(selectedValue.getTitle());
							});
							scrollPane2.setViewportView(webPanel);
						}
						else {
							viewPost.setText(selectedValue.getDate() + "\n" + "\n");
							viewPost.append(selectedValue.getTitle());
							scrollPane2.setViewportView(viewPost);
						}
					}
				}
			}

		});
	}
	
	private void editPosts() {
		faceButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				FaceEditor fe = new FaceEditor();
				fe.open();
				
			}
		});
	}

	public void open() {
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}	

//	public static void main(String[] args) {
//		Email email = new Email("");
//		Interface i = new Interface();
//		i.open();
//	}

}
