package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import common.Xml;
import facebook.Timeline;
import mail.EmailHandler;
import twitter.TwitterApp;

public class Login {
	
	private JFrame frame;
	private JLabel usernameL, passL;
	private JTextField usernameTF; 
	private JComboBox<String> cbox;
	private JPasswordField passPF;
	private JButton lButton;
	private Xml xml = new Xml();
	
/**
 * O Login permite, por equanto, que haja a autenticação do mail antes de entrarmos na aplicação.
 */
	
	public Login() {
		
		frame = new JFrame("Authentication");
		frame.setLayout(new GridLayout(3,1));
		
		JPanel usernameP = new JPanel();
		JPanel passwordP = new JPanel();
		JPanel b = new JPanel();
		
		usernameL = new JLabel("Username:", SwingConstants.LEFT);
		usernameP.add(usernameL);
		usernameTF = new JTextField(10);
		usernameP.add(usernameTF);
		
		passL = new JLabel("Password:", SwingConstants.LEFT);
		passwordP.add(passL);
		passPF = new JPasswordField(10);
		passwordP.add(passPF);
		
		lButton = new JButton("Login");
		b.add(lButton);
		
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
				}
				else {
					String message = "Credenciais incorretas";
					JOptionPane.showMessageDialog(new JFrame(), message, "Credenciais incorretas",
					        JOptionPane.ERROR_MESSAGE);
				}
				
				
			}
		});

		frame.add(usernameP);
		frame.add(passwordP);
		frame.add(b);
		
		frame.setSize(250, 150);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
	}
	
/**
 * Esta função tem como objetivo garantir que o que está inserido na JComboBox é um mail contento
 * um @ e com ou @ e pt
 * @return boolean que verifica se é um mail ou não
 */
	
//	private boolean itsAnEmail() {
//		
//		String email = cbox.getSelectedItem().toString();
//		
//		if(email.contains("@") && (email.contains("com") || email.contains("pt"))) {
//			return true;
//		}
//		
//		System.out.println("Email não válido");
//		return false;
//	}
	
	private boolean auth(String user, String pass) {
		
		String inputUser = xml.leituraXML("Login", "Username");
		String inputPass = xml.leituraXML("Login", "Password");
		
		if(user.equals(inputUser) && pass.equals(inputPass)) {
			
			return true;
			
		}
		
		return false;
		
	}

/**
 * 
 * @return a passe que está inserida
 */

	public String getPass() {
		
		char[] p = passPF.getPassword();
		return new String(p);
		
	}

	private void open() {
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	
	public static void main(String[] args) {
		Login l = new Login();
		l.open();
	}
	

}
