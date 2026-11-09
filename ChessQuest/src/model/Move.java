package model;

import model.piece.Piece;

public class Move {
	private Position from;
	private Position to;
	private Piece capturedPiece;
	
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
}
