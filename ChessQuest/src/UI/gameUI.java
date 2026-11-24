package UI;

import java.awt.*;
import javax.swing.*;

import game.*;

public class gameUI {
	private JFrame frame;
	private ChessGame game;
	private ChessPanel chessPanel;
	private StatusPanel statusPanel;

	public gameUI() {
		frame = new JFrame("ChessGUI");
		buildGUI();
		frame.setSize(700,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	public void buildGUI() {
		game = new ChessGame();
		chessPanel = new ChessPanel(this);
		statusPanel = new StatusPanel(this);
		frame.add(chessPanel, BorderLayout.CENTER);
		frame.add(statusPanel, BorderLayout.EAST);
		refresh();
	}
	public ChessGame getGame() {
		return game;
	}
	public void refresh() {
		chessPanel.refresh();
		statusPanel.updateStatus();
	}
	public void undo() {
		game.undoTurn();
		chessPanel.clearSelection();
		refresh();
	}
	public void reset() {
		game = new ChessGame();
		chessPanel.clearSelection();
		refresh();
	}

	public static void main(String[] args) {
		gameUI a = new gameUI();
	}
}
