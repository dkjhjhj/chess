package UI;

import java.util.List;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import game.*;
import model.*;
import model.Color;
import model.piece.*;

public class gameUI {
	private JFrame frame;
	private JPanel chessPanel = new JPanel();
	private JPanel statusPanel = new JPanel();
	private ChessGame game;
	private JButton[][] squares = new JButton[ChessGame.BOARD_HEIGHT][ChessGame.BOARD_WIDTH];
	private Position selected = null;
	private List<Position> positions = null;
	private JLabel statusLabel = new JLabel();
	private JButton b_undo, b_reset;
	private static final java.awt.Color LIGHT    = new java.awt.Color(240, 217, 181);
	private static final java.awt.Color DARK     = new java.awt.Color(181, 136, 99);
	private static final java.awt.Color SELECTED = new java.awt.Color(246, 246, 105);
	private static final java.awt.Color MOVE = new java.awt.Color(135, 206, 235);
	public gameUI() {
		frame = new JFrame("ChessGUI");
		buildGUI();
		frame.setSize(700,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
	public void buildGUI() {
		game = new ChessGame();
		frame.add(chessPanel(),BorderLayout.CENTER);
		frame.add(statusPanel(),BorderLayout.EAST);
	}
	public JPanel chessPanel() {
		chessPanel.setLayout(new GridLayout(ChessGame.BOARD_HEIGHT, ChessGame.BOARD_WIDTH));
		for(int row=0;row<ChessGame.BOARD_HEIGHT;row++) {
			for(int col=0;col<ChessGame.BOARD_WIDTH;col++) {
				final int r = row;
				final int c = col;
				squares[row][col] = new JButton("");
				squares[row][col].setFocusPainted(false);
				squares[row][col].setMargin(new Insets(0,0,0,0));
				squares[row][col].setBorderPainted(false);
				squares[row][col].setOpaque(true);
				squares[row][col].addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent e) {
				    	if(selected == null) {
					        if(game.isSelectable(new Position(r,c))) {
					        	selected = new Position(r,c);
					        	refresh();
					        }
				    	}
				        else {
				        	if(selected.getRow() == r && selected.getCol() == c) {
				        		selected = null;
				        		refresh();
				        	}
				        	else if(game.isSelectable(new Position(r,c))) {
				        		selected = new Position(r,c);
					        	refresh();
				        	}
				        	else {
				        		for(Position p:positions) {
				        			if(p.getRow() == r && p.getCol() == c) {
					        			game.movePiece(selected, new Position(r,c));
						        		selected = null;
						        		refresh();
					        		}
				        		}
				        	}
				        }
				    }
				});
				chessPanel.add(squares[row][col]);
			}
		}
		refresh();
		return chessPanel;
	}
	public JPanel statusPanel() {
		b_undo = new JButton("무르기");
		b_undo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.undoTurn();
				selected = null;
				refresh();
			}
		});
		b_reset = new JButton("게임 초기화");
		b_reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game = new ChessGame();
				selected = null;
				refresh();
			}
		});
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.Y_AXIS));
		statusPanel.add(statusLabel);
		statusPanel.add(Box.createVerticalStrut(10));
		statusPanel.add(b_undo);
		statusPanel.add(Box.createVerticalStrut(10));
		statusPanel.add(b_reset);
		return statusPanel;
	}
	public void refresh() {
		if(selected != null) {
			positions = game.getPossibleMoves(selected);
		}
		else {
			positions = null;
		}
	    for (int row = 0; row < ChessGame.BOARD_HEIGHT; row++) {
	        for (int col = 0; col < ChessGame.BOARD_WIDTH; col++) {
	            JButton button = squares[row][col];
	            

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
	    updateStatus();
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
	private void updateStatus() {
	    Color turn = game.getCurrentTurn();
	    statusLabel.setText((turn == Color.WHITE ? "백" : "흑") + "의 차례");
	}
				    
	
	public static void main(String[] args) {
		gameUI a = new gameUI();
	}
}
	
