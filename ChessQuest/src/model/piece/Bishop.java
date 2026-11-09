package model.piece;

import java.util.ArrayList;
import java.util.List;

import model.Board;
import model.Color;
import model.Position;

public class Bishop extends Piece {
	public Bishop(Color color, Position position) {
		super(color, position);
	}
	
	@Override
	public List<Position> getPossibleMoves(Board board) {
		List<Position> moves = new ArrayList<>();
		
		int[][] directions = {
				{-1, 1},	// 우측 대각선 상단
				{1, 1},		// 우측 대각선 하단
				{-1, -1},	// 좌측 대각선 상단
				{1, -1}		// 좌측 대각선 하단
		};
		
		int row = position.getRow();
		int col = position.getCol();
		
		// 코드를 분리해도 될듯!
		for(int[] direction : directions) {
			int dr = direction[0];
			int dc = direction[1];
			
			int nextRow = row + dr;
			int nextCol = col + dc;
			
			while(board.isInside(nextRow, nextCol)) {				
				Position move = new Position(nextRow, nextCol);
				
				if(!board.hasPiece(move)) {
					moves.add(move);
				} else {
					if(board.getPiece(move).color != color) {
						moves.add(move);
					}
					
					break;
				}
				
				nextRow += dr;
				nextCol += dc;
			}
		}
		
		return moves;
	}

}
