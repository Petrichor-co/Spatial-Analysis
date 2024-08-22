package AssignmentAll;

public class SlopePoint {
	private double x;
	private double y;
	private double slope;
	
	public SlopePoint(double slope) {
		super();
		this.slope=slope;
	}
	
	public SlopePoint(double x,double y,double slope) {
		super();
		this.x=x;
		this.y=y;
		this.slope=slope;
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

	public double getSlope() {
		return slope;
	}

	public void setSlope(double slope) {
		this.slope = slope;
	}

}
