package game;

import model.Board;
import model.Color;
import model.Position;
import model.piece.Bishop;
import model.piece.King;
import model.piece.Knight;
import model.piece.Pawn;
import model.piece.Piece;
import model.piece.Queen;
import model.piece.Rook;

public class ChessGame {
	public static final int BOARD_WIDTH = 8;
	public static final int BOARD_HEIGHT = 8;
	
	private Board board;
	
	public ChessGame() {
		board = new Board();
		initializeBoard();
	}
	
	private void initializeBoard() {
		for(int col = 0; col < BOARD_WIDTH; col++) {
			Pawn whitePawn = new Pawn(Color.WHITE, new Position(BOARD_HEIGHT - 2, col));
			Pawn blackPawn = new Pawn(Color.BLACK, new Position(1, col));
			
			board.setPiece(whitePawn.getPosition(), whitePawn);
			board.setPiece(blackPawn.getPosition(), blackPawn);
		}
		
		Piece[] whitePieces = createBackRow(Color.WHITE, BOARD_HEIGHT - 1);
		Piece[] blackPieces = createBackRow(Color.BLACK, 0);
		
		for(int i = 0; i < whitePieces.length; i++) {
			board.setPiece(whitePieces[i].getPosition(), whitePieces[i]);
			board.setPiece(blackPieces[i].getPosition(), blackPieces[i]);
		}
		
	}
	
	private Piece[] createBackRow(Color color, int row) {
		return new Piece[] {
			new Rook(color, new Position(row, 0)),
			new Knight(color, new Position(row, 1)),
			new Bishop(color, new Position(row, 2)),
			new Queen(color, new Position(row, 3)),
			new King(color, new Position(row, 4)),
			new Bishop(color, new Position(row, 5)),
			new Knight(color, new Position(row, 6)),
			new Rook(color, new Position(row, 7))
		};
	}
}
