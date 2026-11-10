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

	/**
	 * 특정 좌표에 위치한 기물을 반환한다.
	 *
	 * @param position 조회할 기물의 위치
	 * @return 해당 위치의 기물, 기물이 없다면 null
	 */
	public Piece getPiece(Position position) {
		return board.getPiece(position);
	}
	
	/**
	 * 특정 좌표에 위치한 기물의 이동가능한 좌표들을 반환한다.
	 * 
	 * @param position 조회할 기물의 위치
	 * @return 해당 위치의 기물이 이동가능한 좌표 목록, 없다면 빈 리스트
	 */
	public List<Position> getPossibleMoves(Position position) {
		Piece piece = getPiece(position);
		
		if(piece == null) {
			return new ArrayList<>();
		}
		
		return piece.getPossibleMoves(board);
	}
	
	/**
	 * 한 좌표에서 다른 좌표로 기물을 이동시킨다.
	 * 
	 * @param from 기물이 위치할 첫 번째 좌표
	 * @param to 기물이 이동할 두 번째 좌표
	 */
	public void movePiece(Position from, Position to) {
		board.movePiece(from, to);
			if(isPromotion(to)) {
				pendingPromotion = to;
				return;
			}
		
		finishTurn();
	}
	
	/**
	 * 프로모션 타입을 받고, 폰을 프로모션시킨다.
	 * 
	 * @param type 프로모션할 기물의 타입
	 */
	public void promotionPiece(PieceType type) {
		if(pendingPromotion == null) {
			return;
		}
		
		board.promote(pendingPromotion, type);
		pendingPromotion = null;
		
		finishTurn();
	}
	
	/**
	 * 현재 턴이 맞는지 체크한다.
	 * 
	 * @param position 움직이고자하는 기물의 위치
	 * @return 기물의 이동가능 여부 판단
	 */
	public boolean isSelectable(Position position) {
		Piece piece = getPiece(position);
		
		return piece != null
				&& piece.getColor() == currentTurn;
	}
	
	/**
	 * 한 턴 뒤로 무른다.
	 */
	public void undoTurn() {
		if(!undoMove()) return;
		if(!undoMove()) return;
	}
}
