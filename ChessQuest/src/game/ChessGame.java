package game;

import java.util.ArrayList;
import java.util.List;

import model.Board;
import model.Color;
import model.Position;
import model.piece.Bishop;
import model.piece.King;
import model.piece.Knight;
import model.piece.Pawn;
import model.piece.Piece;
import model.piece.PieceType;
import model.piece.Queen;
import model.piece.Rook;

public class ChessGame {
	public static final int BOARD_WIDTH = 8;
	public static final int BOARD_HEIGHT = 8;
	
	private Board board;
	private Color currentTurn = Color.WHITE;
	private boolean gameOver = false;
	private Position pendingPromotion;
	
	public ChessGame() {
		board = new Board();
		initializeBoard();
	}
	
	private void initializeBoard() {
		// 폰 배치
		for(int col = 0; col < BOARD_WIDTH; col++) {
			Pawn whitePawn = new Pawn(Color.WHITE, new Position(BOARD_HEIGHT - 2, col));
			Pawn blackPawn = new Pawn(Color.BLACK, new Position(1, col));
			
			board.setPiece(whitePawn.getPosition(), whitePawn);
			board.setPiece(blackPawn.getPosition(), blackPawn);
		}
		
		Piece[] whitePieces = createBackRow(Color.WHITE, BOARD_HEIGHT - 1);
		Piece[] blackPieces = createBackRow(Color.BLACK, 0);
		
		// 후열 배치
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

	private void changeTurn() {
		currentTurn = (currentTurn == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private void finishTurn() {
		changeTurn();
		
		if (RuleManager.isCheckmate(board, currentTurn)) {
			gameOver = true;
			// 체크메이트
		} else if (RuleManager.isStalemate(board, currentTurn)) {
			gameOver = true;
			// 스테일메이트
		}
	}
	
	private boolean undoMove() {
		if(!board.undoMove()) {
			return false;
		}
		
		changeTurn();
		
		return true;
	}
	
	private boolean isPromotion(Position position) {
		Piece piece = board.getPiece(position);
		
		if(!(piece instanceof Pawn)) {
			return false;
		}
		
		switch(piece.getColor()) {
			case Color.WHITE:
				return position.getRow() == 0;
			case Color.BLACK:
				return position.getRow() == BOARD_HEIGHT - 1;
			default:
				return false;
		}
	}
	
	public Color getCurrentTurn() {
		return currentTurn;
	}
	
	// 특정 좌표에 위치한 기물 획득	
	public Piece getPiece(Position position) {
		return board.getPiece(position);
	}
	
	// 특정 좌표의 기물이 이동할 수 있는 좌표를 가져오는 함수
	public List<Position> getPossibleMoves(Position position) {
		Piece piece = getPiece(position);
		
		if(piece == null) {
			return new ArrayList<>();
		}
		
		return piece.getPossibleMoves(board);
	}
	
	// 특정 좌표의 기물을 이동시키는 함수
	public void movePiece(Position from, Position to) {
		board.movePiece(from, to);
			if(isPromotion(to)) {
				pendingPromotion = to;
				return;
			}
		
		finishTurn();
	}
	
	// 프로모션 타입을 선택하는 것
	public void promotionPiece(PieceType type) {
		if(pendingPromotion == null) {
			return;
		}
		
		board.promote(pendingPromotion, type);
		pendingPromotion = null;
		
		finishTurn();
	}
	
	// 현재 턴이 맞는지 체크하는 함수
	public boolean isSelectable(Position position) {
		Piece piece = getPiece(position);
		
		return piece != null
				&& piece.getColor() == currentTurn;
	}
	
	// UI에서 사용 가능한 턴 무르기
	public void undoTurn() {
		if(!undoMove()) return;
		if(!undoMove()) return;
	}
}
