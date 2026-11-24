package model.piece;

import java.util.ArrayList;
import java.util.List;

import model.Board;
import model.Color;
import model.Position;
import rule.RuleManager;

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
	
	public List<Position> getPossibleMoves(Board board) {
		List<Position> moves = new ArrayList<>();
		
		for(Position move : getRawPositions(board)) {
			if(RuleManager.isLegalMove(board, position, move)) {
				moves.add(move);
			}
		}
		
		return moves;
	}
	
	public List<Position> getAttackPositions(Board board) {
		return getRawPositions(board);
	}
	
	protected abstract List<Position> getRawPositions(Board board);
}
