package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import common.standardInfoStruct;
import mail.MailInfoStruct;

public class IconListRenderer extends JLabel implements ListCellRenderer<standardInfoStruct>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Override
	public Component getListCellRendererComponent(JList<? extends standardInfoStruct> list, standardInfoStruct value,
			int index, boolean isSelected, boolean cellHasFocus) {
		
		setText(value.toString());
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setOpaque(true);
		if(isSelected) {
			setBackground(Color.LIGHT_GRAY);
//			setForeground(Color.GRAY);
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
	
	private void setIcons(standardInfoStruct value) {
		if(value instanceof MailInfoStruct) {
			setIcon(new ImageIcon(this.getClass().getResource("/email-icon.png")));
		}
		else if(value.getAuthor() == null) {
			setIcon(new ImageIcon(this.getClass().getResource("/facebook-icon.png")));
		}
		else {
			setIcon(new ImageIcon(this.getClass().getResource("/twitter-icon.jpg")));
		}
	}

}
