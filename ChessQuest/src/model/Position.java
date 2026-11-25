package model;

import java.io.Serializable;

public class Position implements Serializable{
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
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!(obj instanceof Position)) return false;
		
		Position other = (Position) obj;
		return row == other.getRow()
				&& col == other.getCol();
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
}
