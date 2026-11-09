package model.piece;

import java.util.List;

import model.Board;
import model.Color;
import model.Position;

public abstract class Piece {
	protected Color color;
	protected Position position;
	
	public Piece(Color color, Position position) {
		this.color = color;
		this.position = position;
	}
	
	public Color getColor() {
		return color;
	}
	
	public Position getPosition() {
		return position;
	}
	
	public void setPosition(Position position) {
		this.position = position;
	}
	
	public abstract List<Position> getPossibleMoves(Board board);
}
