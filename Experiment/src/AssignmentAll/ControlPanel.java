package AssignmentAll;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.*;

import DouglasPeucker.DouglasPeuckerUtil;
import lineBuffer.*;

public class ControlPanel extends JPanel {
	Point gra1 = new Point();// n-1 个坐标和
	Point gra2 = new Point();// n 个坐标和
	Point gra = new Point();// 重心点坐标
	double perimeter1 = 0; // n-1 条边的边长和
	double perimeter = 0;
	double line = 0;
	int[][] location = new int[100][2];
	double sum = 0;
	double area = 0;
	public int flag = -1;
	JButton getArea = new JButton("面积");
	JButton getPerimeter = new JButton("周长");
	JButton lineLength = new JButton("边长");
	JButton gravitycenter = new JButton("重心");
	JButton dp = new JButton("DouglasPeucker");
	JButton pointBufferButton=new JButton("点缓冲区");
    JButton lineBufferButton=new JButton("线缓冲区");
    JButton polygonBufferButton=new JButton("面缓冲区");

    private Topology topology = new Topology();
    ArrayList<Points> linePoints = new ArrayList<>();
    ArrayList<Points> leftPoints = new ArrayList<>();
    ArrayList<Points> rightPoints = new ArrayList<>();
    ArrayList<Points> resultsPoints = new ArrayList<>();

    
	public static Shape[] shapearray = new Shape[1000000];

	private static final long serialVersionUID = 1L;

	public GraphicsFrame gf;

	public GraphicPanel gp;

