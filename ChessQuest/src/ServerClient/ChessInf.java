package ServerClient;

import java.io.Serializable;

import model.Position;
import model.piece.Piece;

public class ChessInf implements Serializable{
	Piece piece;
	Position position;
	ChessInf(Piece piece, Position position){
		this.piece = piece;
		this.position = position;
	}
	public Piece getpiece() {
		return piece;
	}
	public Position getposition() {
		return position;
	}
}