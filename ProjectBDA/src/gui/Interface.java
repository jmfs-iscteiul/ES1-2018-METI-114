package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import common.Xml;
import common.standardInfoStruct;
import facebook.Timeline;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import mail.EmailHandler;
import mail.MailInfoStruct;
import twitter.TwitterApp;

/**
 * A classe interface tem como objetivo criar a base gráfica do projeto.
 * Esta JFrame é constituida por dois JScrollPane com uma JTextArea para visualização de posts e 
 * uma JList para visualizar a timeline por ordem cronológica. 
 * Para além do dito anteriormente existe também três botões que quando primidos criam uma janela
 * para envio de post, tweets e emails.
 * 
 * @author jose_santos
 */
public class Interface {

	private JFrame frame;
	private JList<standardInfoStruct> allLists;
	private JTextArea viewPost;
	private JScrollPane scrollPane2;
	private JFXPanel webPanel;
	private WebView webView;
	private JButton faceButton;
	private JButton tweetButton;
	private JButton sendEmail;
	private JButton search;
	private JButton reset;
	private JTextField word;
	private Timeline facebook;
	private TwitterApp twitter;
	private EmailHandler email;
	private Xml xml;
	private JCheckBox f, t, e;
	private ArrayList<standardInfoStruct> faceList, tweetList;
	private List<MailInfoStruct> emailList;
	private ArrayList<standardInfoStruct> all;
	private DefaultListModel<standardInfoStruct> model;

	public Interface(Timeline facebook, TwitterApp twitter, EmailHandler email, Xml xml) {

		frame = new JFrame("Bom Dia Academia");
		frame.setLayout(new BorderLayout());

		this.facebook = facebook;
		this.twitter = twitter;
		this.email = email;
		this.xml = xml;

		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel(new FlowLayout());

		panel1.setLayout(new GridLayout());
		frame.add(panel1, BorderLayout.CENTER);
		frame.add(panel2, BorderLayout.SOUTH);
		frame.add(panel3, BorderLayout.NORTH);

		stateIcons();

		allLists = new JList<>();
		allLists.setCellRenderer(new IconListRenderer());
		allLists.setModel(getLists());

		viewPost = new JTextArea();
		viewPost.setLineWrap(true);
		viewPost.setWrapStyleWord(true);

		readElements();
		iconsAction();

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

		search = new JButton("Procurar");
		word = new JTextField("Introduzir palavra", 30);
		reset = new JButton("Reset");

		faceButton = new JButton("Postar");
		tweetButton = new JButton("Tweetar");
		sendEmail = new JButton("Enviar email");

		ImageIcon image = new ImageIcon(getClass().getResource("/vertical-line.png"));
		JLabel filtros = new JLabel("Filtros: ");
		JLabel separator = new JLabel(image);

		panel2.add(faceButton);
		panel2.add(tweetButton);
		panel2.add(sendEmail);

		panel3.add(reset);
		panel3.add(search);
		panel3.add(word);
		panel3.add(separator);
		panel3.add(filtros);
		panel3.add(f);
		panel3.add(t);
		panel3.add(e);

		sendNot();
		searchWord();

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

		all = new ArrayList<>();
		model = new DefaultListModel<>();


		if(verifyInternetConnection()) {

			Timeline timelineList = new Timeline("EAAEdPLJA8d0BAKBpufqqEP96zJusMI6EhV9ErThejmx0ZBgEhFnyhZCTCZADRdWV3WIsPgzeUwyBbd17ucBcITE3sCZBdXbP1n0pUUZBDHPXE1BqqZCHz6sFvpTOZBhb3Wiy6M4RoAYHP1Acul3SaM3NK0SvLkAqBmIcYcEZBYOMFwZDZD");
			faceList = timelineList.getTimeline();

			tweetList = twitter.fetchTimeline();
			emailList = email.receberEmail();

			all.addAll(faceList);
			all.addAll(tweetList);
			all.addAll(emailList);
			Collections.sort(all);

			xml.escreverDeVarias(all);

		}else {

			all = xml.lerDeVarias();
			emailList = new ArrayList<>();
			faceList = new ArrayList<>();
			tweetList = new ArrayList<>();

			for(int i = 0; i < all.size(); i++) {
				if(all.get(i) instanceof MailInfoStruct) {
					emailList.add((MailInfoStruct) all.get(i));
				}
				else if(all.get(i).getAuthor() == null || all.get(i).getAuthor().equals("")) {
					faceList.add(all.get(i));
				}
				else {
					tweetList.add(all.get(i));
				}
			}


		}

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
	 * Função que permite fazer a acção de cada JButton para abrir a janela de edição de posts, tweets e 
	 * emails.
	 */
	private void sendNot() {

		faceButton.addActionListener((e) -> {

			FaceEditor fe = new FaceEditor(xml);
			fe.open();

		});

		tweetButton.addActionListener((e) -> {

			TwitterEditor te = new TwitterEditor(twitter);
			te.open();

		});

		sendEmail.addActionListener((e) -> {

			EmailEditor ee = new EmailEditor(email);
			ee.open();

		});
	}

	/**
	 * A função stateIcons() tem como objetivo trocar os icons para ativado / desativado. Posteriormente esses
	 * icons serão usados para fazer os filtros de ativação / desativação de cada serviço.
	 */
	private void stateIcons() {

		f = new JCheckBox(new ImageIcon(getClass().getResource("/facebook-disabled.png")),true);

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
	 * Esta função permite a atualização das listas que são apresentadas consoante o icon (
	 * ativado/desativado).
	 */
	private void iconsAction() {
		List<standardInfoStruct> temp = new ArrayList<>();

		f.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				model.clear();
				viewPost.setText(null);
				Platform.runLater(() -> {
					webView.getEngine().loadContent("");
				});

				if(!f.isSelected()) {

					if(t.isSelected()) temp.addAll(tweetList);
					if(e.isSelected()) temp.addAll(emailList);

					Collections.sort(temp);
					for(standardInfoStruct s : temp) {
						model.addElement(s);
					}
					temp.clear();
				} else {

					if(t.isSelected()) temp.addAll(tweetList);
					if(e.isSelected()) temp.addAll(emailList);
					temp.addAll(faceList);

					Collections.sort(temp);

					for(standardInfoStruct s : temp) {
						model.addElement(s);
					}
					temp.clear();
				}

			}
		});

