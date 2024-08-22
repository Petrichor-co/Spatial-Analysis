package lineBuffer;

import java.util.Collections;
import java.util.List;

public class PolylineBuffer {
	public static String getLineBufferEdgeCoords(List<LatLng> coords, double radius) {
		// ��������
		// �ֱ����������Ҳ�Ļ������߽�����괮
		String leftBufferCoords = getLeftBufferEdgeCoords(coords, radius);
		// ����
		Collections.reverse(coords);
		String rightBufferCoords = getLeftBufferEdgeCoords(coords, radius);
		return leftBufferCoords + "," + rightBufferCoords;
	}

	/**
	 * ���ݸ�����һϵ����˳������꣬��ʱ�������������Ļ������߽��
	 *
	 * @param coords
	 *            һϵ����˳�������
	 * @param radius
	 *            �������뾶
	 * @return �������ı߽�����
	 */
	private static String getLeftBufferEdgeCoords(List<LatLng> coords, double radius) {
		// ��������
		if (coords.size() < 1)
			return "";

		// ����ʱ�������
		double alpha = 0.0;// ��������ʼ����˳ʱ�뷽����ת��X����������ɨ���ĽǶ�
		double delta = 0.0;// ǰ���߶����γɵ�����֮��ļн�
		double l = 0.0;// ǰ���߶����γɵ������Ĳ��

		// ��������
		StringBuilder strCoords = new StringBuilder();
		double startRadian = 0.0;
		double endRadian = 0.0;
		double beta = 0.0;
		double x = 0.0, y = 0.0;

		// ��һ�ڵ�Ļ�����,�ڴ˳�����ֻ����2��
		{
			alpha = Topology.getQuadrantAngle(coords.get(0), coords.get(1));
			startRadian = alpha + Math.PI;
			endRadian = alpha + (3 * Math.PI) / 2;
			strCoords.append(getBufferCoordsByRadian(coords.get(0), startRadian, endRadian, radius));
		}

		// �м�ڵ�
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

		// ���һ���㣬�ڴ˳�����ֻ����2��

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
	 * ��ȡָ�����ȷ�Χ֮��Ļ�����Բ����ϱ߽��
	 *
	 * @param center
	 *            ָ�����Բ����ԭ��
	 * @param startRadian
	 *            ��ʼ����
	 * @param endRadian
	 *            ��������
	 * @param radius
	 *            �������뾶
	 * @return �������ı߽�����
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
	 * ��ȡ��������������
	 *
	 * @param preCoord
	 *            ��һ���ڵ�����ɵ����������Ľ���˻�
	 * @param midCoord
	 *            �ڶ����ڵ�����
	 * @param nextCoord
	 *            �������ڵ�����
	 * @return �������������γɵ����������Ľ���˻�
	 */
	private static double getVectorProduct(LatLng preCoord, LatLng midCoord, LatLng nextCoord) {
		return (midCoord.getLatitude() - preCoord.getLatitude()) * (nextCoord.getLongitude() - midCoord.getLongitude())
				- (nextCoord.getLatitude() - midCoord.getLatitude())
						* (midCoord.getLongitude() - preCoord.getLongitude());
	}

}
