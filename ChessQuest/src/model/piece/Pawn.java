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
		
		for(int dc : new int[] {-1, 1}) {
			int nextCol = col + dc;
			
			if (board.isInside(row, nextCol)) {
				Piece adjacent = board.getPiece(new Position(row, nextCol));
				
				if(adjacent != null &&
						adjacent == board.getEnPassantPawn() &&
						adjacent.getColor() != color) {
					Position target = new Position(nextRow, nextCol);
					
					if(RuleManager.isLegalMove(board, position, move)) {
						moves.add(target);
					}
				}
			}
			
			if (board.isInside(row, nextCol) &&
					board.getEnPassantPawn().getColor() != color &&
					board.getEnPassantPawn() == board.getPiece(new Position(row, nextCol))) {
				moves.add(new Position(nextRow, nextCol));
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
	
	/**
	 * 앙파상
	 * 
	 * 1. 폰이 두 칸 움직여야한다.
	 * 2. 폰을 움직이는데, 바로 옆에 폰이 있어야하며 그 폰이 앙파상 가능한 상태여야한다.
	 * 3. 이 경우, 폰에게 공격 가능한 영역이 생기게 된다.
	 * 4. 그리고 이 경우 앙파상 가능한 폰을 잡을 수 있다.
	 * 5. move의 경우, 그 위치에 존재한 것만 capturedPiece로 저장함으로, board 클래스에 따로 앙파상 메서드를 만들어야 한다.
	 * 
	 * 
	 */
}
