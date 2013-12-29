package util;

public class Coordinate3 extends Coordinate {
	protected int z;

	public Coordinate3() {

	}

	public void set(int x, int y, int z) {
		super.set(x, y);
		this.z = z;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}
}
