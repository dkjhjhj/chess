package model.piece;

import java.util.ArrayList;
import java.util.List;

import game.RuleManager;
import model.Board;
import model.Color;
import model.Position;

public class Pawn extends Piece {
	public Pawn(Color color, Position position) {
		super(color, position);
	}
	
	@Override
	public List<Position> getPossibleMoves(Board board) {
		List<Position> moves = new ArrayList<>();
		
		int direction = (color == Color.WHITE) ? -1 : 1;
		
		int row = position.getRow();
		int col = position.getCol();
		
		// 한 칸 행진
		int nextRow = row + direction;
		
		Position move = new Position(nextRow, col);
		if(board.isInside(nextRow, col) && !board.hasPiece(move) &&
				RuleManager.isLegalMove(board, position, move)) {
			moves.add(move);
			
			// 두 칸 행진
			int startRow = (color == Color.WHITE) ? 6 : 1;
			int doubleRow = row + direction * 2;
			
			move = new Position(doubleRow, col);
			if(row == startRow &&
					board.isInside(doubleRow, col) &&
					!board.hasPiece(move) &&
					RuleManager.isLegalMove(board, position, move)) {
				moves.add(move);
			}
		}
		
		// 대각선 공격
		List<Position> positions = getDiagonalPositions(board);
		
		for(Position attack : positions) {
			if(board.hasPiece(attack) &&
					board.getPiece(attack).color != color &&
					RuleManager.isLegalMove(board, position, attack)) {
				moves.add(attack);
			}
		}
		
		return moves;
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
