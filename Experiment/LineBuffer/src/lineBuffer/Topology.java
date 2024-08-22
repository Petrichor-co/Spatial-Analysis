package lineBuffer;

public class Topology {
	/**
	 * 获取象限角
	 **/
	public static double getQuadrantAngle(LatLng preCoord, LatLng nextCoord) {
		return getQuadrantAngle(nextCoord.getLatitude() - preCoord.getLatitude(),
				nextCoord.getLongitude() - preCoord.getLongitude());
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
	 * 获取由两个点所形成的向量的模(长度)
	 *
	 * @param preCoord
	 *            第一个点
	 * @param nextCoord
	 *            第二个点
	 * @return 由两个点所形成的向量的模(长度)
	 */
	public static double getDistance(LatLng preCoord, LatLng nextCoord) {
		return Math.sqrt(Math.pow((nextCoord.getLatitude() - preCoord.getLatitude()), 2)
				+ Math.pow((nextCoord.getLongitude() - preCoord.getLongitude()), 2));
	}
}
