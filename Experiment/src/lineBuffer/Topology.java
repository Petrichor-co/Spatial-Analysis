package lineBuffer;

import java.awt.Point;

public class Topology {
	/**
	 * ��ȡ���޽�
	 **/
	public static double getQuadrantAngle(Point preCoord, Point nextCoord) {
		return getQuadrantAngle(nextCoord.getY() - preCoord.getY(),
				nextCoord.getX() - preCoord.getX());
	}

	/**
	 * 
	 * ������X������Y���γɵ����������޽Ƕ�
	 *
	 * @param x
	 *            ����X
	 * @param y
	 *            ����Y
	 * @return ���޽�
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
	 * ��ȡ�����ڵ����������γɵ���������֮��ļн�
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
	 * ��ȡ�����������γɵ�������ģ(����)
	 *
	 * @param preCoord
	 *            ��һ����
	 * @param nextCoord
	 *            �ڶ�����
	 * @return �����������γɵ�������ģ(����)
	 */
	public static double getDistance(Point preCoord, Point nextCoord) {
		return Math.sqrt(Math.pow((nextCoord.getY() - preCoord.getY()), 2)
				+ Math.pow((nextCoord.getX() - preCoord.getX()), 2));
	}
}
