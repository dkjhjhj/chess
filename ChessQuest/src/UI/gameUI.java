package UI;

import java.awt.*;
import javax.swing.*;

import ServerClient.ChessInf;
import ServerClient.Client;
import game.*;

public class gameUI extends JPanel{
	private JFrame frame;
	private ChessGame game;
	private ChessPanel chessPanel;
	private StatusPanel statusPanel;
	private Client client;

	public gameUI(Client client) {
		this.client = client;
		buildGUI();
	}
	public void buildGUI() {
		game = new ChessGame();
		setLayout(new BorderLayout());
		chessPanel = new ChessPanel(this);
		statusPanel = new StatusPanel(this);
		add(chessPanel, BorderLayout.CENTER);
		add(statusPanel, BorderLayout.EAST);
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
	public ChessInf getData() {
		return chessPanel.getData();
	}
	public Client getClient() {
		return client;
	}

	public static void main(String[] args) {
		//gameUI a = new gameUI();
		/*JFrame frame = new JFrame("ChessGUI");
		frame.add(new gameUI());
		frame.setSize(700, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);*/
	}
}
