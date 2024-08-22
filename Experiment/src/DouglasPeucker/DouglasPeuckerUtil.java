package DouglasPeucker;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class DouglasPeuckerUtil {
 
    public static List<Point> DouglasPeucker(List<Point> points, int epsilon) {
        // �ҵ������ֵ�㣬��������1��
        double maxH = 0;
        int index = 0;
        int end = points.size();
        for (int i = 1; i < end - 1; i++) {
            double h = H(points.get(i), points.get(0), points.get(end - 1));
            if (h > maxH) {
                maxH = h;
                index = i;
            }
        }
 
        // ������������ֵ�㣬�ͽ��еݹ���������������ֵ��
        List<Point> result = new ArrayList<>();
        if (maxH > epsilon) {
            List<Point> leftPoints = new ArrayList<>();// ������
            List<Point> rightPoints = new ArrayList<>();// ������
            // �ֱ���ȡ�������ߺ������ߵ������
            for (int i = 0; i < end; i++) {
                if (i <= index) {
                    leftPoints.add(points.get(i));
                    if (i == index)
                        rightPoints.add(points.get(i));
                } else {
                    rightPoints.add(points.get(i));
                }
            }
 
            // �ֱ𱣴����߱����Ľ��
            List<Point> leftResult = new ArrayList<>();
            List<Point> rightResult = new ArrayList<>();
            leftResult = DouglasPeucker(leftPoints, epsilon);
            rightResult = DouglasPeucker(rightPoints, epsilon);
 
            // �����ߵĽ������
            rightResult.remove(0);//�Ƴ��ظ���
            leftResult.addAll(rightResult);
            result = leftResult;
        } else {// ��������������ֵ���򷵻ص�ǰ�����������ߵ���ʼ��
            result.add(points.get(0));
            result.add(points.get(end - 1));
        }
        return result;
    }
 
    /**
     * ����㵽ֱ�ߵľ���
     * 
     * @param p
     * @param s
     * @param e
     * @return
     */
    public static double H(Point p, Point s, Point e) {
        double AB = distance(s, e);
        double CB = distance(p, s);
        double CA = distance(p, e);
 
        double S = helen(CB, CA, AB);
        double H = 2 * S / AB;
 
        return H;
    }
 
    /**
     * ��������֮��ľ���
     * 
     * @param p1
     * @param p2
     * @return
     */
    public static double distance(Point p1, Point p2) {
        double x1 = p1.x;
        double y1 = p1.y;
 
        double x2 = p2.x;
        double y2 = p2.y;
 
        double xy = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        return xy;
    }
 
    /**
     * ���׹�ʽ����֪���������������
     * 
     * @param cB
     * @param cA
     * @param aB
     * @return ���
     */
    public static double helen(double CB, double CA, double AB) {
        double p = (CB + CA + AB) / 2;
        double S = Math.sqrt(p * (p - CB) * (p - CA) * (p - AB));
        return S;
    }

}
