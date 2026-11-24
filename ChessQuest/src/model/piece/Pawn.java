package model.piece;

import java.util.ArrayList;
import java.util.List;

import model.Board;
import model.Color;
import model.Position;
import rule.RuleManager;

public class Pawn extends Piece {	
	public Pawn(Color color, Position position) {
		super(color, position);
	}
	
	@Override
	protected List<Position> getRawPositions(Board board) {
		List<Position> positions = new ArrayList<>();
		
		int direction = (color == Color.WHITE) ? -1 : 1;
		int row = position.getRow();
		int col = position.getCol();
		
		// 정면 이동
		int nextRow = row + direction;
		if(board.isInside(nextRow, col)) {
			Position move = new Position(nextRow, col);
			
			if(!board.hasPiece(move)) {
				positions.add(move);
				
				int startRow = (color == Color.WHITE) ? 6 : 1;
				int doubleRow = row + direction * 2;
				
				if(row == startRow
						&& board.isInside(doubleRow, col)) {
					Position doubleMove = new Position(doubleRow, col);
					
					if(!board.hasPiece(doubleMove))
					positions.add(doubleMove);
				}
			}
		}
		
		// 대각선 공격
		for (Position attack : getAttackPositions(board)) {
			Piece target = board.getPiece(attack);
			
			if(target != null
					&& target.getColor() != color) {
				positions.add(attack);
			}
		}
		
		// 앙파상
		Piece enPassantPawn = board.getEnPassantPawn();
		if(enPassantPawn != null
				&& enPassantPawn.getColor() != color) {
			
			for(int dc : new int[] {-1, 1}) {
				int nextCol = col + dc;
				
				if(!board.isInside(row, nextCol)) {
					continue;
				}
				
				Position adjacent = new Position(row, nextCol);
				if(board.getPiece(adjacent) == enPassantPawn) {
					positions.add(new Position(nextRow, nextCol));
				}
			}
		}
		
		return positions;
	}
	
	@Override
	public List<Position> getAttackPositions(Board board) {
		return getDiagonalPositions(board);
	}
	
	private List<Position> getDiagonalPositions(Board board) {
	    List<Position> positions = new ArrayList<>();

	    int direction = (color == Color.WHITE) ? -1 : 1;
	    int nextRow = position.getRow() + direction;
	    int col = position.getCol();

	    for (int dc : new int[] {-1, 1}) {
	        int nextCol = col + dc;

	        if (board.isInside(nextRow, nextCol)) {
	            positions.add(new Position(nextRow, nextCol));
	        }
	    }

	    return positions;
	}
}
