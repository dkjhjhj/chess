package rule.special;

import game.ChessGame;
import model.Board;
import model.Color;
import model.Position;
import model.piece.King;
import model.piece.Piece;
import model.piece.Rook;
import rule.RuleManager;

public class Castling {
	public int getAvailableDirections(Board board, Piece piece) {
		if(!(piece instanceof King)) {
			return 0;
		}
		
		King king = (King)piece;
		
		if(king.getMoving()) {
			return 0;
		}
		
		Color color = king.getColor();
		int kingR = king.getPosition().getRow();
		
		Position leftSidePosition = new Position(kingR, 0);
		Position rightSidePosition = new Position(kingR, ChessGame.BOARD_WIDTH - 1);
		
		int direction = 0;
		
		direction |= isValidRook(board, color, leftSidePosition) ? King.LEFT : 0;
		direction |= isValidRook(board, color, rightSidePosition) ? King.RIGHT : 0;
		
		return filterCastlingDirections(board, king, direction);
	}
	
	public boolean isCastlingMove(Board board, King king, Position to) {
		int distance = to.getCol() - king.getPosition().getCol();
		
		if(to.getRow() != king.getPosition().getRow()) {
			return false;
		}
		
		if(Math.abs(distance) <= 1) {
			return false;
		}
		
		int direction = (distance > 0) ? King.RIGHT : King.LEFT;
		
		return (getAvailableDirections(board, king) & direction) != 0;
	}
	
	public Position rookFrom(King king, int direction) {
		int row = king.getPosition().getRow();
		int col = (direction == King.LEFT) ? 0 : ChessGame.BOARD_WIDTH - 1;
		
		return new Position(row, col);
	}
	
	public Position rookTo(King king, int direction) {
		Position kingFrom = king.getPosition();
		
		int row = kingFrom.getRow();
		int col = (direction == King.LEFT) ? kingFrom.getCol() - 1 : kingFrom.getCol() + 1;
		
		return new Position(row, col);
	}
	
	private int filterCastlingDirections(Board board, King king, int direction) {
		if((direction & King.LEFT) != 0) {
			direction = checkCastlingPath(board, king, direction, King.LEFT);
		}
		
		if((direction & King.RIGHT) != 0) {
			direction = checkCastlingPath(board, king, direction, King.RIGHT);
		}
		
		return direction;
	}
	
	private int checkCastlingPath(Board board, King king, int direction, int side) {
		int kingR = king.getPosition().getRow();
		int kingC = king.getPosition().getCol();
		
		int step = (side == King.LEFT) ? -1 : 1;
		int limit = (side == King.LEFT) ? 0 : ChessGame.BOARD_WIDTH - 1;
		
		for(int i = kingC + step; i != limit; i += step) {
			Position position = new Position(kingR, i);
			
			if(board.getPiece(position) != null) {
				return direction & ~side;
			}
			
			if(Math.abs(kingC - i) <= 2 &&
					RuleManager.isPositionAttacked(board, position, king.getColor())) {
				return direction & ~side;
			}
		}
		
		return direction;
	}
	
	private boolean isValidRook(Board board, Color color, Position position) {
		Piece piece = board.getPiece(position);
		
		if(!(piece instanceof Rook)) {
			return false;
		}
		
		Rook rook = (Rook)piece;
		
		return !rook.getMoving() && rook.getColor() == color;		
	}
	
}
