package DelaunayTIN;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DrawTIN {
    /**
     * DEM坐标点
     */
    private Point[] DEMPoints;
    /**
     * 屏幕绘图中心
     */
    private double xCenter;
    private double yCenter;
    /**
     * 屏幕宽和高
     */
    private int width;
    private int height;
    /**
     * 绘图比例
     */
    private double scaleSize;
    /**
     * 三角网边的集合
     */
    private List<Line> lineList;
    /**
     * 三角形集合
     */
    private List<Triangle> triangleList;
    /**
     * 单例实例
     */
    private static DrawTIN mInstance=null;
    /**
     * 进度条
     */
    private MyProgressBar myProgressBar;

    /**
     * 单例模式
     * @param DEMPoints
     * @param xCenter
     * @param yCenter
     * @param scaleSize
     * @param width
     * @param height
     * @return 单例实例
     */
    public static DrawTIN newInstance(Point[] DEMPoints, double xCenter, double yCenter, double scaleSize, int width, int height){
        if(mInstance == null){
            mInstance = new DrawTIN(DEMPoints,xCenter,yCenter,scaleSize,width,height);
        }
        return mInstance ;
    }

    /**
     * 私有构造方法
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
     * 计算并绘制三角网
     * @param g 绘图画布
     * @param xCenter
     * @param yCenter
     * @param scaleSize
     * @param width
     * @param height
     */
    public  void draw(Graphics g, double xCenter, double yCenter , double scaleSize, int width, int height){

        //点的颜色为绿色
        g.setColor(new Color(0,0,255));
        //绘制所有的边，即可形成三角网
        for(int j = 0 ; j<lineList.size(); j++){
            lineList.get(j).draw(g,xCenter,yCenter,scaleSize,width,height);
        }

    }

    /**
     * 构TIN，在线程中被调用
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

            //寻找第一个可用的边
            for (; i < lineList.size(); i++) {
                activeLine = lineList.get(i);
                if (activeLine.getUseCount() < 2) break;
            }
            if (i >= lineList.size()) {
                myProgressBar.update(100,System.currentTimeMillis());
                break;
            }
            Point bestPoint = this.findBestPointByAngle(activeLine);

            //进度条的估算还未解决
            myProgressBar.update(10,System.currentTimeMillis());

            //没有找到最优点，则该边使用次数加1，该边将变为不可用边
            if (bestPoint == null) activeLine.addUseCount();

        }
    }

    /**
     * 基于张角的余弦寻找最优点
     * @param activeLine 当前的可用边
     * @return 最优点
     */
    public Point findBestPointByAngle(Line activeLine) {
        Point bestPoint = null;
        for (int i = 0; i < DEMPoints.length; i++) {
            Point activePoint = DEMPoints[i];
            //判断当前活动点是不是在直线的左边
            if (this.isOnLeft(activePoint, activeLine))
            {
                //在直线左边
                double minCos = this.getCos(activePoint, activeLine);
                int best = i;
                for (int j = i + 1; j < DEMPoints.length; j++) {
                    //寻找做边余弦值最小的点作为最优点
                    if (this.isOnLeft(DEMPoints[j], activeLine))
                    {
                        double cos = this.getCos(DEMPoints[j], activeLine);
                        if (cos < minCos) {
                            minCos = cos;
                            best = j;
                        }
                    }

                }
                //得到最优点
                bestPoint = DEMPoints[best];

                //生成第一条边
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

                //生成第二条边
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

                //处理当前边
                activeLine.addUseCount();
                trunStartAndEnd(activeLine);

                //记录三角形
                Triangle triangle = new Triangle(activeLine,firstLine,secondLine);
                triangleList.add(triangle);

                break;
            }
        }
        return bestPoint;
    }

    /**
     * 改变边的方向，即交换起始点
     * @param currentLine
     */
    private void trunStartAndEnd(Line currentLine){
        Point temp=currentLine.getStartPoint();
        currentLine.setStartPoint(currentLine.getEndPoint());
        currentLine.setEndPoint(temp);
    }

    /**
     *
     * @param currentPoint 当前点
     * @param currentLine  当前线
     * @return 余弦值
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
     * 得到里当前点最近的点
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
     * 判断当前点是否在当前直线的左侧
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
