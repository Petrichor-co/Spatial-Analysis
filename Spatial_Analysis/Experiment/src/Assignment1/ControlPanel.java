package Assignment1;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import java.awt.event.MouseListener;

import java.util.ArrayList;

import java.util.List;

import javax.swing.*;

public class ControlPanel extends JPanel {
	Point gra1 = new Point();//n-1 个坐标和
	Point gra2 = new Point();//n 个坐标和
	Point gra = new Point();//重心点坐标
    double perimeter1=0; //n-1 条边的边长和
    double perimeter=0;
    double line=0;
    int[][] location =new int[100][2];
    double sum=0;
    double area=0;
    public int flag=-1;
    JButton getArea=new JButton("面积");
    JButton getPerimeter=new JButton("周长");
    JButton lineLength=new JButton("边长");
    JButton gravitycenter=new JButton("重心");

    private static final long serialVersionUID = 1L;

    public GraphicsFrame gf; //图形框架

    public GraphicPanel gp;  //图形面板


    public ControlPanel(GraphicsFrame _gf) {

        super();

        gf = _gf;

        gp = gf.gp;


        ButtonGroup bg = new ButtonGroup(); //按钮

        final JRadioButton pointsButton = new JRadioButton("点", false);

        final JRadioButton linesButton = new JRadioButton("线", false);

        final JRadioButton trianglesButton = new JRadioButton("三角形",false);

        final JRadioButton polygonsButton = new JRadioButton("多边形", false);


        
        bg.add(pointsButton);

        bg.add(linesButton);

        bg.add(trianglesButton);

        bg.add(polygonsButton);

        //setLayout(new GridLayout(20,1,2,2));   //创建一个窗口：创建具有指定行数、列数以及组件水平、纵向一定间距的网格布局

        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        
//        GridBagConstraints s= new GridBagConstraints();//定义一个GridBagConstraints，
//        s.fill = GridBagConstraints.BOTH;//使组件完全填满其显示区域
//        s.gridwidth=1;//该方法是设置组件水平所占用的格子数，如果为0，就说明该组件是该行的最后一个
//        s.weightx = 0;//该方法设置组件水平的拉伸幅度，如果为0就说明不拉伸，不为0就随着窗口增大进行拉伸，0到1之间
//        s.weighty=0;//该方法设置组件垂直的拉伸幅度，如果为0就说明不拉伸，不为0就随着窗口增大进行拉伸，0到1之间
        //设置组件......
        

        //实例化按钮对象
        add(pointsButton);

        add(linesButton);

        add(trianglesButton);

        add(polygonsButton);

        add(getPerimeter);

        add(getArea);

        add(lineLength);
        
        add(gravitycenter);


        //添加鼠标监听事件
        gp.addMouseListener(new MouseListener() {


            List<Point> li = new ArrayList<Point>();//线集合

            List<Point> tri = new ArrayList<Point>();//三角形集合

            List<Point> pol = new ArrayList<Point>();//多边形集合

            Graphics g; //Graphics类提供基本的几何图形绘制方法，主要有：画线段、画矩形、画圆、画带颜色的图形、画椭圆、画圆弧、画多边形等。



            //鼠标点击事件
            public void mouseClicked(MouseEvent e) {

                if (pointsButton.isSelected()) {//点按钮被选中

                    int x, y;

                    x = e.getX();

                    y = e.getY();

                    Graphics g = gf.getGraphics();

                    g.setColor(Color.red);

                    g.fillOval(x+7, y+30, 2, 2);
                    //画椭圆，x-要填充椭圆的左上角的 x 坐标 y-要填充椭圆的左上角的 y 坐标  width - 要填充椭圆的宽度 height - 要填充椭圆的高度

                } else if (linesButton.isSelected()) {//线按钮

                    Point p1 = new Point();

                    Point p2 = new Point();

                    li.add(e.getPoint());

                    if (li.size() % 2 == 0) {

                        g = gf.getGraphics();

                        g.setColor(Color.red);

                        p1 = li.get(0);

                        p2 = li.get(1);

                        g.fillOval(e.getX()+7, e.getY()+30, 2, 2); //public abstract void fillOval(int x,int y,int width,int height)

                        g.drawLine(p1.x+7, p1.y+30, p2.x+7, p2.y+30);

                        repaint();
                        
                        line=Math.sqrt(Math.pow(p2.x-p1.x, 2)+Math.pow(p2.y-p1.y,2));
                        
                        flag=2;

                        li.clear();

                    } else {

                        g = gf.getGraphics();

                        g.setColor(Color.red);

                        g.fillOval(e.getX()+7, e.getY()+30, 2, 2);//fillOval画点

                    }

                } else if (trianglesButton.isSelected()) {//三角形按钮

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

                        g.fillOval(e.getX()+7, e.getY()+30, 2, 2);

                        g.drawLine(pt1.x+7, pt1.y+30, pt2.x+7, pt2.y+30);

                        g.drawLine(pt2.x+7, pt2.y+30, pt3.x+7, pt3.y+30);

                        g.drawLine(pt3.x+7, pt3.y+30, pt1.x+7, pt1.y+30);
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
                        repaint();
                        tri.clear();     

                    } else {

                        g = gf.getGraphics();

                        g.setColor(Color.red);

                        g.fillOval(e.getX()+7, e.getY()+30, 2, 2);//fillOval画点

                    }

                } else if (polygonsButton.isSelected()) {//多边形

                    try {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                            | UnsupportedLookAndFeelException h) {
                        h.printStackTrace();
                    }
                    pol.add(e.getPoint());

                    g = gf.getGraphics();

                    g.setColor(Color.red);

                    g.fillOval(e.getX()+7, e.getY()+30, 2, 2);

                    if (e.isMetaDown()) {

                        g = gf.getGraphics();

                        g.setColor(Color.red);

                        for (int i = 0; i < pol.size() - 1; i++) {

                            Point po1 = pol.get(i);

                            Point po2 = pol.get(i + 1);

                            g.drawLine(po1.x+7, po1.y+30, po2.x+7, po2.y+30);
                        }

                        g.drawLine(pol.get(0).x+7, pol.get(0).y+30, pol.get(pol.size() - 1).x+7, pol.get(pol.size() - 1).y+30);
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
                if(flag==2) {
                	lineLength.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                        	g = gf.getGraphics();
                            g.setColor(Color.BLACK);
                            g.drawString("边长：" + line,50,120);
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
                            g.drawString("面积：" + area,100,100);
                        }
                    });
                    //添加周长按钮响应事件
                    getPerimeter.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            g = gf.getGraphics();
                            g.setColor(Color.BLACK);
                            g.drawString("周长：" + perimeter,100,80);
                        }
                    });
                    //添加重心按钮响应事件
                    gravitycenter.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            g = gf.getGraphics();
                            g.setColor(Color.BLUE);
                            g.drawString("重心：" + "X 坐标："+gra.x+"  Y 坐标："+gra.y,100,120);
                        }
                    });
                }
                
                if(flag==4) {
                	//添加面积按钮响应事件
                    getArea.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            g = gf.getGraphics();
                            g.setColor(Color.BLACK);
                            g.drawString("面积：" + area,50,100);
                        }
                    });
                    //添加周长按钮响应事件
                    getPerimeter.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            g = gf.getGraphics();
                            g.setColor(Color.BLACK);
                            g.drawString("周长：" + perimeter,50,80);
                        }
                    });
                   //添加重心按钮响应事件
                    gravitycenter.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            g = gf.getGraphics();
                            g.setColor(Color.BLUE);
                            g.drawString("重心：" + "X 坐标："+gra.x+"  Y 坐标："+gra.y,50,120);
                        }
                    });
                }
                                             
                
            }
            

            public void mouseReleased(MouseEvent e) {

            }


            public void mousePressed(MouseEvent e) {

            }


            public void mouseExited(MouseEvent e) {

            }


            public void mouseEntered(MouseEvent e) {

            }


        });

    }
}