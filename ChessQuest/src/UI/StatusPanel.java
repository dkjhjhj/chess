package UI;

import java.awt.event.*;
import javax.swing.*;

import model.Color;

public class StatusPanel extends JPanel {
	private gameUI ui;
	private JLabel statusLabel = new JLabel();
	private JButton b_undo, b_reset;

	public StatusPanel(gameUI ui) {
		this.ui = ui;
		b_undo = new JButton("무르기");
		b_undo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ui.undo();
			}
		});
		b_reset = new JButton("게임 초기화");
		b_reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ui.reset();
			}
		});
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(statusLabel);
		add(Box.createVerticalStrut(10));
		add(b_undo);
		add(Box.createVerticalStrut(10));
		add(b_reset);
	}
	public void updateStatus() {
		Color turn = ui.getGame().getCurrentTurn();
		statusLabel.setText((turn == Color.WHITE ? "백" : "흑") + "의 차례");
	}
}
