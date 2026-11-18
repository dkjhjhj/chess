package model.piece;

import java.util.ArrayList;
import java.util.List;

import game.RuleManager;
import model.Board;
import model.Color;
import model.Position;

public class King extends Piece{
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	
	private boolean isMove = false;
	
	public King(Color color, Position position) {
		super(color, position);
	}
	
	@Override
	public List<Position> getPossibleMoves(Board board) {
		List<Position> moves = new ArrayList<>();
		
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
			
			if(board.hasPiece(move) && board.getPiece(move).color == color) {
				continue;
			}
			
			if(!RuleManager.isPositionAttacked(board, move, color)) {
				moves.add(move);
			}
		}
		
		if(!RuleManager.isKingInCheck(board, this)) {
			int direction = board.canCastling(this);
			
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
	
	public void setMoving() {
		isMove = true;
	}
	
	/** 캐슬링
	 *  조건 3. 킹과 룩 사이 어떠한 기물도 있으면 안된다.
	 *  조건 4. 킹과 룩 사이, 정확히는 캐슬링을 위한 공간인 킹으로부터 움직일 방향 2칸 내가 공격받아선 안된다.
	 *  -> RuleManager의 isPositionAttacked() 함수에서 쉽게 체크 가능
	 *  
	 */
}
