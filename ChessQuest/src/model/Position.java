package model;

public class Position {
	private int row;
	private int col;
	
	public Position(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public void setPosition(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public void setPosition(Position pos) {
		if (pos == null) {
			throw new IllegalArgumentException("좌표값이 옳지 않습니다.");
		}
		
		setPosition(pos.row, pos.col);
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
}
