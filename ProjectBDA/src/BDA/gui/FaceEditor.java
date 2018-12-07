package BDA.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import BDA.common.Xml;
import BDA.facebook.PostGroups;

/**
 * Classe que permite a criação de uma janela para envio de posts com ou sem images para a 
 * plataforma facebook online.
 * 
 * @author jose_santos
 */
public class FaceEditor {

	private JFrame frame;
	private PostGroups postGroups;
	private boolean pressed = false;
	private JFileChooser chooser;

	public FaceEditor(Xml xml) {

		frame = new JFrame("Editor");
		frame.setLayout(new BorderLayout());

		JPanel panel1 = new JPanel(new GridLayout(1, 1));
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();


		JTextArea post = new JTextArea();
		post.setEditable(true);
		post.setLineWrap(true);
		post.setWrapStyleWord(true);

		JButton postar = new JButton(new ImageIcon(getClass().getResource("/send.png")));
		JButton archieve = new JButton(new ImageIcon(getClass().getResource("/file.png")));

		postGroups = new PostGroups(xml.leituraXML("Facebook", "Token"));
		ArrayList<String> grupos = postGroups.getResult();
		String[]vetorGrupos = new String [grupos.size()];
		for (int i = 0; i < grupos.size(); i++) {
			vetorGrupos[i] = grupos.get(i);
		}

		JComboBox<String> groups = new JComboBox<>(vetorGrupos);

		panel1.add(post);

		panel2.add(postar);
		panel2.add(archieve);

		/**
		 * O ActionListener do JButton postar tem como objetivo saber o que está escrito na JTextArea
		 * post e enviar essa informação para a plataforma facebook online.
		 */
		postar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(isPressed() == true) {
					postGroups.postarimagem(chooser.getSelectedFile().getPath(), chooser.getSelectedFile().getName(), post.getText(), groups.getSelectedItem().toString());
					pressed = false;
					post.setText("");
				}
				else {
					postGroups.postarTexto(groups.getSelectedItem().toString(), post.getText());
					post.setText("");
				}
			}
		});

		/**
		 * ActionListener que permite que a ação do JButton archive seja a abertura de um diretório
		 * com os ficheiros do nosso pc para ser anexado ao post para ser enviado a plataforma facebook 
		 * online.
		 */
		archieve.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				pressed = true;

				chooser = new JFileChooser();
				int returnVal = chooser.showOpenDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					System.out.println("You chose to open this file: " +
							chooser.getSelectedFile().getName());
				}


			}
		});
		panel3.add(groups);

		frame.add(panel1, BorderLayout.CENTER);
		frame.add(panel2, BorderLayout.SOUTH);
		frame.add(panel3, BorderLayout.NORTH);

		frame.setSize(800, 400);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

	}

	/**
	 * 
	 * @return variável que permite saber se o JButton archive foi primido.
	 */
	private boolean isPressed() {
		return pressed;
	}

	/**
	 * Função para a JFrame ser aberta no centro do ecrã.
	 */
	public void open() {
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}	

}
