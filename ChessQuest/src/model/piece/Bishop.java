package model.piece;

import model.Color;
import model.Position;

public class Bishop extends SlidingPiece {
	public Bishop(Color color, Position position) {
		super(color, position);
	}
	
	@Override
	protected int[][] getDirections() {
		int[][] directions = {
				{-1, 1},	// 우측 대각선 상단
				{1, 1},		// 우측 대각선 하단
				{-1, -1},	// 좌측 대각선 상단
				{1, -1},	// 좌측 대각선 하단
		};
		
		return directions;
	}
}
