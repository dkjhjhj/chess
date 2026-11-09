package model.piece;

import model.Color;
import model.Position;

public class Queen extends SlidingPiece {
	public Queen(Color color, Position position) {
		super(color, position);
	}
	
	@Override
	protected int[][] getDirections() {
		int[][] directions = {
				{-1, 1},	// 우측 대각선 상단
				{1, 1},		// 우측 대각선 하단
				{-1, -1},	// 좌측 대각선 상단
				{1, -1},	// 좌측 대각선 하단
				{-1, 0},	// 위
				{1, 0},		// 아래
				{0, -1},	// 왼쪽
				{0, 1}		// 오른쪽
		};
		
		return directions;
	}
}