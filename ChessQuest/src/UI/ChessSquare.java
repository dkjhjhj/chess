package UI;

import java.awt.*;
import javax.swing.*;

public class ChessSquare extends JButton {
	private int row;
	private int col;

	public ChessSquare(int row, int col) {
		super("");
		this.row = row;
		this.col = col;
		setFocusPainted(false);
		setMargin(new Insets(0,0,0,0));
		setBorderPainted(false);
		setOpaque(true);
	}
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
}
