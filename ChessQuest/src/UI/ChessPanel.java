package UI;

import java.util.List;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import game.*;
import model.*;
import model.Color;
import model.piece.*;

public class ChessPanel extends JPanel {
	private gameUI ui;
	private ChessSquare[][] squares = new ChessSquare[ChessGame.BOARD_HEIGHT][ChessGame.BOARD_WIDTH];
	private Position selected = null;
	private List<Position> positions = null;
	private static final java.awt.Color LIGHT    = new java.awt.Color(240, 217, 181);
	private static final java.awt.Color DARK     = new java.awt.Color(181, 136, 99);
	private static final java.awt.Color SELECTED = new java.awt.Color(246, 246, 105);
	private static final java.awt.Color MOVE     = new java.awt.Color(135, 206, 235);

	public ChessPanel(gameUI ui) {
		this.ui = ui;
		setLayout(new GridLayout(ChessGame.BOARD_HEIGHT, ChessGame.BOARD_WIDTH));
		for(int row=0;row<ChessGame.BOARD_HEIGHT;row++) {
			for(int col=0;col<ChessGame.BOARD_WIDTH;col++) {
				final ChessSquare square = new ChessSquare(row, col);
				square.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						handleClick(square.getRow(), square.getCol());
					}
				});
				squares[row][col] = square;
				add(squares[row][col]);
			}
		}
	}
	private void handleClick(int r, int c) {
		ChessGame game = ui.getGame();
		game.handleBoardClick(selected);
		refresh();
	}
	public void clearSelection() {
		selected = null;
	}
	public void refresh() {
		ChessGame game = ui.getGame();
		if(selected != null) {
			positions = game.getPossibleMoves(selected);
		}
		else {
			positions = null;
		}
		for (int row = 0; row < ChessGame.BOARD_HEIGHT; row++) {
			for (int col = 0; col < ChessGame.BOARD_WIDTH; col++) {
				ChessSquare button = squares[row][col];

				button.setOpaque(true);
				button.setBackground((row + col) % 2 == 0 ? LIGHT : DARK);
				if (selected != null
						&& selected.getRow() == row
						&& selected.getCol() == col) {
					button.setBackground(SELECTED);
				}

				button.setText(toSymbol(game.getPiece(new Position(row, col))));
				if(positions != null) {
					for(Position p : positions) {
						if(p.getRow() == row && p.getCol() == col){
							button.setBackground(MOVE);
						}
					}
				}
			}
		}
	}
	private String toSymbol(Piece piece) {
		if (piece == null) {
			return "";
		}
		boolean white = (piece.getColor() == Color.WHITE);

		if (piece instanceof King)   return white ? "♔" : "♚";
		if (piece instanceof Queen)  return white ? "♕" : "♛";
		if (piece instanceof Rook)   return white ? "♖" : "♜";
		if (piece instanceof Bishop) return white ? "♗" : "♝";
		if (piece instanceof Knight) return white ? "♘" : "♞";
		if (piece instanceof Pawn)   return white ? "♙" : "♟";
		return "?";
	}
}
