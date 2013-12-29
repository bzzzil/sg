package util;

public class Coordinate {
	private int x;
    private int y;

	public Coordinate() {

	}

	public Coordinate(int x, int y) {
		set(x, y);
	}

	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void set(Coordinate location) {
		this.x = location.getX();
		this.y = location.getY();
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public String toString() {
		return "("+getX()+", "+getY()+")";
	}
}
