package model.piece;

import java.util.ArrayList;
import java.util.List;

import model.Board;
import model.Color;
import model.Position;
import rule.RuleManager;

public class Knight extends Piece{
	private int [][] directions = {
			{1, 2},
			{1, -2},
			{-1, 2},
			{-1, -2},
			{2, 1},
			{2, -1},
			{-2, 1},
			{-2, -1}
	};
	
	public Knight(Color color, Position position) {
		super(color, position);
	}
	
	@Override
	protected List<Position> getRawPositions(Board board) {
		List<Position> positions = new ArrayList<>();
		
		int row = position.getRow();
		int col = position.getCol();
		
		for(int[] direction : directions) {
			int nextRow = row + direction[0];
			int nextCol = col + direction[1];
			
			if(!board.isInside(nextRow, nextCol)) {
				continue;
			}
			
			Position move = new Position(nextRow, nextCol);
			
			if(board.hasPiece(move)
					&& board.getPiece(move).getColor() == color) {
				continue;
			}
			
			positions.add(move);
		}
		
		return positions;
	}
}
