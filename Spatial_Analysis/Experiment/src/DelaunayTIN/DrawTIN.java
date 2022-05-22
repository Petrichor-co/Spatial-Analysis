package DelaunayTIN;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DrawTIN {
    /**
     * DEM�����
     */
    private Point[] DEMPoints;
    /**
     * ��Ļ��ͼ����
     */
    private double xCenter;
    private double yCenter;
    /**
     * ��Ļ��͸�
     */
    private int width;
    private int height;
    /**
     * ��ͼ����
     */
    private double scaleSize;
    /**
     * �������ߵļ���
     */
    private List<Line> lineList;
    /**
     * �����μ���
     */
    private List<Triangle> triangleList;
    /**
     * ����ʵ��
     */
    private static DrawTIN mInstance=null;
    /**
     * ������
     */
    private MyProgressBar myProgressBar;

    /**
     * ����ģʽ
     * @param DEMPoints
     * @param xCenter
     * @param yCenter
     * @param scaleSize
     * @param width
     * @param height
     * @return ����ʵ��
     */
    public static DrawTIN newInstance(Point[] DEMPoints, double xCenter, double yCenter, double scaleSize, int width, int height){
        if(mInstance == null){
            mInstance = new DrawTIN(DEMPoints,xCenter,yCenter,scaleSize,width,height);
        }
        return mInstance ;
    }

    /**
     * ˽�й��췽��
     * @param DEMPoints
     * @param xCenter
     * @param yCenter
     * @param scaleSize
     * @param width
     * @param height
     */
    private DrawTIN(Point[] DEMPoints, double xCenter, double yCenter , double scaleSize, int width, int height){
        this.DEMPoints = DEMPoints;
        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.scaleSize = scaleSize;
        this.height=height;
        this.width = width;
        lineList = new ArrayList<Line>();
        triangleList = new ArrayList<Triangle>();
    }

    /**
     * ���㲢����������
     * @param g ��ͼ����
     * @param xCenter
     * @param yCenter
     * @param scaleSize
     * @param width
     * @param height
     */
    public  void draw(Graphics g, double xCenter, double yCenter , double scaleSize, int width, int height){

        //�����ɫΪ��ɫ
        g.setColor(new Color(0,0,255));
        //�������еıߣ������γ�������
        for(int j = 0 ; j<lineList.size(); j++){
            lineList.get(j).draw(g,xCenter,yCenter,scaleSize,width,height);
        }

    }

    /**
     * ��TIN�����߳��б�����
     */
    public void getTIN(){

        myProgressBar = new MyProgressBar();
        myProgressBar.setStartTime(System.currentTimeMillis());

        Point firstPoint = DEMPoints[(int)(DEMPoints.length/2)];
        Point secondPoint = getNearestPoint(firstPoint);

        Line firstLine = new Line(firstPoint,secondPoint);
        lineList.add(firstLine);

        myProgressBar.update(1,System.currentTimeMillis());

        int i = 0;
        while(true) {
            Line activeLine = null;

            //Ѱ�ҵ�һ�����õı�
            for (; i < lineList.size(); i++) {
                activeLine = lineList.get(i);
                if (activeLine.getUseCount() < 2) break;
            }
            if (i >= lineList.size()) {
                myProgressBar.update(100,System.currentTimeMillis());
                break;
            }
            Point bestPoint = this.findBestPointByAngle(activeLine);

            //�������Ĺ��㻹δ���
            myProgressBar.update(10,System.currentTimeMillis());

            //û���ҵ����ŵ㣬��ñ�ʹ�ô�����1���ñ߽���Ϊ�����ñ�
            if (bestPoint == null) activeLine.addUseCount();

        }
    }

    /**
     * �����Žǵ�����Ѱ�����ŵ�
     * @param activeLine ��ǰ�Ŀ��ñ�
     * @return ���ŵ�
     */
    public Point findBestPointByAngle(Line activeLine) {
        Point bestPoint = null;
        for (int i = 0; i < DEMPoints.length; i++) {
            Point activePoint = DEMPoints[i];
            //�жϵ�ǰ����ǲ�����ֱ�ߵ����
            if (this.isOnLeft(activePoint, activeLine))
            {
                //��ֱ�����
                double minCos = this.getCos(activePoint, activeLine);
                int best = i;
                for (int j = i + 1; j < DEMPoints.length; j++) {
                    //Ѱ����������ֵ��С�ĵ���Ϊ���ŵ�
                    if (this.isOnLeft(DEMPoints[j], activeLine))
                    {
                        double cos = this.getCos(DEMPoints[j], activeLine);
                        if (cos < minCos) {
                            minCos = cos;
                            best = j;
                        }
                    }

                }
                //�õ����ŵ�
                bestPoint = DEMPoints[best];

                //���ɵ�һ����
                Line firstLine = new Line(activeLine.getStartPoint(), bestPoint);
                firstLine.addUseCount();
                if (lineList.contains(firstLine)) {
                    for (int k = 0; k < lineList.size(); k++) {
                        if (lineList.get(k).equals(firstLine)) {
                            lineList.get(k).addUseCount();
                            break;
                        }
                    }
                }
                else
                    lineList.add(firstLine);

                //���ɵڶ�����
                Line secondLine = new Line(bestPoint, activeLine.getEndPoint());
                secondLine.addUseCount();
                if (lineList.contains(secondLine)) {
                    for (int k = 0; k < lineList.size(); k++) {
                        if (lineList.get(k).equals(secondLine)) {
                            lineList.get(k).addUseCount();
                            break;
                        }
                    }

                } else
                    lineList.add(secondLine);

                //����ǰ��
                activeLine.addUseCount();
                trunStartAndEnd(activeLine);

                //��¼������
                Triangle triangle = new Triangle(activeLine,firstLine,secondLine);
                triangleList.add(triangle);

                break;
            }
        }
        return bestPoint;
    }

    /**
     * �ı�ߵķ��򣬼�������ʼ��
     * @param currentLine
     */
    private void trunStartAndEnd(Line currentLine){
        Point temp=currentLine.getStartPoint();
        currentLine.setStartPoint(currentLine.getEndPoint());
        currentLine.setEndPoint(temp);
    }

    /**
     *
     * @param currentPoint ��ǰ��
     * @param currentLine  ��ǰ��
     * @return ����ֵ
     */
    private double getCos(Point currentPoint ,Line currentLine)
    {
        Point startPoint = currentLine.getStartPoint();
        Point endPoint = currentLine.getEndPoint();

        double a2 = Math.pow(startPoint.getX()-currentPoint.getX(),2)+
                Math.pow(startPoint.getY()-currentPoint.getY(),2);

        double b2 = Math.pow(endPoint.getX()-currentPoint.getX(),2)+
                Math.pow(endPoint.getY()-currentPoint.getY(),2);

        double c2 = Math.pow(endPoint.getX()-startPoint.getX(),2)+
                Math.pow(endPoint.getY()-startPoint.getY(),2);

        double cosC = (a2+b2-c2)/(2*Math.sqrt(a2)*Math.sqrt(b2));

        return cosC;
    }

    /**
     * �õ��ﵱǰ������ĵ�
     * @param currentPoint
     * @return
     */
    private  Point getNearestPoint(Point currentPoint){
        double nearestDistance = Double.MAX_VALUE;
        int temp=0;
        for(int i=0; i<DEMPoints.length; i++){
            if(DEMPoints[i].X == currentPoint.X && DEMPoints[i].Y == currentPoint.Y){
                continue;
            }
            if(nearestDistance >= Math.sqrt((DEMPoints[i].X-currentPoint.X)*(DEMPoints[i].X-currentPoint.X)+
                    (DEMPoints[i].Y-currentPoint.Y)*(DEMPoints[i].Y-currentPoint.Y))){
                nearestDistance = Math.sqrt((DEMPoints[i].X-currentPoint.X)*(DEMPoints[i].X-currentPoint.X)+
                        (DEMPoints[i].Y-currentPoint.Y)*(DEMPoints[i].Y-currentPoint.Y));
                temp = i;
            }
        }
        return DEMPoints[temp];
    }

    /**
     * �жϵ�ǰ���Ƿ��ڵ�ǰֱ�ߵ����
     * @param currentPoint
     * @param currentLine
     * @return
     */
    public boolean isOnLeft(Point currentPoint , Line currentLine){

        Point startPoint = new Point();
        startPoint=currentLine.getStartPoint();
        Point endPoint =new Point();
        endPoint = currentLine.getEndPoint();

        double value=(startPoint.getY()-endPoint.getY())*currentPoint.getX()+(endPoint.getX()-startPoint.getX())
                *currentPoint.getY()-endPoint.getX()*startPoint.getY()+endPoint.getY()*startPoint.getX();

        if(value < 0)
            return true;
        else
            return false;
    }

}
