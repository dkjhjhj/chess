package model.piece;

import java.util.ArrayList;
import java.util.List;

import model.Board;
import model.Color;
import model.Position;

public class Knight extends Piece{
	public Knight(Color color, Position position) {
		super(color, position);
	}
	
	@Override
	public List<Position> getPossibleMoves(Board board) {
		List<Position> moves = new ArrayList<>();
		
		int[][] directions = {
				{1, 2},
				{1, -2},
				{-1, 2},
				{-1, -2},
				{2, 1},
				{2, -1},
				{-2, 1},
				{-2, -1}
		};
		
		int row = position.getRow();
		int col = position.getCol();
		
		for(int[] direction : directions) {
			int dr = direction[0];
			int dc = direction[1];
			
			int nextRow = row + dr;
			int nextCol = col + dc;
			
			if(!board.isInside(nextRow, nextCol)) {
				continue;
			}
			
			Position move = new Position(nextRow, nextCol);
			
			if(board.hasPiece(move) && board.getPiece(move).getColor() == color) {
				continue;
			}
			
			moves.add(move);
		}
		
		return moves;
	}

}
