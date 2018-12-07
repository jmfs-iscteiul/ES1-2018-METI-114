package BDA.gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import BDA.common.standardInfoStruct;
import BDA.mail.MailInfoStruct;

/**
 * Esta classe tem como objetivo adaptarmos os icons no incio de cada elemento da lista.
 */
public class IconListRenderer extends JLabel implements ListCellRenderer<standardInfoStruct>{

	private static final long serialVersionUID = 1L;

	@Override
	public Component getListCellRendererComponent(JList<? extends standardInfoStruct> list, standardInfoStruct value,
			int index, boolean isSelected, boolean cellHasFocus) {

		setText(value.toString());
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setOpaque(true);
		if(isSelected) {
			setBackground(Color.LIGHT_GRAY);
		}
		else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}
		setIcons(value);
		setEnabled(list.isEnabled());
		setFont(list.getFont());
		return this;
	}

	/**
	 * Função que define quais os icons que correspondem a cada classe (facebook, twitter ou email)
	 * pela estrutura da mensagem.
	 * @param value
	 */
	private void setIcons(standardInfoStruct value) {
		if(value instanceof MailInfoStruct) {
			setIcon(new ImageIcon(this.getClass().getResource("/email-icon.png")));
		}
		else if(value.getAuthor() == null || value.getAuthor().equals("")) {
			setIcon(new ImageIcon(this.getClass().getResource("/facebook-icon.png")));
		}
		else {
			setIcon(new ImageIcon(this.getClass().getResource("/twitter-icon.jpg")));
		}
	}
	
}