	public ControlPanel(GraphicsFrame _gf) {

        super();

        gf = _gf;

        gp = gf.gp;


        ButtonGroup bg = new ButtonGroup();

        final JRadioButton pointsButton = new JRadioButton("Points", false);
        final JRadioButton linesButton = new JRadioButton("Lines", false);
        final JRadioButton brokenlinesButton = new JRadioButton("Brokenlines", false);
        final JRadioButton trianglesButton = new JRadioButton("Triangles", false);
        final JRadioButton polygonsButton = new JRadioButton("Polygons", false);
        
        //图形组
        bg.add(pointsButton);
        bg.add(linesButton);
        bg.add(brokenlinesButton);
        bg.add(trianglesButton);
        bg.add(polygonsButton);      

        setLayout(new GridLayout(20, 1, 2, 2));
        add(pointsButton);
        add(linesButton);
        add(brokenlinesButton);
        add(trianglesButton);
        add(polygonsButton);
        add(pointBufferButton);
        add(lineBufferButton);
        add(polygonBufferButton);
        add(getPerimeter);
        add(getArea);
        add(lineLength);
        add(gravitycenter);
        add(dp);
        //缓冲区组
        add(pointBufferButton);
        add(lineBufferButton);
        add(polygonBufferButton);
        
        gp.addMouseListener(new MouseListener() {//使用同一个frame框架绘图，分开一个frame一个panel会导致坐标不准
        	
        	List<Point> po = new ArrayList<Point>();
            List<Point> li = new ArrayList<Point>();
            List<Point> broli = new ArrayList<Point>();       
            List<Point> tri = new ArrayList<Point>();
            List<Point> pol = new ArrayList<Point>();
            
            List<Point> points = new ArrayList<>();
            List<Point> result = new ArrayList<>();
            Graphics g;

            @Override
            public void mouseClicked(MouseEvent e) {
                if (pointsButton.isSelected()) {//点按钮
                	po.add(e.getPoint());
                    int x, y;
                    x = e.getX();
                    y = e.getY();
                    Graphics g = gf.getGraphics();
                    g.setColor(Color.red);
                    g.fillOval(x+7, y+88, 2, 2);
                    System.out.println("x = "+x+"  y = "+y);
                    flag = 0;

                } else if (linesButton.isSelected()) {//线按钮

                    Point p1 = new Point();

                    Point p2 = new Point();

                    li.add(e.getPoint());

                    if (li.size() % 2 == 0) {

                        g = gf.getGraphics();

                        g.setColor(Color.red);

                        p1 = li.get(0);

                        p2 = li.get(1);

                        g.fillOval(e.getX()+7, e.getY()+88, 2, 2);//public abstract void fillOval(int x,int y,int width,int height)
                        
                        g.drawLine(p1.x+7, p1.y+88, p2.x+7, p2.y+88);

                        //repaint();
                        
                        line=Math.sqrt(Math.pow(p2.x-p1.x, 2)+Math.pow(p2.y-p1.y,2));
                        
                        flag=2;

                        li.clear();

                    } else {

                        g = gf.getGraphics();

                        g.setColor(Color.red);

                        g.fillOval(e.getX()+7, e.getY()+88, 2, 2);//fillOval画点

                    }
                } else if(brokenlinesButton.isSelected()) {//折线按钮                	
                		broli.add(e.getPoint());
                    	points.add(e.getPoint());

                    	linePoints.add(new Points(e.getX(),e.getY()));
                    	
                        g = gf.getGraphics();
                        g.setColor(Color.red);
                        g.fillOval(e.getX()+7, e.getY()+88, 2, 2);
                        
                        if (e.isMetaDown()) {

                            g = gf.getGraphics();
                            g.setColor(Color.ORANGE);
                            for (int i = 0; i < broli.size() - 1; i++) {
                                Point b1 = broli.get(i);
                                Point b2 = broli.get(i + 1);
                                g.drawLine(b1.x+7, b1.y+88, b2.x+7, b2.y+88);
                            }                            
                        }
                        flag = 1;
                }else if (trianglesButton.isSelected()) {//三角形按钮

                    Point pt1 = new Point();

                    Point pt2 = new Point();

                    Point pt3 = new Point();
                    
                    

                    tri.add(e.getPoint());

                    if (tri.size() % 3 == 0) {

                        g = gf.getGraphics();

                        g.setColor(Color.red);

                        pt1 = tri.get(0);

                        pt2 = tri.get(1);

                        pt3 = tri.get(2);

                        g.fillOval(e.getX()+7, e.getY()+88, 2, 2);

                        g.drawLine(pt1.x+7, pt1.y+88, pt2.x+7, pt2.y+88);

                        g.drawLine(pt2.x+7, pt2.y+88, pt3.x+7, pt3.y+88);

                        g.drawLine(pt3.x+7, pt3.y+88, pt1.x+7, pt1.y+88);
                        //计算周长
                        for (int i = 0; i <= tri.size()-1; i++) {
                            if(i<tri.size()-1) {
                                perimeter1 += Math.sqrt(Math.pow(tri.get(i + 1).x - tri.get(i).x, 2) + Math.pow(tri.get(i + 1).y - tri.get(i).y, 2));
                            }
                            if(i==tri.size()-1) {
                                perimeter = perimeter1+Math.sqrt(Math.pow(tri.get(i).x - tri.get(0).x, 2) + Math.pow(tri.get(i).y - tri.get(0).y, 2));
                            }
                    }
                        System.out.println("三角形周长已记录！");
                        //计算面积
                        for (int i = 0; i <= tri.size() - 1; i++) {
                            if(i<tri.size()-1) {
                                location[i][0]=tri.get(i).x;
                                location[i][1]=tri.get(i).y;
                            }
                            if(i==tri.size()-1)
                            {
                                location[tri.size()-1][0]=tri.get(tri.size()-1).x;
                                location[tri.size()-1][1]=tri.get(tri.size()-1).y;
                            }
                    }
                    location[tri.size()][0] = location[0][0];
                    location[tri.size()][1] = location[0][1];
                    for (int j = 0; j <=tri.size()-1 ; j++) {
                        sum+=(location[j][0]*location[j+1][1]-location[j+1][0]*location[j][1]);
                    }
                    area=sum/2;
                    if(area<0) area=-area; //转换为正数
                    System.out.println("三角形面积已记录！");    
                        //计算重心     
                            gra.x+= (tri.get(0).x + tri.get(1).x + tri.get(2).x)/3;
                            gra.y+= (tri.get(0).y + tri.get(1).y + tri.get(2).y)/3;
                    
                        flag=3;                
                        //repaint();
                        tri.clear();     

                    } else {

                        g = gf.getGraphics();

                        g.setColor(Color.red);

                        g.fillOval(e.getX()+7, e.getY()+88, 2, 2);//fillOval画点

                    }

                }else if (polygonsButton.isSelected()) {//多边形

                    try {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                            | UnsupportedLookAndFeelException h) {
                        h.printStackTrace();
                    }
                    pol.add(e.getPoint());

                    g = gf.getGraphics();

                    g.setColor(Color.red);

                    g.fillOval(e.getX()+7, e.getY()+88, 2, 2);

                    if (e.isMetaDown()) {

                        g = gf.getGraphics();

                        g.setColor(Color.red);

                        for (int i = 0; i < pol.size() - 1; i++) {

                            Point po1 = pol.get(i);

                            Point po2 = pol.get(i + 1);

                            g.drawLine(po1.x+7, po1.y+88, po2.x+7, po2.y+88);
                        }

                        g.drawLine(pol.get(0).x+7, pol.get(0).y+88, pol.get(pol.size() - 1).x+7, pol.get(pol.size() - 1).y+88);
                        //计算周长
                        for (int i = 0; i <= pol.size()-1; i++) {
                            if(i<pol.size()-1) {
                                perimeter1 += Math.sqrt(Math.pow(pol.get(i + 1).x - pol.get(i).x, 2) + Math.pow(pol.get(i + 1).y - pol.get(i).y, 2));
                            }
                            if(i==pol.size()-1) {
                                perimeter = perimeter1+Math.sqrt(Math.pow(pol.get(i).x - pol.get(0).x, 2) + Math.pow(pol.get(i).y - pol.get(0).y, 2));
                            }
                    }
                        System.out.println("多边形周长已记录！");
                        //利用二维矩阵计算面积
                        for (int i = 0; i <= pol.size() - 1; i++) {
                                if(i<pol.size()-1) {
                                    location[i][0]=pol.get(i).x;
                                    location[i][1]=pol.get(i).y;
                                }
                                if(i==pol.size()-1)
                                {
                                    location[pol.size()-1][0]=pol.get(pol.size()-1).x;
                                    location[pol.size()-1][1]=pol.get(pol.size()-1).y;
                                }
                        }
                        location[pol.size()][0] = location[0][0];
                        location[pol.size()][1] = location[0][1];
                        for (int j = 0; j <=pol.size()-1 ; j++) {
                            sum+=(location[j][0]*location[j+1][1]-location[j+1][0]*location[j][1]);
                        }
                        area=sum/2;
                        System.out.println("多边形面积已记录！");
                        if(area<0) area=-area; //转换为正数
                        //打印各个点的个数和坐标
                        System.out.println("点的个数为："+pol.size());
                        for (int i = 0; i <=pol.size()-1 ; i++) {
                            System.out.println("["+"X"+(i+1)+"="+pol.get(i).x+","+"Y"+(i+1)+"="+pol.get(i).y+"]");
                        }
                        //计算重心
                        for (int i = 0; i <= pol.size()-1; i++) {
                            if(i<pol.size()-1) {
                                gra1.x= pol.get(i + 1).x + pol.get(i).x;
                                gra1.y= pol.get(i + 1).y + pol.get(i).y;
                            }
                            if(i==pol.size()-1) {
                            	gra2.x= pol.get(i).x + pol.get(0).x;
                                gra2.y= pol.get(i).y + pol.get(0).y;
                            }
                    }
                        gra.x=(gra2.x)/pol.size();
                        gra.y=(gra2.y)/pol.size();
                        
                        flag=4;
                        pol.clear();
                    }
                }
//////////////////////按钮响应事件函数////////////////////////////
       if(flag == 0) { //点缓冲区
    	   pointBufferButton.addActionListener(new ActionListener() {
          	 public void actionPerformed(ActionEvent e) {
          		int radius=50;
                Graphics g = gf.getGraphics();
                Graphics t = gf.getGraphics();
                g.setColor(Color.red);
                t.setColor(Color.ORANGE);
                for(int i=0; i<po.size();i++) {
                	if(po.get(i).x!=0&po.get(i).y!=0){
                    g.fillOval(po.get(i).x+7,po.get(i).y+88, 2, 2);
                    t.drawOval(po.get(i).x+7-radius/2,po.get(i).y+88-radius/2,radius,radius);}
                }
          	 }
    	   });
       }
      if(flag==1) {//道格拉斯扑克算法
         dp.addActionListener(new ActionListener() {
        	 public void actionPerformed(ActionEvent e) {
        		 String  o=JOptionPane.showInputDialog("请输入阈值：");
        		 int epsilon=Integer.parseInt(o);
        		 result = DouglasPeuckerUtil.DouglasPeucker(points, epsilon);
        		 g = gf.getGraphics();
        		 g.clearRect(0, 95, 650, 700);
                 g.setColor(Color.BLUE);
                 for (int i = 0; i < result.size() - 1; i++) {
                     Point b1 = result.get(i);
                     Point b2 = result.get(i + 1);
                     g.drawLine(b1.x+7, b1.y+88, b2.x+7, b2.y+88);
                 }
        	 }
         });
         
         lineBufferButton.addActionListener(new ActionListener() {//线缓冲区
        	 public void actionPerformed(ActionEvent e) {
                         for(int i = 0;i < leftPoints.size()-1;i++) {
                             g.drawLine((int)(leftPoints.get(i).x+7),(int)(leftPoints.get(i).y+91),(int)(leftPoints.get(i+1).x+7),(int)(leftPoints.get(i+1).y+91));
                         }
                         getLineBufferEdgeCoords(linePoints,20);
                     }
         });
     }
      if(flag==2) {
      	lineLength.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
              	g = gf.getGraphics();
                  g.setColor(Color.BLACK);
                  g.drawString("边长：" + line,10,120);
              }
          });
      }
      
      if(flag==3) {
      	//添加面积按钮响应事件
          getArea.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                  g = gf.getGraphics();
                  g.setColor(Color.BLACK);
                  g.drawString("面积：" + area,10,140);
              }
          });
          //添加周长按钮响应事件
          getPerimeter.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                  g = gf.getGraphics();
                  g.setColor(Color.BLACK);
                  g.drawString("周长：" + perimeter,10,160);
              }
          });
          //添加重心按钮响应事件
          gravitycenter.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                  g = gf.getGraphics();
                  g.setColor(Color.BLUE);
                  g.drawString("重心：" + "X 坐标："+gra.x+"  Y 坐标："+gra.y,10,180);
              }
          });         
          
      }

	if(flag==4)

	{
		// 添加面积按钮响应事件
		getArea.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				g = gf.getGraphics();
				g.setColor(Color.BLACK);
				g.drawString("面积：" + area, 10, 140);
			}
		});
		// 添加周长按钮响应事件
		getPerimeter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				g = gf.getGraphics();
				g.setColor(Color.BLACK);
				g.drawString("周长：" + perimeter, 10, 160);
			}
		});
		// 添加重心按钮响应事件
		gravitycenter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				g = gf.getGraphics();
				g.setColor(Color.BLUE);
				g.drawString("重心：" + "X 坐标：" + gra.x + "  Y 坐标：" + gra.y, 10, 180);
			}
		});
	}
	}

	@Override

            public void mouseReleased(MouseEvent e) {

            }

	@Override

            public void mousePressed(MouseEvent e) {

            }

	@Override

            public void mouseExited(MouseEvent e) {

            }

	@Override

            public void mouseEntered(MouseEvent e) {

            }

});

}
	public void getLineBufferEdgeCoords(ArrayList< Points> linepoints, double radius) {
        leftPoints = getLeftBufferEdgeCoords(linepoints, radius);
        Collections.reverse(linepoints);
        rightPoints = getLeftBufferEdgeCoords(linepoints, radius);

        System.out.println("linepoints:");
        for( Points p:linepoints) {
            System.out.println(p.x + " " + p.y);
        }

        System.out.println("leftpoints:");
        for( Points p: leftPoints) {
            System.out.println(p.x + " " + p.y);
        }

        System.out.println("rightpoints:");
        for( Points p: rightPoints) {
            System.out.println(p.x + " " + p.y);
        }

        resultsPoints.addAll(leftPoints);
        resultsPoints.addAll(rightPoints);
        System.out.println("results Points:");
        for( Points p:resultsPoints) {
            System.out.println(p.x + " " + p.y);
        }
    }
    private ArrayList< Points> getLeftBufferEdgeCoords(ArrayList< Points> linepoints, double radius) {
        if (linepoints.size() < 1)
            return null;

        ArrayList< Points> points = new ArrayList< Points>();
        double alpha = 0.0;
        double delta = 0.0;
        double l = 0.0;

        double startRadian = 0.0;
        double endRadian = 0.0;
        double beta = 0.0;
        double x = 0.0, y = 0.0;

        {
            alpha = topology.getQuadrantAngle(linepoints.get(0), linepoints.get(1));
            startRadian = alpha + Math.PI;
            endRadian = alpha + (3 * Math.PI) / 2;
            points.addAll(getBufferCoordsByRadian(linepoints.get(0), startRadian, endRadian, radius));
        }

        for (int i = 1; i < linepoints.size() - 1; i++) {
            alpha = topology.getQuadrantAngle(linepoints.get(i), linepoints.get(i + 1));
            delta = topology.getIncludedAngel(linepoints.get(i - 1), linepoints.get(i), linepoints.get(i + 1));
            l = getVectorProduct(linepoints.get(i - 1), linepoints.get(i), linepoints.get(i + 1));
            if (l > 0) {
                startRadian = alpha + (3 * Math.PI) / 2 - delta;
                endRadian = alpha + (3 * Math.PI) / 2;
                points.addAll(getBufferCoordsByRadian(linepoints.get(i), startRadian, endRadian, radius));
//                strCoords.append(getBufferCoordsByRadian(coords.get(i), startRadian, endRadian, radius));
            } else if (l < 0) {
                beta = alpha - (Math.PI - delta) / 2;
                x = linepoints.get(i).x + radius * Math.cos(beta);
                y = linepoints.get(i).y + radius * Math.sin(beta);
                points.add(new  Points(x, y));
            }
        }


        {
            alpha = topology.getQuadrantAngle(linepoints.get(linepoints.size() - 2), linepoints.get(linepoints.size() - 1));
            startRadian = alpha + (3 * Math.PI) / 2;
            endRadian = alpha + 2 * Math.PI;
            points.addAll(getBufferCoordsByRadian(linepoints.get(linepoints.size() - 1), startRadian, endRadian, radius));
        }
        return points;
    }


    private ArrayList< Points> getBufferCoordsByRadian( Points center, double startRadian, double endRadian, double radius) {
        double gamma = Math.PI / 6;
        ArrayList< Points> points = new ArrayList<>();
        double x = 0.0, y = 0.0;
        for (double phi = startRadian; phi <= endRadian + 0.000000000000001; phi += gamma) {
            x = center.x + radius * Math.cos(phi);
            y = center.y + radius * Math.sin(phi);
            points.add(new  Points(x, y));
        }
        return points;
    }


    private static double getVectorProduct( Points preCoord,  Points midCoord,  Points nextCoord) {
        return (midCoord.x - preCoord.x) * (nextCoord.y - midCoord.y)
                - (nextCoord.x - midCoord.x)
                * (midCoord.y - preCoord.y);
    }
    class Topology {
        public double getQuadrantAngle(Points preCoord, Points nextCoord) {
            return getQuadrantAngle(nextCoord.x - preCoord.x,
                    nextCoord.y - preCoord.y);
        }


        public double getQuadrantAngle(double x, double y) {
            double theta = Math.atan(y / x);
            if (x > 0 && y > 0)
                return theta;
            if (x > 0 && y < 0)
                return Math.PI * 2 + theta;
            if (x < 0 && y > 0)
                return theta + Math.PI;
            if (x < 0 && y < 0)
                return theta + Math.PI;
            return theta;
        }


        public double getIncludedAngel(Points preCoord, Points midCoord, Points nextCoord) {
            double innerProduct = (midCoord.x - preCoord.x)
                    * (nextCoord.x - midCoord.x)
                    + (midCoord.y - preCoord.y)
                    * (nextCoord.y - midCoord.y);
            double mode1 = Math.sqrt(Math.pow((midCoord.x - preCoord.x), 2.0)
                    + Math.pow((midCoord.y - preCoord.y), 2.0));
            double mode2 = Math.sqrt(Math.pow((nextCoord.x - midCoord.x), 2.0)
                    + Math.pow((nextCoord.y - midCoord.y), 2.0));
            return Math.acos(innerProduct / (mode1 * mode2));
        }

        public double getDistance(Point preCoord, Point nextCoord) {
            return Math.sqrt(Math.pow((nextCoord.x - preCoord.x), 2)
                    + Math.pow((nextCoord.y - preCoord.y), 2));
        }
    }
    static class Points extends Point{
        private double x;
        private double y;

        public Points() {
        }

        public Points(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
}
