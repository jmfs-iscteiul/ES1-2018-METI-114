package gui;

import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class Login {
	
	private JFrame frame;
	private JLabel usernameE, passwordE, usernameF, tokenF, usernameT, tokenT1, tokenT2, tokenT3, tokenT4;
	private JTextField usernameTextAreaF, usernameTextAreaT; 
	private JComboBox<String> cbox;
	private JPasswordField pass, tokenFP, tokenTP1, tokenTP2, tokenTP3, tokenTP4;
	private JButton lButton;
	
/**
 * O Login permite, por equanto, que haja a autenticação do mail antes de entrarmos na aplicação.
 */
	
	public Login() {
		
		frame = new JFrame("Authentication");
		frame.setLayout(new GridLayout(13,1));
		
		JPanel i1 = new JPanel();
		JPanel e1 = new JPanel();
		JPanel e2 = new JPanel();
		JPanel i2 = new JPanel();
		JPanel f1 = new JPanel();
		JPanel f2 = new JPanel();
		JPanel i3 = new JPanel();
		JPanel t1 = new JPanel();
		JPanel t2 = new JPanel();
		JPanel t3 = new JPanel();
		JPanel t4 = new JPanel();
		JPanel t5 = new JPanel();
		JPanel b = new JPanel();
		
		JLabel mailI = new JLabel(new ImageIcon(getClass().getResource("/email-able.png")));
		i1.add(mailI);

		usernameE = new JLabel("E-mail: ", SwingConstants.LEFT);
		e1.add(usernameE);
		String[] emails = {"jmfss1@iscte-iul.pt", "jmloa@iscte-iul.pt", "rmcmc@iscte-iul.pt", "darsa@iscte-iul.pt"};
		cbox = new JComboBox<String>(emails);
		e1.add(cbox);
		
		passwordE = new JLabel("        E-mail password:", SwingConstants.LEFT);
		e2.add(passwordE);
		pass = new JPasswordField(35);
		e2.add(pass);
		
		JLabel faceI = new JLabel(new ImageIcon(getClass().getResource("/facebook-able.png")));
		i2.add(faceI);
		
		usernameF = new JLabel("Username for facebook:", SwingConstants.LEFT);
		f1.add(usernameF);
		usernameTextAreaF = new JTextField(35);
		f1.add(usernameTextAreaF);
		
		tokenF = new JLabel("               Access Token:", SwingConstants.LEFT);
		f2.add(tokenF);
		tokenFP = new JPasswordField(35);
		f2.add(tokenFP);
		
		JLabel twitterI = new JLabel(new ImageIcon(getClass().getResource("/twitter-able.png")));
		i3.add(twitterI);
		
		usernameT = new JLabel("  Username for twitter:", SwingConstants.LEFT);
		t1.add(usernameT);
		usernameTextAreaT = new JTextField(35);
		t1.add(usernameTextAreaT);
		
		tokenT1 = new JLabel("            Consumer Key:", SwingConstants.LEFT);
		t2.add(tokenT1);
		tokenTP1 = new JPasswordField(35);
		t2.add(tokenTP1);
		
		tokenT2 = new JLabel("         Consumer Secret:", SwingConstants.LEFT);
		t3.add(tokenT2);
		tokenTP2 = new JPasswordField(35);
		t3.add(tokenTP2);
		
		tokenT3 = new JLabel("             Access Token:", SwingConstants.LEFT);
		t4.add(tokenT3);
		tokenTP3 = new JPasswordField(35);
		t4.add(tokenTP3);
		
		tokenT4 = new JLabel("   Access Token Secret:", SwingConstants.LEFT);
		t5.add(tokenT4);
		tokenTP4 = new JPasswordField(35);
		t5.add(tokenTP4);
		
		lButton = new JButton("Login");
		b.add(lButton);
//		
/**
 * O ActionListener do lbutton permite que a janela da interface seja aberta a partir do momento
 * em que o mail está correto
 */
	
//		lButton.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				
//				if(itsAnEmail() == true) {
//					System.out.println("Acesso concedido");
//					Interface i = new Interface(new EmailHandler(getcboxText(), getPass()));
//					i.open();
//				}
//			
//			}
//		});
//		
		frame.add(i1);
		frame.add(e1);
		frame.add(e2);
		frame.add(i2);
		frame.add(f1);
		frame.add(f2);
		frame.add(i3);
		frame.add(t1);
		frame.add(t2);
		frame.add(t3);
		frame.add(t4);
		frame.add(t5);
		frame.add(b);
		
		frame.setSize(700, 700);
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
