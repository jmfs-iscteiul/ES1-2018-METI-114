package gui;

import java.awt.Component;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import common.standardInfoStruct;

public class IconListRenderer extends JLabel implements ListCellRenderer<standardInfoStruct>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Component getListCellRendererComponent(JList<? extends standardInfoStruct> list, standardInfoStruct value,
			int index, boolean isSelected, boolean cellHasFocus) {
		
		setText(value.toString());
		if(isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		}
		else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}
		try {
			setIcon(new ImageIcon(new URL("https://upload.wikimedia.org/wikipedia/commons/c/c2/F_icon.svg")));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		setEnabled(list.isEnabled());
		setFont(list.getFont());
		return this;
	}

}
