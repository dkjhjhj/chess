package model.piece;

import java.util.ArrayList;
import java.util.List;

import model.Board;
import model.Color;
import model.Position;
import rule.RuleManager;

public class King extends Piece{
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	
	private boolean isMove = false;
	
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
	
	public King(Color color, Position position) {
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
					&& board.getPiece(move).color == color) {
				continue;
			}
			
			positions.add(move);
		}
		
		return positions;
	}
	
	@Override
	public List<Position> getPossibleMoves(Board board) {
		List<Position> moves = super.getPossibleMoves(board);
		
		if(!RuleManager.isKingInCheck(board, this)) {
			int direction = board.canCastling(this);
			int row = position.getRow();
			int col = position.getCol();
			
			if((direction & LEFT) != 0) {
				moves.add(new Position(row, col - 2));
			}
			
			if((direction & RIGHT) != 0) {
				moves.add(new Position(row, col + 2));
			}
		}
		
		return moves;
	}
	
	public boolean getMoving() {
		return isMove;
	}
	
	public void setMoving(boolean state) {
		isMove = state;
	}
}
