package util;

public class Coordinate {
	private double x;
    private double y;

	public Coordinate() {

	}

	public Coordinate(double x, double y) {
		set(x, y);
	}

	public void set(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void set(Coordinate location) {
		this.x = location.getX();
		this.y = location.getY();
	}

	public double getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public String toString() {
		return "("+getX()+", "+getY()+")";
	}
}
