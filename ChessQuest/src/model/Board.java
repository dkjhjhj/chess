package model;

import java.util.Stack;

import game.ChessGame;
import model.piece.Piece;

public class Board {
	private Piece[][] board;
	private Stack<Move> moveHistory = new Stack<>();
	
	public Board() {
		board = new Piece[ChessGame.BOARD_HEIGHT][ChessGame.BOARD_WIDTH];
	}
	
	public Piece getPiece(Position position) {
		return board[position.getRow()][position.getCol()];
	}
	
	public boolean hasPiece(Position position) {
		return getPiece(position) != null;
	}
	
	public void setPiece(Position position, Piece piece) {
		board[position.getRow()][position.getCol()] = piece;
	}
	
	public void removePiece(Position position) {
        board[position.getRow()][position.getCol()] = null;
    }
	
	public void movePiece(Position from, Position to) {
		Piece piece = getPiece(from);
		
		if(piece == null) {
			throw new IllegalArgumentException("해당 위치에 기물이 없습니다.");
		}
		
		Piece capturedPiece = getPiece(to);
		Move move = new Move(from, to, capturedPiece);
		
		setPiece(to, piece);
		removePiece(from);
		piece.setPosition(to);
		
		moveHistory.add(move);
	}
	
	public boolean undoMove() {
		if(moveHistory.isEmpty()) {
			return false;
		}
		
		Move move = moveHistory.pop();
		Piece piece = getPiece(move.getTo());
		
		setPiece(move.getFrom(), piece);
		piece.setPosition(move.getFrom());
		
		if(move.getCapturedPiece() != null) {
			setPiece(move.getFrom(), move.getCapturedPiece());
		}
		
		return true;
	}
	
	public boolean isInside(int row, int col) {
	    return row >= 0 && row < ChessGame.BOARD_HEIGHT
	        && col >= 0 && col < ChessGame.BOARD_WIDTH;
	}
}
