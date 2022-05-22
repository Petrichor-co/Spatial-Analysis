package lineBuffer;

public class Topology {
	/**
	 * ��ȡ���޽�
	 **/
	public static double getQuadrantAngle(LatLng preCoord, LatLng nextCoord) {
		return getQuadrantAngle(nextCoord.getLatitude() - preCoord.getLatitude(),
				nextCoord.getLongitude() - preCoord.getLongitude());
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
	public static double getIncludedAngel(LatLng preCoord, LatLng midCoord, LatLng nextCoord) {
		double innerProduct = (midCoord.getLatitude() - preCoord.getLatitude())
				* (nextCoord.getLatitude() - midCoord.getLatitude())
				+ (midCoord.getLongitude() - preCoord.getLongitude())
						* (nextCoord.getLongitude() - midCoord.getLongitude());
		double mode1 = Math.sqrt(Math.pow((midCoord.getLatitude() - preCoord.getLatitude()), 2.0)
				+ Math.pow((midCoord.getLongitude() - preCoord.getLongitude()), 2.0));
		double mode2 = Math.sqrt(Math.pow((nextCoord.getLatitude() - midCoord.getLatitude()), 2.0)
				+ Math.pow((nextCoord.getLongitude() - midCoord.getLongitude()), 2.0));
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
	public static double getDistance(LatLng preCoord, LatLng nextCoord) {
		return Math.sqrt(Math.pow((nextCoord.getLatitude() - preCoord.getLatitude()), 2)
				+ Math.pow((nextCoord.getLongitude() - preCoord.getLongitude()), 2));
	}
}
