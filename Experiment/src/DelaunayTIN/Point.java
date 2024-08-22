package DelaunayTIN;

public class Point {

    public double X = 0;
    public double Y = 0;

    public Point(){}

    /**
     * 构造方法
     * @param X
     * @param Y
     */
    public Point(double X, double Y){
        this.X = X;
        this.Y = Y;
    }

    public boolean equals(Point mPoint){
       if(this.X == mPoint.getX() && this.Y == mPoint.getY())
           return true;
       else
           return false;
    }

    public double getX() {
        return X;
    }

    public void setX(double x) {
        X = x;
    }

    public double getY() {
        return Y;
    }

    public void setY(double y) {
        Y = y;
    }
}
