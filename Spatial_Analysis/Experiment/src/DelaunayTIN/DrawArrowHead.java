package DelaunayTIN;

import java.awt.*;

public class DrawArrowHead {

    /**
     * ���Ƽ�ͷ
     * @param sx ���X
     * @param sy ���Y
     * @param ex �յ�X
     * @param ey �յ�Y
     * @param g ����
     */
    public static void drawAL(int sx, int sy, int ex, int ey, Graphics g)
    {
        // ��ͷ�߶�
        double H = 10;
        // �ױߵ�һ��
        double L = 4;
        int x3 = 0;
        int y3 = 0;
        int x4 = 0;
        int y4 = 0;
        g.setColor(new Color(255,255,255));
        // ��ͷ�Ƕ�
        double awrad = Math.atan(L / H);
        // ��ͷ�ĳ���
        double arraow_len = Math.sqrt(L * L + H * H);
        double[] arrXY_1 = rotateVec(ex - sx, ey - sy, awrad, true, arraow_len);
        double[] arrXY_2 = rotateVec(ex - sx, ey - sy, -awrad, true, arraow_len);
        // (x3,y3)�ǵ�һ�˵�
        double x_3 = ex - arrXY_1[0];
        double y_3 = ey - arrXY_1[1];
        // (x4,y4)�ǵڶ��˵�
        double x_4 = ex - arrXY_2[0];
        double y_4 = ey - arrXY_2[1];

        Double X3 = new Double(x_3);
        x3 = X3.intValue();
        Double Y3 = new Double(y_3);
        y3 = Y3.intValue();
        Double X4 = new Double(x_4);
        x4 = X4.intValue();
        Double Y4 = new Double(y_4);
        y4 = Y4.intValue();
        //����
        g.drawLine(sx, sy, ex, ey);

        //����ͷ
        int[] xPoints=new int[]{ex,x3,x4};
        int[] yPoints=new int[]{ey,y3,y4};
        g.drawPolygon(xPoints,yPoints,3);

    }

    /**
     *
     * @param px
     * @param py
     * @param ang
     * @param isChLen
     * @param newLen
     * @return
     */
    public static double[] rotateVec(int px, int py, double ang,
                                     boolean isChLen, double newLen) {

        double mathstr[] = new double[2];
        // ʸ����ת��������������ֱ���x������y��������ת�ǡ��Ƿ�ı䳤�ȡ��³���
        double vx = px * Math.cos(ang) - py * Math.sin(ang);
        double vy = px * Math.sin(ang) + py * Math.cos(ang);
        if (isChLen) {
            double d = Math.sqrt(vx * vx + vy * vy);
            vx = vx / d * newLen;
            vy = vy / d * newLen;
            mathstr[0] = vx;
            mathstr[1] = vy;
        }
        return mathstr;
    }
}
