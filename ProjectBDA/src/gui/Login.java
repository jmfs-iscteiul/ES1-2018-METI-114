package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.WindowConstants;

import mail.Email;

public class Login {
	
	private JFrame frame;
	private JLabel username, password;
	private JComboBox<String> cbox;
	private JPasswordField pass;
	private JButton lButton;
	
/**
 * O Login permite, por equanto, que haja a autenticação do mail antes de entrarmos na aplicação.
 */
	
	public Login() {
		
		frame = new JFrame("Authentication");
		frame.setLayout(new GridLayout(2, 1));
		
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		
		panel1.setLayout(new GridLayout(2, 2));
		frame.add(panel1);
		
		frame.add(panel2);

		username = new JLabel("Username");
		panel1.add(username);
		
		String[] emails = {"jmfss1@iscte-iul.pt", "jmloa@iscte-iul.pt", "rmcmc@iscte-iul.pt", "darsa@iscte-iul.pt"};
		
		cbox = new JComboBox<String>(emails);
		panel1.add(cbox);
		
		password = new JLabel("Password");
		panel1.add(password);
		
		pass = new JPasswordField();
		panel1.add(pass);
		
		lButton = new JButton("Login");
		panel2.add(lButton);
		
/**
 * O ActionListener do lbutton permite que a janela da interface seja aberta a partir do momento
 * em que o mail está correto
 */
	
		lButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(itsAnEmail() == true) {
					Interface i = new Interface(new Email(getcboxText(), getPass()));
					i.open();
				}
			
			}
		});
		
		frame.setSize(350, 140);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
	}
	
/**
 * Esta função tem como objetivo garantir que o que está inserido na JComboBox é um mail contento
 * um @ e com ou @ e pt
 * @return boolean que verifica se é um mail ou não
 */
	
	private boolean itsAnEmail() {
		
		String email = cbox.getSelectedItem().toString();
		
		if(email.contains("@") && (email.contains("com") || email.contains("pt"))) {
			return true;
		}
		
		System.out.println("Email não válido");
		return false;
	}

/**
 * 
 * @return o conteudo da combo box
 */
	
	public String getcboxText() {
		return cbox.getSelectedItem().toString();
	}

/**
 * 
 * @return a passe que está inserida
 */

	public String getPass() {
		
		char[] p = pass.getPassword();
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
