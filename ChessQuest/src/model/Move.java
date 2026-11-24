package model;

import model.piece.Piece;
import model.piece.PieceType;

public class Move {
	private Position from;
	private Position to;
	private Piece capturedPiece;
	
	private Piece originalPiece;
	private PieceType promotionType;
	private Piece previousEnPassant;
	private int castlingDirection;
	
	private Piece castlingRook;
	private boolean isFirstMove;
	
	public Move(Position from, Position to, Piece capturedPiece) {
		this.from = from;
		this.to = to;
		this.capturedPiece = capturedPiece;
	}
	
	public Position getFrom() {
		return from;
	}
	
	public Position getTo() {
		return to;
	}
	
	public Piece getCapturedPiece() {
		return capturedPiece;
	}
	
	public Piece getOriginalPiece() {
		return originalPiece;
	}
	
	public void setOriginalPiece(Piece original) {
		originalPiece = original;
	}
	
	public PieceType getPromotionType() {
		return promotionType;
	}
	
	public void setPromotionType(PieceType type) {
		promotionType = type;
	}
	
	public Piece getPreviousEnPassant() {
		return previousEnPassant;
	}
	
	public void setPreviousEnPassant(Piece pawn) {
		previousEnPassant = pawn;
	}
	
	public int getCastlingDirection() {
		return castlingDirection;
	}
	
	public void setCastlingDirection(int direction) {
		castlingDirection = direction;
	}
	
	public boolean isCastling() {
		return castlingDirection != 0;
	}
	
	public Piece getCastlingRook() {
		return castlingRook;
	}
	
	public void setCastlingRook(Piece rook) {
		castlingRook = rook;
	}
	
	public boolean getFirstMove() {
		return isFirstMove;
	}
	
	public void setFisrtMove(boolean state) {
		isFirstMove = state;
	}
}
