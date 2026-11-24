package ServerClient;

import java.io.Serializable;

import model.Position;
import model.piece.Piece;

public class ChessInf implements Serializable{
	Piece piece;
	Position from;
	Position to;
	String id;
	
	public ChessInf(Position from, Position to) {
		this.from = from;
		this.to = to;
		this.id = id;
	}
	public void setPiece(Piece piece) {
		this.piece = piece;
	}
	public Piece getpiece() {
		return piece;
	}
	public Position getFrom() {
		return from;
	}
	public Position getTo() {
		return to;
	}
	public String getId() {
		return id;
	}
}