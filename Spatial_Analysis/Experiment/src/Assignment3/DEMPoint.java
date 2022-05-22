package Assignment3;

/**
 * 格网点数据
 * @author ASUS
 *
 */

public class DEMPoint {
	//private int id;
	public double x;
	public double y;
	public double h;
	
	public DEMPoint(double x, double y, double h) {
		//super();
		//this.id = id;
		this.x = x;
		this.y = y;
		this.h = h;
	}

	public DEMPoint(double x, double y) {
		//super();
		this.x = x;
		this.y = y;
	}

//	public int getId() {
//		return id;
//	}

//	public void setId(int id) {
//		this.id = id;
//	}

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

	public double getH() {
		return h;
	}

	public void setH(double h) {
		this.h = h;
	}

	@Override
	public String toString() {
		return x + "   " + y + "   " + h;
	}
}
