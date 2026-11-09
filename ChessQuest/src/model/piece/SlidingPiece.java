package model.piece;

import java.util.ArrayList;
import java.util.List;

import game.RuleManager;
import model.Board;
import model.Color;
import model.Position;

public abstract class SlidingPiece extends Piece {
	public SlidingPiece(Color color, Position position) {
		super(color, position);
	}
	
	protected abstract int[][] getDirections();
	
	@Override
	public List<Position> getPossibleMoves(Board board) {
		List<Position> moves = new ArrayList<>();
		
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
				
				if(RuleManager.isLegalMove(board, position, move)) {
					moves.add(move);
				}
				
				if (target != null) {
			        break;
			    }
			
				nextRow += dr;
				nextCol += dc;
			}
		}
		
		return moves;
	}
}
