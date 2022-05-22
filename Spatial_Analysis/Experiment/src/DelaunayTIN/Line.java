package DelaunayTIN;

import java.awt.*;


public class Line{
    /**
     * 起点
     */
    private Point startPoint;

    /**
     * 终点
     */
    private Point endPoint;

    /**
     * 使用次数
     */
    private int useCount;

    public Line(){}

    /**
     * @param startPoint
     * @param endPoint
     */
    public Line(Point startPoint , Point endPoint){
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.useCount = 0;
    }

    /**
     * 绘制当前线
     * @param g
     * @param xCenter
     * @param yCenter
     * @param scaleSize
     * @param width
     * @param height
     */
    public void draw(Graphics g , double xCenter, double yCenter, double scaleSize, int width, int height){
        g.setColor(new Color(0,255,255));
        g.drawLine((int)((startPoint.getX()-xCenter)/scaleSize+width/2),
                (int)((startPoint.getY()-yCenter)/scaleSize+height/2),
                (int)((endPoint.getX()-xCenter)/scaleSize+width/2),
                (int)((endPoint.getY()-yCenter)/scaleSize+height/2));
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Line)) return false;
        Line mLine = (Line) o;
        Point mStartPoint = mLine.getStartPoint();
        Point mEndPoint = mLine.getEndPoint();
        if((this.startPoint.equals(mStartPoint)&&this.endPoint.equals(mEndPoint))||
                (this.startPoint.equals(mEndPoint)&&this.endPoint.equals(mStartPoint)))
        {
            return true;
        }
        else
            return false;

    }

    @Override
    public int hashCode() {
        int result = startPoint.hashCode();
        result = 31 * result + endPoint.hashCode();
        return result;
    }

    public int getUseCount(){
        return this.useCount;
    }

    public void addUseCount(){
        this.useCount++;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }
}
