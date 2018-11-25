package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
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
 * Esta JFrame é constituida por dois JScrollPane com uma JTextArea para visualização de posts e 
 * uma JList para visualizar a timeline por ordem cronológica. 
 * Para além do dito anteriormente existe também três botões que quando premidos criam uma janela
 * de edição de posts e tweets que são enviados para a plataforma online (falta fazer a edição e 
 * envio de emails)
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
	private JButton tweetButton;
	private TwitterApp twitter;
	private JCheckBox f, t, e;

	public Interface(Email email) {

		frame = new JFrame("Bom Dia Academia");
		frame.setLayout(new BorderLayout());

		this.email = email;
		twitter = new TwitterApp();
		twitter.authenticateMyAccount();

		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();

		panel1.setLayout(new GridLayout());
		frame.add(panel1, BorderLayout.CENTER);
		frame.add(panel2, BorderLayout.SOUTH);
		frame.add(panel3, BorderLayout.NORTH);

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
		scrollPane1.setHorizontalScrollBar(null);
		scrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane2 = new JScrollPane(viewPost);

		panel1.add(scrollPane1);
		panel1.add(scrollPane2);

		faceButton = new JButton("Postar");
		tweetButton = new JButton("Tweetar");
		JButton sendEmail = new JButton("Enviar email");

		
		stateIcons();

		panel2.add(faceButton);
		panel2.add(tweetButton);
		panel2.add(sendEmail);

		panel3.add(f);
		panel3.add(t);
		panel3.add(e);

		editPosts();
		editTweets();

		viewPost.setEditable(false);

		frame.setSize(800, 400);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	}
	
/**
 * A função getList tem como objetivo retornar uma DefaultListModel de standardInfoStruct. Esta model é
 * constituida pelas listas de posts, tweets e emails que são retornadas das respetivas classes.
 * Posteriormente essa model vai ser adicionada a JList para que seja mostrada a timeline.
 * @return model com a timeline
 */


	private DefaultListModel<standardInfoStruct> getLists() {

		Timeline timelineList = new Timeline("EAAEdPLJA8d0BAKBpufqqEP96zJusMI6EhV9ErThejmx0ZBgEhFnyhZCTCZADRdWV3WIsPgzeUwyBbd17ucBcITE3sCZBdXbP1n0pUUZBDHPXE1BqqZCHz6sFvpTOZBhb3Wiy6M4RoAYHP1Acul3SaM3NK0SvLkAqBmIcYcEZBYOMFwZDZD");
		ArrayList<standardInfoStruct> faceList = timelineList.getTimeline();

		ArrayList<standardInfoStruct> tweetList = twitter.fetchTimeline();

		List<MailInfoStruct> emailList = email.receberEmail();
		
		ArrayList<standardInfoStruct> all = new ArrayList<>(); 
		all.addAll(faceList);
		all.addAll(tweetList);
		all.addAll(emailList);
		Collections.sort(all);

		DefaultListModel<standardInfoStruct> model = new DefaultListModel<>();
		
		for(standardInfoStruct val : all)
			model.addElement(val);

		return model;


	}
	
/**
 * A função readElements é uma forma de vermos as notifiações na JTextArea com um principal cuidado
 * para o mail que está a ser adicionado a uma webView.
 */

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
	
/**
 * Função para dar uma ação ao (JButton) faceButton para garantir que abre o editor de envio de
 * posts para a plataforma facebook
 */
	
	private void editPosts() {
		faceButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				FaceEditor fe = new FaceEditor();
				fe.open();
				
			}
		});
	}

/**
 * Função para dar uma ação ao (JButton) tweetButton para garantir que abre o editor de envio de
 * tweets para a plataforma twitter
 */

	private void editTweets() {
		tweetButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				TwitterEditor te = new TwitterEditor(twitter);
				te.open();
				
			}
		});
	}
	
/**
 * A função stateIcons() é ainda incompleta mas tem como objetivo ser uma check box de icons para
 * ativar e desativar as arraylist do facebook, do twitter e do mail. Neste momento só altera o estado
 * do icon
 */
	
	private void stateIcons() {
		
		f = new JCheckBox();
		f.setSelected(true);

		f.setIcon(new ImageIcon(getClass().getResource("/facebook-disabled.png")));
		f.setSelectedIcon(new ImageIcon(getClass().getResource("/facebook-able.png")));
		f.setDisabledIcon(new ImageIcon(getClass().getResource("/facebook-disabled.png")));
		
		t = new JCheckBox();
		t.setSelected(true);

		t.setIcon(new ImageIcon(getClass().getResource("/twitter-disabled.png")));
		t.setSelectedIcon(new ImageIcon(getClass().getResource("/twitter-able.png")));
		t.setDisabledIcon(new ImageIcon(getClass().getResource("/twitter-disabled.png")));
		
		e = new JCheckBox();
		e.setSelected(true);

		e.setIcon(new ImageIcon(getClass().getResource("/email-disabled.png")));
		e.setSelectedIcon(new ImageIcon(getClass().getResource("/email-able.png")));
		e.setDisabledIcon(new ImageIcon(getClass().getResource("/email-disabled.png")));
		
	}

/**
 * Função para abrir a janela do BDA e coloca-la no centro do ecrã
 */

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
