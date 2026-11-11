package model;

import java.util.Stack;

import game.ChessGame;
import model.piece.Bishop;
import model.piece.King;
import model.piece.Knight;
import model.piece.Pawn;
import model.piece.Piece;
import model.piece.PieceType;
import model.piece.Queen;
import model.piece.Rook;

public class Board {
	private Piece[][] board;
	private Stack<Move> moveHistory = new Stack<>();
	private Piece enPassantPawn;
	
	public Board() {
		board = new Piece[ChessGame.BOARD_HEIGHT][ChessGame.BOARD_WIDTH];
	}
	
	public Piece getPiece(Position position) {
		return board[position.getRow()][position.getCol()];
	}
	
	public boolean hasPiece(Position position) {
		return getPiece(position) != null;
	}
	
	public void setPiece(Position position, Piece piece) {
		board[position.getRow()][position.getCol()] = piece;
		
		if(piece != null) {
			piece.setPosition(position);
		}
	}
	
	public Piece[][] getBoard() {
		return board;
	}
	
	public void removePiece(Position position) {
        board[position.getRow()][position.getCol()] = null;
    }
	
	public void movePiece(Position from, Position to) {
		Piece piece = getPiece(from);
		
		if(piece == null) {
			throw new IllegalArgumentException("해당 위치에 기물이 없습니다.");
		}
		
		Piece capturedPiece;
		Piece previousEnPassant = null;
		if(isEnPassantMove(piece, to)) {
			capturedPiece = enPassantPawn;
			previousEnPassant = enPassantPawn;
			removePiece(enPassantPawn.getPosition());
		} else {
			capturedPiece = getPiece(to);
			removePiece(to);
		}
		
		removePiece(from);
		
		enPassantPawn = null;
		if(canEnPassant(piece, to)) {
			enPassantPawn = piece;
		}
		
		Move move = new Move(from, to, capturedPiece);
		
		move.setPreviousEnPassant(previousEnPassant);
		setPiece(to, piece);
		moveHistory.add(move);
	}
	
	public boolean undoMove() {
		if(moveHistory.isEmpty()) {
			return false;
		}
		
		Move move = moveHistory.pop();
		Piece piece = getPiece(move.getTo());
		
		if(move.getPromotionType() != null) {
			piece = move.getOriginalPiece();
		}
		
		setPiece(move.getFrom(), piece);
		removePiece(move.getTo());
		
		if(move.getCapturedPiece() != null) {
			Piece capturedPiece = move.getCapturedPiece();
			setPiece(capturedPiece.getPosition(), capturedPiece);
		}
		
		// 앙파상 여부 복구
		if(enPassantPawn != null) {
			enPassantPawn = null;
		}
		
		if(move.getPreviousEnPassant() != null) {
			enPassantPawn = move.getPreviousEnPassant();
		}
		
		return true;
	}
	
	public void promote(Position position, PieceType pieceType) {
		Piece pawn = getPiece(position);
		
		if(!(pawn instanceof Pawn)) {
			throw new IllegalArgumentException("폰이 아닙니다.");
		}
		
		Move move = moveHistory.peek();
		
		move.setOriginalPiece(pawn);
		move.setPromotionType(pieceType);
		
		Piece promotionPiece = createPiece(pieceType, pawn.getColor(), position);
		setPiece(position, promotionPiece);
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
	
	public King getKing(Color color) {
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				Piece piece = board[row][col];
				
				if(piece instanceof King && piece.getColor() == color) {
					return (King) piece;
				}
			}
		}
		
		return null;
	}
	
	public boolean isInside(int row, int col) {
	    return row >= 0 && row < ChessGame.BOARD_HEIGHT
	        && col >= 0 && col < ChessGame.BOARD_WIDTH;
	}
	
	private boolean canEnPassant(Piece piece, Position to) {
		return piece instanceof Pawn && 
				Math.abs(to.getRow() - piece.getPosition().getRow()) == 2;
		
	}
	
	private boolean isEnPassantMove(Piece piece, Position to) {
		if(enPassantPawn != null &&														// 앙파상한 기물이 있어야 한다.
				piece instanceof Pawn &&												// 움직인 기물이 폰이어야 한다.
				piece.getAttackPositions(this).contains(to) &&							// 그 폰이 공격해야한다.
				piece.getPosition().getRow() == enPassantPawn.getPosition().getRow() &&	// 폰과 앙파상 폰이 같은 열에 있어야 한다.
				to.getCol() == enPassantPawn.getPosition().getCol()) {					// 목적지가 앙파상 폰과 같은 행에 위치해야 한다.
			return true;
		}
		
		return false;
	}
	
	public Piece getEnPassantPawn() {
		return enPassantPawn;
	}
	
	
}
