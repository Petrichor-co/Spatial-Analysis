package lineBuffer;

import java.awt.Point;

public class Topology {
	/**
	 * 获取象限角
	 **/
	public static double getQuadrantAngle(Point preCoord, Point nextCoord) {
		return getQuadrantAngle(nextCoord.getY() - preCoord.getY(),
				nextCoord.getX() - preCoord.getX());
	}

	/**
	 * 
	 * 由增量X和增量Y所形成的向量的象限角度
	 *
	 * @param x
	 *            增量X
	 * @param y
	 *            增量Y
	 * @return 象限角
	 */
	public static double getQuadrantAngle(double x, double y) {
		double theta = Math.atan(y / x);
		if (x > 0 && y > 0)
			return theta;
		if (x > 0 && y < 0)
			return Math.PI * 2 + theta;
		if (x < 0 && y > 0)
			return theta + Math.PI;
		if (x < 0 && y < 0)
			return theta + Math.PI;
		return theta;
	}

	/**
	 * 获取由相邻的三个点所形成的两个向量之间的夹角
	 */
	public static double getIncludedAngel(Point preCoord, Point midCoord, Point nextCoord) {
		double innerProduct = (midCoord.getY() - preCoord.getY())
				* (nextCoord.getY() - midCoord.getY())
				+ (midCoord.getX() - preCoord.getX())
						* (nextCoord.getX() - midCoord.getX());
		double mode1 = Math.sqrt(Math.pow((midCoord.getY() - preCoord.getY()), 2.0)
				+ Math.pow((midCoord.getX() - preCoord.getX()), 2.0));
		double mode2 = Math.sqrt(Math.pow((nextCoord.getY() - midCoord.getY()), 2.0)
				+ Math.pow((nextCoord.getX() - midCoord.getX()), 2.0));
		return Math.acos(innerProduct / (mode1 * mode2));
	}

	/**
	 * 获取由两个点所形成的向量的模(长度)
	 *
	 * @param preCoord
	 *            第一个点
	 * @param nextCoord
	 *            第二个点
	 * @return 由两个点所形成的向量的模(长度)
	 */
	public static double getDistance(Point preCoord, Point nextCoord) {
		return Math.sqrt(Math.pow((nextCoord.getY() - preCoord.getY()), 2)
				+ Math.pow((nextCoord.getX() - preCoord.getX()), 2));
	}
}
