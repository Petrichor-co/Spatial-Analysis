package Assignment3;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class Text extends JFrame{
	public static ArrayList<DEMPoint> dem = new ArrayList<DEMPoint>();//插值后的格网点集合
	public static ArrayList<SlopePoint> slope_degrees = new ArrayList<SlopePoint>();//坡度点集合
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
	JButton jb1;
	JToolBar jtb;// 工具条
    // 菜单条组件
    JMenuBar jmb;
    JMenu menu1, menu2, menu3, menu4, menu5;
    JMenuItem fanjvli, podu, poxiang, item4, item5, item6, item7;
    JTextArea jta;//文本区域条
    
	private static final long serialVersionUID = 1L;
	/**
	 * 确定格网点坐标
	 * @return
	 */
	public static ArrayList<DEMPoint> border(){
		//读取文件中数据
		ArrayList<ArrayList<point>> sum = ReadNewFile.readnewFile();
		System.out.println("读取完毕！");
		double[] dx = new double[sum.size()];//x的数组
		double[] dy = new double[sum.size()];//y的数组
		List<point> points = null;//Point类型的变量，有x，y，h三个值
		//给dx dy赋值
		for(int i=0;i<sum.size();i++) {
			//System.out.print(i);
			//System.out.println(sum.get(i));
			points = new ArrayList<point>(sum.get(i));
			//System.out.println(points);
			List<Double> xd = points.stream().map(point::getX).collect(Collectors.toList());//获取x的值
			dx[i] = xd.get(0);
			List<Double> yd = points.stream().map(point::getY).collect(Collectors.toList());//获取y的值
			dy[i] = yd.get(0);
		}
		//边界范围
		System.out.println("正在确定格网边界范围......");
		double xmax = dx[0];
		double xmin = dx[0];
		double ymax = dy[0];
		double ymin = dy[0];
		for(int i=0;i<dx.length;i++) {
			//System.out.println("dx["+i+"]的值为："+dx[i]);
			if(xmax<dx[i]) {
				xmax = dx[i];
			}
			if(xmin>dx[i]) {
				xmin = dx[i];
			}
		}
		for(int i=0;i<dy.length;i++) {
			//System.out.println("dy["+i+"]的值为："+dy[i]);
			if(ymax<dy[i]) {
				ymax = dy[i];
			}
			if(ymin>dy[i]) {
				ymin = dy[i];
			}
		}
		System.out.println("x最大值为："+xmax+"  "+"x最小值为："+xmin);
		System.out.println("y最大值为："+ymax+"  "+"y最小值为："+ymin);
		int demXmin = (int) (xmin);
		int demYmin = (int) (ymin);
		
		//边界长,格网尺寸取 1
		int numX = (int) ((xmax - xmin)/5+1);//(n个间隔值为 1 的空隙)+1 个坐标点
		int numY = (int) ((ymax - ymin)/5+1);
		System.out.println("numX: "+numX+" "+"numY: "+numY);
		
		//计算格网点坐标
		double[] demX = new double[numX];
		double[] demY = new double[numY];
		ArrayList<DEMPoint> demPoints = new ArrayList<DEMPoint>();//DEMPoint类型数据，两个参数包含参数x，y
		for(int i=0;i<numX;i++) {
			for(int j=0;j<numY;j++) {
				demX[i] = demXmin + i * 5;
				demY[j] = demYmin + j * 5;
				//System.out.println("格网点坐标："+"demX["+i+"]: "+demX[i]+"demY["+i+"]: "+demY[j]);
				demPoints.add(new DEMPoint(demX[i], demY[j]));
				//System.out.println(demPoints);
			}
			
		}
		System.out.println("格网化结束！");
		return demPoints;		
	}
	/**
	 * 距离平方倒数加权
	 * @return
	 */
	public static ArrayList<DEMPoint> interpolation() {		
		int r;//搜索半径
		int num;//搜索点数
		int count = 0;//用来迭代数据集中的数据
		ArrayList<ArrayList<point>> sum = ReadNewFile.readnewFile();//原始数据集合
		ArrayList<DEMPoint> demPoints = border();//格网点集合
		double[] dx = new double[sum.size()];//x的数组
		double[] dy = new double[sum.size()];//y的数组
		double[] dh = new double[sum.size()];//y的数组
		List<point> points = null;
		//ArrayList<DEMPoint> dem = new ArrayList<DEMPoint>();//插值后的格网点集合
		System.out.println("点的数量： "+demPoints.size());
		while(count<demPoints.size()) {
			DEMPoint p = demPoints.get(count++);//取出每一个格网点
			double px = p.getX();//格网点x
			double py = p.getY();//格网点y
			double[] d = new double[sum.size()];//长度为sum长度的数组
			r = 0;
			num = 0;
			//System.out.println("px = "+px+"  py = "+py+"sum.size(): "+sum.size());
			//取出每一个原始点
			for(int i=0;i<sum.size();i++) {
				points = new ArrayList<point>(sum.get(i));
				List<Double> xd = points.stream().map(point::getX).collect(Collectors.toList());
				dx[i] = xd.get(0);//原始点x
				List<Double> yd = points.stream().map(point::getY).collect(Collectors.toList());
				dy[i] = yd.get(0);//原始点y
				List<Double> hd = points.stream().map(point::getH).collect(Collectors.toList());
				dh[i] = hd.get(0);//原始点h
				//System.out.println("dh = "+dh[i]);
				//计算当前格网点到每个原始点的距离
				d[i] = Math.sqrt((px-dx[i])*(px-dx[i])+(py-dy[i])*(py-dy[i]));
				//System.out.print(i+"  "+d[i]);
			}
			//System.out.println("第"+count+"个格网点到原始点的距离为："+d[count]);
			
			//距离平方倒数加权
			double w = 0;//权值
			double wz = 0;
			double wh = 0;
			double whz = 0;
			//反距离加权插值核心算法
			for(;r<100;r=r+5) {
				for(int i=0;i<sum.size();i++) {
					if(d[i] < r) {
						w = 1/d[i]*d[i];//距离权重
						wh = w*dh[i];//距离权重*高程
						num++;//num从0开始加，也就是说最多只进行五次加权运算
						wz = wz + w;//权重的和
						whz = whz + wh;//高程值的和						
					}
					
				}
				if(num>=5)break;
			}
			System.out.println("正在计算第 "+count+" 个"+"当前格网点权重的和："+wz+"  当前格网点高程的和："+whz);
			DecimalFormat df = new DecimalFormat("###.000");//格式化十进制数字：三个整数 三个小数 0必须在#后面
			double ph = Double.parseDouble(df.format(whz/wz));//格网点h
			dem.add(new DEMPoint(px, py, ph));
	        
		}
		System.out.println("反距离加权插值成功！");
		//System.out.println("dem的值为："+dem);
		return dem;
	}
	       
	public Text() {
//		Panel gp =new Panel();
//		gp.setPreferredSize(new Dimension(200, 800));
//        gp.setBackground(Color.WHITE);
//        this.add(gp);
        
		setTitle("课程设计");
		setSize(800, 800);
        setLocationRelativeTo(null);//设置居中        
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置关闭进程		
        jb1 = new JButton("清屏");
        
        ButtonGroup bg = new ButtonGroup();
		final JRadioButton pointsButton = new JRadioButton("Points", false);
		final JRadioButton linesButton = new JRadioButton("Lines", false);
		final JRadioButton trianglesButton = new JRadioButton("Triangles", false);
		final JRadioButton polygonsButton = new JRadioButton("Polygons", false);
 
		bg.add(pointsButton);
		bg.add(linesButton);
		bg.add(trianglesButton);
		bg.add(polygonsButton);
 
		//setLayout(new GridLayout(20, 1, 2, 2));
		setLayout(new FlowLayout());
 
 
		add(pointsButton);
		add(linesButton);
		add(trianglesButton);
		add(polygonsButton);
		
		// 创建工具条
        jtb = new JToolBar();
        jtb.add(jb1);
        jtb.add(getPerimeter);
        jtb.add(getArea);
        jtb.add(lineLength);        
        jtb.add(gravitycenter);
        this.add(jtb, BorderLayout.NORTH);

        //创建菜单条
        jmb = new JMenuBar();
        menu1 = new JMenu("插值算法(F)");
        menu1.setMnemonic('F');// 设置助记符
        menu2 = new JMenu("地形特征点提取(E)");
        menu2.setMnemonic('E');
        
        fanjvli = new JMenuItem("反距离加权法");
        podu = new JMenuItem("坡度绘制");
        poxiang = new JMenuItem("坡向绘制");
        //创建文本区域条
        jta = new JTextArea();
     
        // 将菜单添加到菜单栏上
        menu1.add(fanjvli);
        menu2.add(podu);
        menu2.addSeparator();// 添加分割线
        menu2.add(poxiang);
        
        // 将菜单添加到菜单条上
        jmb.add(menu1);
        jmb.add(menu2);

        // 将菜单添加到窗体上
        this.setJMenuBar(jmb);
        this.setBackground(Color.WHITE);
		setVisible(true);
		jb1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Graphics g = getGraphics();
				g.clearRect(0, 100, 800, 800);
			}
		});
        fanjvli.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	interpolation();            	
            	Graphics g = getGraphics();//Graphics函数要在类继承JFrame或者JPanel两个属性下才能实现这个方法，并且这个方法是系统自动调用的。
            	for(int i=0;i<=4401;i++) {
            	  double x1=dem.get(i).getX();
                  double y1=dem.get(i).getY();
                  double h1=dem.get(i).getH();
                  int x=(int)x1-509800;
                  int y=(int)y1-3983000;
                  int h=(int)h1*2-50;      //int h2=(int)(h-1100)*6;
                  System.out.println(h);
            	  g.setColor(new Color(h,h,h));
                  g.fillRect(x+200 , y-500 , 5, 5);                                                      
                  
              }
            }});            			        	      
        podu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	Graphics G = getGraphics();
            	int numX=62,numY=71;
        		double ex,ey;
        		int c=numY-1;int f=numY;
        		int i=numY+1;int a= -numY-1;
        	    int d= -numY;int g= -numY+1;
        	    int h=1;int b= -1;
        	    int s=72;//从index=72开始
        		for (int ii = 0; ii<60; ii++) {//60列
        			for(int j=0;j<69;j++) {//69行
        				ex=((dem.get(j+s+c).getH()+2*dem.get(j+s+f).getH()+dem.get(j+s+i).getH())-(dem.get(j+s+a).getH()+2*dem.get(j+s+d).getH()+dem.get(j+s+g).getH()))/40;
        				ey=((dem.get(j+s+g).getH()+2*dem.get(j+s+h).getH()+dem.get(j+s+i).getH())-(dem.get(j+s+a).getH()+2*dem.get(j+s+b).getH()+dem.get(j+s+c).getH()))/40;
        				double rise_run=Math.sqrt(ex*ex+ey*ey);
        				double slope_degree= Math.atan(rise_run)*57.29578;
        				slope_degrees.add(new SlopePoint(slope_degree));
        				double x1=dem.get(j+s).getX();
                        double y1=dem.get(j+s).getY();
                        int x=(int)x1-509800;
                        int y=(int)y1-3983000;
                        int slope=(int)slope_degree*2+60;
                        System.out.println(slope);
                  	  	G.setColor(new Color(slope-60,slope,slope+60));
                  	  	G.fillRect(x+200 , y-500 , 5, 5);
        			}
        			s+=71;					}
        		
            }});
        poxiang.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	Graphics G = getGraphics();
            	int numX=62,numY=71;
        		double ex,ey;
        		int c=numY-1;int f=numY;
        		int i=numY+1;int a= -numY-1;
        	    int d= -numY;int g= -numY+1;
        	    int h=1;int b= -1;
        	    int s=72;//从index=72开始	
        		for (int ii = 0; ii<60; ii++) {//60列
        			for(int j=0;j<69;j++) {//69行
        				ex=((dem.get(j+s+c).getH()+2*dem.get(j+s+f).getH()+dem.get(j+s+i).getH())-(dem.get(j+s+a).getH()+2*dem.get(j+s+d).getH()+dem.get(j+s+g).getH()))/8;
        				ey=((dem.get(j+s+g).getH()+2*dem.get(j+s+h).getH()+dem.get(j+s+i).getH())-(dem.get(j+s+a).getH()+2*dem.get(j+s+b).getH()+dem.get(j+s+c).getH()))/8;
        				double aspect=57.29578 *Math.atan2(ey,ex);
        				double cell1=0;
        				if(aspect<0) {
        					cell1 = 90.0 - aspect;
        				}
        				else if(aspect>=0) {
        					cell1=aspect;
        				}
        				DecimalFormat df = new DecimalFormat("##.0");
        				double cell=Double.parseDouble(df.format(cell1));
        				double x1=dem.get(j+s).getX();
                        double y1=dem.get(j+s).getY();
                        int x=(int)x1-509800;
                        int y=(int)y1-3983000;
                        System.out.println(cell);
                        if(cell<22.5&&cell>=0) {// N
                        	G.setColor(Color.red);
                        	G.fillRect(x+200 , y-500 , 5, 5);
                        }                 	  
                        else if(cell>=22.5&&cell<67.5) {// NE
                  	  	G.setColor(Color.orange);
                    	G.fillRect(x+200 , y-500 , 5, 5);
                  	  	}
                        else if(cell>=67.5&&cell<112.5) {// E
                    	  	G.setColor(Color.yellow);
                      	G.fillRect(x+200 , y-500 , 5, 5);
                    	  	}                        
                        else if(cell>=112.5&&cell<157.5) {// SE
                    	  	G.setColor(Color.green);
                    	  	G.fillRect(x+200 , y-500 , 5, 5);
                    	  	}
                        else if(cell>=157.5&&cell<202.5) {// S
                    	  	G.setColor(Color.cyan);
                    	  	G.fillRect(x+200 , y-500 , 5, 5);
                    	  	}
                        else if(cell>=202.5&&cell<247.5) {// SW
                    	  	G.setColor(new Color(135,206,250));//浅蓝
                    	  	G.fillRect(x+200 , y-500 , 5, 5);
                    	  	}
                        else if(cell>=247.5&&cell<292.5) {// W
                    	  	G.setColor(Color.blue);
                    	  	G.fillRect(x+200 , y-500 , 5, 5);
                    	  	}
                        else if(cell>=292.5&&cell<337.5) {// NW
                    	  	G.setColor(Color.pink);
                    	  	G.fillRect(x+200 , y-500 , 5, 5);
                    	  	}
                        else if(cell>=337.5&&cell<=360) {// N
                    	  	G.setColor(Color.red);
                    	  	G.fillRect(x+200 , y-500 , 5, 5);
                    	  	}
                        else if(cell== -1){
                        	G.setColor(Color.gray);
                    	  	G.fillRect(x+200 , y-500 , 5, 5);
                        }
        			}
        			s+=71;} 
            }});

      //添加鼠标监听事件
        this.addMouseListener(new MouseListener() {


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

                    g = getGraphics();

                    g.setColor(Color.red);

                    g.fillOval(x+7, y+30, 2, 2);
                    //画椭圆，x-要填充椭圆的左上角的 x 坐标 y-要填充椭圆的左上角的 y 坐标  width - 要填充椭圆的宽度 height - 要填充椭圆的高度

                } else if (linesButton.isSelected()) {//线按钮

                    Point p1 = new Point();

                    Point p2 = new Point();

                    li.add(e.getPoint());

                    if (li.size() % 2 == 0) {

                        g = getGraphics();

                        g.setColor(Color.red);

                        p1 = li.get(0);

                        p2 = li.get(1);

                        g.fillOval(e.getX()+7, e.getY()+30, 2, 2); //public abstract void fillOval(int x,int y,int width,int height)

                        g.drawLine(p1.x+7, p1.y+30, p2.x+7, p2.y+30);

                        //repaint();
                        
                        line=Math.sqrt(Math.pow(p2.x-p1.x, 2)+Math.pow(p2.y-p1.y,2));
                        
                        flag=2;

                        li.clear();

                    } else {

                        g = getGraphics();

                        g.setColor(Color.red);

                        g.fillOval(e.getX()+7, e.getY()+30, 2, 2);//fillOval画点

                    }

                } else if (trianglesButton.isSelected()) {//三角形按钮

                    Point pt1 = new Point();

                    Point pt2 = new Point();

                    Point pt3 = new Point();
                    
                    

                    tri.add(e.getPoint());

                    if (tri.size() % 3 == 0) {

                        g = getGraphics();

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
                        //repaint();
                        tri.clear();     

                    } else {

                        g = getGraphics();

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

                    g = getGraphics();

                    g.setColor(Color.red);

                    g.fillOval(e.getX()+7, e.getY()+30, 2, 2);

                    if (e.isMetaDown()) {

                        g = getGraphics();

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
                        	g = getGraphics();
                            g.setColor(Color.BLACK);
                            g.drawString("边长：" + line,50,200);
                        }
                    });
                }
                
                if(flag==3) {
                	//添加面积按钮响应事件
                    getArea.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            g = getGraphics();
                            g.setColor(Color.BLACK);
                            g.drawString("面积：" + area,50,250);
                        }
                    });
                    //添加周长按钮响应事件
                    getPerimeter.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            g = getGraphics();
                            g.setColor(Color.BLACK);
                            g.drawString("周长：" + perimeter,50,300);
                        }
                    });
                    //添加重心按钮响应事件
                    gravitycenter.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            g = getGraphics();
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
                            g = getGraphics();
                            g.setColor(Color.BLACK);
                            g.drawString("面积：" + area,50,350);
                        }
                    });
                    //添加周长按钮响应事件
                    getPerimeter.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            g = getGraphics();
                            g.setColor(Color.BLACK);
                            g.drawString("周长：" + perimeter,50,400);
                        }
                    });
                   //添加重心按钮响应事件
                    gravitycenter.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            g = getGraphics();
                            g.setColor(Color.BLUE);
                            g.drawString("重心：" + "X 坐标："+gra.x+"  Y 坐标："+gra.y,50,450);
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
	public static void main(String[] args) {		
		new Text();
	}	

}