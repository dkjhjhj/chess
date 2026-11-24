package rule.special;

import model.Board;
import model.Color;
import model.Move;
import model.Position;
import model.piece.Bishop;
import model.piece.Knight;
import model.piece.Pawn;
import model.piece.Piece;
import model.piece.PieceType;
import model.piece.Queen;
import model.piece.Rook;

public class Promotion {
	public void promote(Board board, Position position, PieceType pieceType, Move move) {
		Piece pawn = board.getPiece(position);
		
		if(!(pawn instanceof Pawn)) {
			throw new IllegalArgumentException("폰이 아닙니다.");
		}
		
		move.setOriginalPiece(pawn);
		move.setPromotionType(pieceType);
		
		Piece promotionPiece = createPiece(pieceType, pawn.getColor(), position);
		board.setPiece(position, promotionPiece);
	}
	
	private Piece createPiece(PieceType type, Color color, Position position) {
		switch(type) {
		case PieceType.ROOK:
			return new Rook(color, position);
		case PieceType.KNIGHT:
			return new Knight(color, position);
		case PieceType.BISHOP:
			return new Bishop(color, position);
		case PieceType.QUEEN:
			return new Queen(color, position);
		default:
			return null;
		}
	}
}