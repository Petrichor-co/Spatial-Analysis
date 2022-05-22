package Kmeans;


public class Tuple {
    float x;
    float y;
    public Tuple(){}

    public Tuple(float a,float b){
        this.x=a;
        this.y=b;
    }

    public void setXY(float a,float b){
        this.x=a;
        this.y=b;
    }

    public void setX(float a){
        this.x=a;
    }

    public void setY(float a){
        this.y=a;
    }

    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }
}