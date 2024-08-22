package lineBuffer;

import java.util.Collections;
import java.util.List;

public class PolylineBuffer {
	public static String getLineBufferEdgeCoords(List<LatLng> coords, double radius) {
		// 参数处理
		// 分别生成左侧和右侧的缓冲区边界点坐标串
		String leftBufferCoords = getLeftBufferEdgeCoords(coords, radius);
		// 反序
		Collections.reverse(coords);
		String rightBufferCoords = getLeftBufferEdgeCoords(coords, radius);
		return leftBufferCoords + "," + rightBufferCoords;
	}

	/**
	 * 根据给定的一系列有顺序的坐标，逆时针生成轴线左侧的缓冲区边界点
	 *
	 * @param coords
	 *            一系列有顺序的坐标
	 * @param radius
	 *            缓冲区半径
	 * @return 缓冲区的边界坐标
	 */
	private static String getLeftBufferEdgeCoords(List<LatLng> coords, double radius) {
		// 参数处理
		if (coords.size() < 1)
			return "";

		// 计算时所需变量
		double alpha = 0.0;// 向量绕起始点沿顺时针方向旋转到X轴正半轴所扫过的角度
		double delta = 0.0;// 前后线段所形成的向量之间的夹角
		double l = 0.0;// 前后线段所形成的向量的叉积

		// 辅助变量
		StringBuilder strCoords = new StringBuilder();
		double startRadian = 0.0;
		double endRadian = 0.0;
		double beta = 0.0;
		double x = 0.0, y = 0.0;

		// 第一节点的缓冲区,在此程序中只生成2点
		{
			alpha = Topology.getQuadrantAngle(coords.get(0), coords.get(1));
			startRadian = alpha + Math.PI;
			endRadian = alpha + (3 * Math.PI) / 2;
			strCoords.append(getBufferCoordsByRadian(coords.get(0), startRadian, endRadian, radius));
		}

		// 中间节点
		for (int i = 1; i < coords.size() - 1; i++) {
			alpha = Topology.getQuadrantAngle(coords.get(i), coords.get(i + 1));
			delta = Topology.getIncludedAngel(coords.get(i - 1), coords.get(i), coords.get(i + 1));
			l = getVectorProduct(coords.get(i - 1), coords.get(i), coords.get(i + 1));
			if (l > 0) {
				startRadian = alpha + (3 * Math.PI) / 2 - delta;
				endRadian = alpha + (3 * Math.PI) / 2;
				if (strCoords.length() > 0)
					strCoords.append(",");
				strCoords.append(getBufferCoordsByRadian(coords.get(i), startRadian, endRadian, radius));
			} else if (l < 0) {
				beta = alpha - (Math.PI - delta) / 2;
				x = coords.get(i).getLatitude() + radius * Math.cos(beta);
				y = coords.get(i).getLongitude() + radius * Math.sin(beta);
				if (strCoords.length() > 0)
					strCoords.append(",");
				strCoords.append(x + "," + y);
			}
		}

		// 最后一个点，在此程序中只生成2点

		{
			alpha = Topology.getQuadrantAngle(coords.get(coords.size() - 2), coords.get(coords.size() - 1));
			startRadian = alpha + (3 * Math.PI) / 2;
			endRadian = alpha + 2 * Math.PI;
			if (strCoords.length() > 0)
				strCoords.append(",");
			strCoords.append(getBufferCoordsByRadian(coords.get(coords.size() - 1), startRadian, endRadian, radius));
		}
		return strCoords.toString();
	}

	/**
	 * 获取指定弧度范围之间的缓冲区圆弧拟合边界点
	 *
	 * @param center
	 *            指定拟合圆弧的原点
	 * @param startRadian
	 *            开始弧度
	 * @param endRadian
	 *            结束弧度
	 * @param radius
	 *            缓冲区半径
	 * @return 缓冲区的边界坐标
	 */
	private static String getBufferCoordsByRadian(LatLng center, double startRadian, double endRadian, double radius) {
		double gamma = Math.PI / 6;
		StringBuilder strCoords = new StringBuilder();
		double x = 0.0, y = 0.0;
		for (double phi = startRadian; phi <= endRadian + 0.000000000000001; phi += gamma) {
			x = center.getLatitude() + radius * Math.cos(phi);
			y = center.getLongitude() + radius * Math.sin(phi);
			if (strCoords.length() > 0)
				strCoords.append(",");
			strCoords.append(x + "," + y);
		}
		return strCoords.toString();
	}

	/**
	 * 获取相邻三个点所形
	 *
	 * @param preCoord
	 *            第一个节点坐标成的两个向量的交叉乘积
	 * @param midCoord
	 *            第二个节点坐标
	 * @param nextCoord
	 *            第三个节点坐标
	 * @return 相邻三个点所形成的两个向量的交叉乘积
	 */
	private static double getVectorProduct(LatLng preCoord, LatLng midCoord, LatLng nextCoord) {
		return (midCoord.getLatitude() - preCoord.getLatitude()) * (nextCoord.getLongitude() - midCoord.getLongitude())
				- (nextCoord.getLatitude() - midCoord.getLatitude())
						* (midCoord.getLongitude() - preCoord.getLongitude());
	}

}
