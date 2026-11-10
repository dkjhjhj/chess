package model;

import model.piece.Piece;
import model.piece.PieceType;

public class Move {
	private Position from;
	private Position to;
	private Piece capturedPiece;
	
	private Piece originalPiece;
    private PieceType promotionType;
	
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
}