		t.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				model.clear();
				viewPost.setText(null);
				Platform.runLater(() -> {
					webView.getEngine().loadContent("");
				});

				if(!t.isSelected()) {

					if(f.isSelected()) temp.addAll(faceList);
					if(e.isSelected()) temp.addAll(emailList);

					Collections.sort(temp);
					for(standardInfoStruct s : temp) {
						model.addElement(s);
					}
					temp.clear();
				} else {

					if(f.isSelected()) temp.addAll(faceList);
					if(e.isSelected()) temp.addAll(emailList);
					temp.addAll(tweetList);

					Collections.sort(temp);

					for(standardInfoStruct s : temp) {
						model.addElement(s);
					}
					temp.clear();
				}

			}
		});

		e.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				model.clear();
				viewPost.setText(null);
				Platform.runLater(() -> {
					webView.getEngine().loadContent("");
				});

				if(!e.isSelected()) {

					if(f.isSelected()) temp.addAll(faceList);
					if(t.isSelected()) temp.addAll(tweetList);

					Collections.sort(temp);
					for(standardInfoStruct s : temp) {
						model.addElement(s);
					}
					temp.clear();
				} else {

					if(f.isSelected()) temp.addAll(faceList);
					if(t.isSelected()) temp.addAll(tweetList);
					temp.addAll(emailList);

					Collections.sort(temp);

					for(standardInfoStruct s : temp) {
						model.addElement(s);
					}
					temp.clear();
				}

			}
		});
	}

	/**
	 * Função que permite a procura de uma palavra chave (e.g. data ou palavra) na lista de notificações.
	 */
	private void searchWord() {

		search.addActionListener((event) -> {
			model.clear();
			for(int i = 0; i < all.size(); i++) {
				if(all.get(i).toString().contains(word.getText())) {
					if((all.get(i) instanceof MailInfoStruct && e.isSelected()) || 
							(all.get(i).getAuthor() == null && f.isSelected())
							|| (all.get(i).getAuthor() != null && t.isSelected())) {
						model.addElement(all.get(i));
					}
				}
			}
		});


		word.addMouseListener(new MouseAdapter() {


			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getSource() == word)
					word.setText("");
			}
		});

		reset.addActionListener((event) -> {

			word.setText("");
			for(standardInfoStruct val : all)
				model.addElement(val);

		});

	}

	/**
	 * Função para verificação da existência de internet.
	 * 
	 * @return valor boolean que assinala se existe conexão à internet ou não
	 */
	private boolean verifyInternetConnection() {
		Socket socket = null;
		boolean reachable = false;
		try {
			socket = new Socket("www.google.com", 80);
			reachable = true;
		} catch (Exception e) {
		} finally {            
			if (socket != null) 
				try { 
					socket.close(); 
				} catch(IOException e) {}
		}

		return reachable;
	}

	/**
	 * Função para abrir a janela do BDA e coloca-la no centro do ecrã.
	 */
	public void open() {
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}	

}
