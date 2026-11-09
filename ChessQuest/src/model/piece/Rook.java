package model.piece;

import model.Color;
import model.Position;

public class Rook extends SlidingPiece {
	public Rook(Color color, Position position) {
		super(color, position);
	}
	
	@Override
	protected int[][] getDirections() {
		int[][] directions = {
				{-1, 0},	// 위
				{1, 0},		// 아래
				{0, -1},	// 왼쪽
				{0, 1}		// 오른쪽
		};
		
		return directions;
	}
}
