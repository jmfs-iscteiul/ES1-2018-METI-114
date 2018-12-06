package BDA.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import BDA.common.Xml;
import BDA.facebook.Timeline;
import BDA.mail.EmailHandler;
import BDA.twitter.TwitterApp;

/**
 * O Login permite que haja uma autenticação da pessoa, sem que haja tokens e passes no código.
 */
public class Login {

	private JFrame frame;
	private JLabel usernameL, passL;
	private JTextField usernameTF; 
	private JPasswordField passPF;
	private Xml xml = new Xml();

	public Login() {

		frame = new JFrame("Authentication");
		frame.setLayout(new GridLayout(3,1));

		JPanel usernameP = new JPanel();
		JPanel passwordP = new JPanel();
		JPanel b = new JPanel();

		usernameL = new JLabel("Username:", SwingConstants.LEFT);
		usernameP.add(usernameL);
		usernameTF = new JTextField(15);
		usernameP.add(usernameTF);

		passL = new JLabel("Password:", SwingConstants.LEFT);
		passwordP.add(passL);
		passPF = new JPasswordField(15);
		passwordP.add(passPF);

		JButton lButton = new JButton("Login");
		b.add(lButton);
		
		JButton rButton = new JButton("Registar");
		b.add(rButton);
		
		/**
		 * A acção do botão lButton permite que haja a associação do que aparece no login aos tokens do
		 * facebook e do twitter e à passe do email para que posteriormente a interface seja aberta.
		 */
		lButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (auth(usernameTF.getText(), getPass())) {
					Timeline facebook = new Timeline(xml.leituraXML("Facebook", "Token"));

					TwitterApp twitter = new TwitterApp();
					twitter.authenticateMyAccount(xml.leituraXML("Twitter", "ConsumerKey"), 
							xml.leituraXML("Twitter", "ConsumerSecret"), 
							xml.leituraXML("Twitter", "AccessToken"), 
							xml.leituraXML("Twitter", "AccessTokenSecret"));

					EmailHandler email = new EmailHandler(xml.leituraXML("Mail", "Account"), 
							xml.leituraXML("Mail", "Password"));

					Interface i = new Interface(facebook, twitter, email, xml);
					i.open();
					frame.dispose();
				}
				else {
					String message = "Credenciais incorretas";
					JOptionPane.showMessageDialog(new JFrame(), message, "Erro",
							JOptionPane.ERROR_MESSAGE);
					usernameTF.setText("");
					passPF.setText("");
				}
				
			}
		});
		
		/**
		 * O ActionListener do botão rButton permite que o utilizador se registe primeiro antes de entrar na
		 * aplicação mandando esses dados para o ficheiro XML.
		 */
		rButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				xml.escreverXML("Registo", "User", usernameTF.getText());
				xml.escreverXML("Registo", "Pass", getPass());
				
				usernameTF.setText("");
				passPF.setText("");
				
				String message = "Registo efetuado";
				JOptionPane.showMessageDialog(new JFrame(), message, "Informação",
						JOptionPane.INFORMATION_MESSAGE);
				
			}
		});

		frame.add(usernameP);
		frame.add(passwordP);
		frame.add(b);

		frame.setSize(300, 150);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	}

	/**
	 * Esta função vai servir para saber se o que está inserido na JTextField corresponde ao que está escrito.
	 * no XML
	 * @param user é o valor que provem da JTextField 
	 * @param pass é o valor que vem da JPasswordField
	 * @return se corresponde ou não ao que está no ficheiro XML
	 */
	private boolean auth(String user, String pass) {

		String inputUser = xml.leituraXML("Registo", "User");
		String inputPass = xml.leituraXML("Registo", "Pass");

		if(user.equals(inputUser) && pass.equals(inputPass)) {

			return true;

		}

		return false;

	}

	/**
	 * 
	 * @return a passe que está inserida.
	 */
	public String getPass() {

		char[] p = passPF.getPassword();
		return new String(p);

	}

	/**
	 * Função para a JFrame ser aberta no centro do ecrã
	 */
	private void open() {
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	
	public static void main(String[] args) {
		Login l = new Login();
		l.open();
	}

}
