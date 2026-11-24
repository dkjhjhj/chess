package model.piece;

import java.util.ArrayList;
import java.util.List;

import model.Board;
import model.Color;
import model.Position;
import rule.RuleManager;

public abstract class SlidingPiece extends Piece {
	protected abstract int[][] getDirections();
	
	public SlidingPiece(Color color, Position position) {
		super(color, position);
	}
	
	@Override
	protected List<Position> getRawPositions(Board board) {
		List<Position> positions = new ArrayList<>();
		
		int row = position.getRow();
		int col = position.getCol();
		
		for(int[] direction : getDirections()) {
			int dr = direction[0];
			int dc = direction[1];
			
			int nextRow = row + dr;
			int nextCol = col + dc;
		
			while(board.isInside(nextRow, nextCol)) {				
				Position move = new Position(nextRow, nextCol);
				Piece target = board.getPiece(move);
				
				if(target != null && target.color == color) {
					break;
				}
				
				positions.add(move);
				
				if (target != null) {
			        break;
			    }
			
				nextRow += dr;
				nextCol += dc;
			}
		}
		
		return positions;
	}
}
