package rule.special;

import model.Board;
import model.Move;
import model.Position;
import model.piece.Pawn;
import model.piece.Piece;

public class EnPassant {
	private Piece enPassantPawn;
	
	public Piece getEnPassantPawn() {
		return enPassantPawn;
	}
	
	public Piece getCapturedPiece(Board board, Piece piece, Position to) {
		Piece capturedPiece;
		
		if(isEnPassantMove(board, piece, to)) {
			capturedPiece = enPassantPawn;
			board.removePiece(capturedPiece.getPosition());
			
			return capturedPiece;
		}
		
		capturedPiece = board.getPiece(to);
		if(enPassantPawn == null) {
			board.removePiece(to);
		}
		
		return capturedPiece;
	}
	
	public void updateAfterMove(Piece piece, Position to, Move move) {
		move.setPreviousEnPassant(enPassantPawn);
		enPassantPawn = null;
		
		if(canEnPassant(piece, to)) {
			enPassantPawn = piece;
		}
	}
	
	public void undo(Move move) {
		enPassantPawn = move.getPreviousEnPassant();
	}
	
	private boolean canEnPassant(Piece piece, Position to) {
		return piece instanceof Pawn && 
				Math.abs(to.getRow() - piece.getPosition().getRow()) == 2;
	}
	
	private boolean isEnPassantMove(Board board, Piece piece, Position to) {
		if(enPassantPawn != null &&														// 앙파상한 기물이 있어야 한다.
				piece instanceof Pawn &&												// 움직인 기물이 폰이어야 한다.
				piece.getAttackPositions(board).contains(to) &&							// 그 폰이 공격해야한다.
				piece.getPosition().getRow() == enPassantPawn.getPosition().getRow() &&	// 폰과 앙파상 폰이 같은 열에 있어야 한다.
				to.getCol() == enPassantPawn.getPosition().getCol()) {					// 목적지가 앙파상 폰과 같은 행에 위치해야 한다.
			return true;
		}
		
		return false;
	}
}
