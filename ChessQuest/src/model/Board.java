package model;

import java.util.Stack;

import game.ChessGame;
import model.piece.Bishop;
import model.piece.King;
import model.piece.Knight;
import model.piece.Pawn;
import model.piece.Piece;
import model.piece.PieceType;
import model.piece.Queen;
import model.piece.Rook;

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
		
		if(piece != null) {
			piece.setPosition(position);
		}
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
			setPiece(move.getTo(), move.getCapturedPiece());
		}
		
		return true;
	}
	
	public void promote(Position position, PieceType pieceType) {
		Piece pawn = getPiece(position);
		
		if(!(pawn instanceof Pawn)) {
			throw new IllegalArgumentException("폰이 아닙니다.");
		}
		
		Move move = moveHistory.peek();
		
		move.setOriginalPiece(pawn);
		move.setPromotionType(pieceType);
		
		Piece promotionPiece = createPiece(pieceType, pawn.getColor(), position);
		setPiece(position, promotionPiece);
	}
	
	private Piece createPiece(PieceType type, Color color, Position position) {
		switch(type) {
		case PieceType.ROOK:
			return new Rook(color, position);
		case PieceType.KNIGHT:
			return new Knight(color, position);
		case PieceType.BISHOP:
			return new Bishop(color, position);
		case PieceType.QUEEN:
			return new Queen(color, position);
		default:
			return null;
		}
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
