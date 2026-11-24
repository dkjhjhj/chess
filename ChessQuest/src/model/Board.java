package model;

import java.util.Stack;

import game.ChessGame;
import model.piece.King;
import model.piece.Piece;
import model.piece.PieceType;
import rule.special.Castling;
import rule.special.EnPassant;
import rule.special.Promotion;

public class Board {
	private Piece[][] board;
	private Stack<Move> moveHistory = new Stack<>();
	
	private Castling castling = new Castling();
	private EnPassant enPassant = new EnPassant();
	private Promotion promotion = new Promotion();
	
	/**
	 * 1. 룩과 킹 실제 이동시 setMoving 함수 불러야함
	 * 2. 캐슬링 따로 만들어야함
	 * 3. 캐슬링 undo 따로 만들어야함
	 */
	
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
		
		if(piece != null) {
			piece.setPosition(position);
		}
	}
	
	public Piece[][] getBoard() {
		Piece[][] copy = new Piece[board.length][];
		
		for(int i = 0; i < board.length; i++) {
			copy[i] = board[i].clone();
		}
		
		return copy;
	}
	
	public void removePiece(Position position) {
        board[position.getRow()][position.getCol()] = null;
    }
	public void movePiece(Position from, Position to) {
		Piece piece = getPiece(from);
		
		if(piece == null) {
			throw new IllegalArgumentException("해당 위치에 기물이 없습니다.");
		}
		
		Piece capturedPiece = enPassant.getCapturedPiece(this, piece, to);
		
		removePiece(from);
		
		Move move = new Move(from, to, capturedPiece);
		
		enPassant.updateAfterMove(piece, to, move);
		
		setPiece(to, piece);
		moveHistory.add(move);
	}
	
	public boolean undoMove() {
		if(moveHistory.isEmpty()) {
			return false;
		}
		
		Move move = moveHistory.pop();
		Piece piece = getPiece(move.getTo());
		
		if(move.getPromotionType() != null) {
			piece = move.getOriginalPiece();
		}
		
		setPiece(move.getFrom(), piece);
		removePiece(move.getTo());
		
		if(move.getCapturedPiece() != null) {
			Piece capturedPiece = move.getCapturedPiece();
			setPiece(capturedPiece.getPosition(), capturedPiece);
		}
		
		enPassant.undo(move);
		
		return true;
	}
	
	public void promote(Position position, PieceType pieceType) {
		promotion.promote(this, position, pieceType, moveHistory.peek());
	}
	
	public int canCastling(Piece piece) {
		return castling.getAvailableDirections(this, piece);
	}
	
	public Piece getEnPassantPawn() {
		return enPassant.getEnPassantPawn();
	}
	
	public King getKing(Color color) {
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				Piece piece = board[row][col];
				
				if(piece instanceof King && piece.getColor() == color) {
					return (King) piece;
				}
			}
		}
		
		return null;
	}
	
	public boolean isInside(int row, int col) {
	    return row >= 0 && row < ChessGame.BOARD_HEIGHT
	        && col >= 0 && col < ChessGame.BOARD_WIDTH;
	}
}
