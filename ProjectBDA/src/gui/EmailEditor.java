package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import mail.EmailHandler;

public class EmailEditor {

	private JFrame frame;
	private JTextArea content;
	private JTextField toArea;
	private JTextField ccArea;
	private JTextField bccArea;
	private JTextField subArea;
	private JButton enviar; 
	private EmailHandler email;

	public EmailEditor(EmailHandler email) {

		frame = new JFrame("Editor");
		frame.setLayout(new BorderLayout());

		this.email = email;

		JPanel panel1 = new JPanel(new GridLayout(1,1));
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel(new BorderLayout());
		JPanel panelL = new JPanel(new GridLayout(4, 1));
		JPanel panelTF = new JPanel(new GridLayout(4, 1));

		JLabel to = new JLabel("To:", SwingConstants.RIGHT);
		JLabel cc = new JLabel("CC:", SwingConstants.RIGHT);
		JLabel bcc = new JLabel("BCC:", SwingConstants.RIGHT);
		JLabel subject = new JLabel("  Subject:");

		toArea = new JTextField();
		ccArea = new JTextField();
		bccArea = new JTextField();
		subArea = new JTextField();

		panelL.add(to);
		panelL.add(cc);
		panelL.add(bcc);
		panelL.add(subject);

		panelTF.add(toArea);
		panelTF.add(ccArea);
		panelTF.add(bccArea);
		panelTF.add(subArea);

		content = new JTextArea();
		content.setEditable(true);
		content.setLineWrap(true);
		content.setWrapStyleWord(true);

		enviar = new JButton(new ImageIcon(getClass().getResource("/send.png")));
		JButton archieve = new JButton(new ImageIcon(getClass().getResource("/file.png")));

		panel1.add(content);

		panel2.add(enviar);
		panel2.add(archieve);

		panel3.add(panelL, BorderLayout.WEST);
		panel3.add(panelTF, BorderLayout.CENTER);

		frame.add(panel1, BorderLayout.CENTER);
		frame.add(panel2, BorderLayout.SOUTH);
		frame.add(panel3, BorderLayout.NORTH);

		enviarEmail();
		
		frame.setSize(1000, 400);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

	}

	private void enviarEmail() {
		enviar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					InternetAddress[] to = {new InternetAddress(toArea.getText())};
//					InternetAddress[] cc = {new InternetAddress(ccArea.getText())};
//					InternetAddress[] bcc = {new InternetAddress(bccArea.getText())};
					email.enviarEmail(subArea.getText(), content.getText(), to, null, null);
				} catch (AddressException ex) {
					ex.printStackTrace();
				}
				toArea.setText("");
				ccArea.setText("");
				bccArea.setText("");
				subArea.setText("");
				content.setText("");
			}
		});
	}



	public void open() {
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}	

	//	public static void main(String[] args) {
	//		EmailEditor ee = new EmailEditor();
	//		ee.open();
	//	}

}
