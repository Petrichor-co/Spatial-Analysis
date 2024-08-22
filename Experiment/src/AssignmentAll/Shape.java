package AssignmentAll;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Shape {

	private int x,y;
	private Point p1,p2;
    private int flag;
    private List<Point> li = new ArrayList<Point>();
    private List<Point> tri = new ArrayList<Point>();
    private List<Point> pol = new ArrayList<Point>();

    public Shape(int flag ,int x, int y,List<Point> li,List<Point> tri,List<Point> pol){
        this.x = x;
        this.y = y;
        this.flag = flag;
        this.li=li;
        this.tri=tri;
        this.pol=pol;
    }


    public void drawshape(Graphics g){
        switch(flag){
        case 1:
            
           
            break;
        case 2:
            
           
            break;
        case 3:
           
            
            break;
        }



    }
}
