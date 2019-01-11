package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ListeningButton implements Listenable {
	private JButton button;

	public ListeningButton(String name, ActionListener actionListener, Dimension d){
		this.button = new JButton(name);
		this.listeningAction(actionListener);
		this.getButton().setPreferredSize(d);
	}

	public JButton getButton() {
		return button;
	}

	@Override
	public void listeningAction(ActionListener actionListener) {
		this.button.addActionListener(actionListener);
	}
}
