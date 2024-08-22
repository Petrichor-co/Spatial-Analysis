package Assignment3;

/**
 * 原始数据
 * @author ASUS
 *
 */
public class point {

	private double x;
	private double y;
	private double h;
	
	public point(double x, double y, double h) {
		super();
		this.x = x;
		this.y = y;
		this.h = h;
	}

	public point(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getH() {
		return h;
	}

	public void setH(double h) {
		this.h = h;
	}

	public String toString() {
		return "Point [x=" + x + ", y=" + y + ", h=" + h + "]";
	}
	
	
}