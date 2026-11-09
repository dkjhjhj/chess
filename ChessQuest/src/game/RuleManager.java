package game;

import model.Board;
import model.Color;
import model.Position;
import model.piece.Piece;

public class RuleManager {
	public static boolean isKingInCheck(Board board, Piece King) {
		return isPositionAttacked(board, King.getPosition(), King.getColor());
	}
	 
	public static boolean isPositionAttacked(Board board, Position target, Color color) {		
		Color attackerColor = (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
		
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				Position current = new Position(row, col);
				Piece piece = board.getPiece(current);
				
				if(piece != null
						&& piece.getColor() == attackerColor
						&& piece.getAttackPositions(board).contains(target)) {
					return true;
				}
			}
		}
		 
		 
		return false;
	}
}
