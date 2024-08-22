package AssignmentAll;
import Kmeans.KMeansPaint;
import KochCurve.Koch;
import Assignment2.CloudModel;
import DelaunayTIN.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;

public class GraphicsFrame extends JFrame{
    public static ArrayList<DEMPoint> dem = new ArrayList<DEMPoint>();//插值后的格网点集合
    public static ArrayList<SlopePoint> slope_degrees = new ArrayList<SlopePoint>();//坡度点集合
    JButton jb1,jb2,jb3,jb4;
    JToolBar jtb;// 工具条
    // 菜单条组件
    JMenuBar jmb;
    JMenu menu1, menu2, menu3, menu4, menu5,menu6,menu7,menu8,menu9;
    JMenuItem fanjvli, podu, poxiang,zhengtaiyun,fenxing,TIN,K_means;
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
        double[] dh = new double[sum.size()];//h的数组
        List<point> points = null;
        //ArrayList<DEMPoint> dem = new ArrayList<DEMPoint>();//插值后的格网点集合
        System.out.println("点的数量： "+demPoints.size());
        while(count<demPoints.size()) {
            DEMPoint p = demPoints.get(count++);//取出每一个格网点
            double px = p.getX();//格网点x
            double py = p.getY();//格网点y
            double[] d = new double[sum.size()];//长度为sum的数组
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
                        wz = wz + w;//距离权重的和
                        whz = whz + wh;//距离权重*高程的和
                    }

                }
                if(num>=5)break;
            }
            System.out.println("正在计算第 "+count+" 个"+"当前格网点 距离权重的和："+wz+"  当前格网点 距离权重*高程的和："+whz);
            DecimalFormat df = new DecimalFormat("###.000");//格式化十进制数字：三个整数 三个小数 0必须在#后面
            double ph = Double.parseDouble(df.format(whz/wz));//格网点h
            dem.add(new DEMPoint(px, py, ph));

        }
        System.out.println("反距离加权插值成功！");
        //System.out.println("dem的值为："+dem);
        return dem;
    }

    public GraphicPanel gp;

    public ControlPanel cp;

    public GraphicsFrame (int height, int width)

    {

        setTitle("课程设计");

        setSize(width, height);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//使用 System exit 方法退出应用程序。仅在应用程序中使用。

        jb1 = new JButton("清屏");
        jb2 = new JButton("清除字符");
        setLayout(new BorderLayout(5, 5));


//        JPanel gp1 = new JPanel();
//        gp1.setPreferredSize(new Dimension(500,500));
//        gp1.setBackground(Color.GREEN);
//        this.add(gp1,BorderLayout.SOUTH);
        
        gp = new GraphicPanel(this);

        this.add(gp, BorderLayout.CENTER);

        cp = new ControlPanel(this);

        this.add(cp, BorderLayout.EAST);

        // 创建工具条
        jtb = new JToolBar();
        jtb.add(jb1);
        jtb.add(jb2);
        this.add(jtb, BorderLayout.NORTH);

        //创建菜单条
        jmb = new JMenuBar();
        menu1 = new JMenu("插值算法(F)");
        menu1.setMnemonic('F');// 设置助记符
        menu2 = new JMenu("地形特征点提取(E)");
        menu2.setMnemonic('E');
        menu3 = new JMenu("绘图(G)");
        menu3.setMnemonic('G');
        menu4 = new JMenu("不确定性分析(I)");
        menu4.setMnemonic('I');
        menu5 = new JMenu("KMeans(J)");
        menu5.setMnemonic('J');
        menu6 = new JMenu("TIN(H)");
        menu6.setMnemonic('H');

        fanjvli = new JMenuItem("反距离加权法");
        podu = new JMenuItem("坡度绘制");
        poxiang = new JMenuItem("坡向绘制");
        zhengtaiyun = new JMenuItem("正态云算法");
        fenxing = new JMenuItem("Koch分形算法");
        TIN=new JMenuItem("构建TIN");
        K_means=new JMenuItem("K均值法聚类分析");
        //创建文本区域条
        jta = new JTextArea();
        // 将菜单添加到菜单栏上
        menu1.add(fanjvli);
        menu2.add(podu);
        menu2.addSeparator();// 添加分割线
        menu2.add(poxiang);
        menu4.add(zhengtaiyun);
        menu4.add(fenxing);
        menu5.add(K_means);
        menu6.add(TIN);
        // 将菜单添加到菜单条上
        jmb.add(menu1);
        jmb.add(menu2);
        jmb.add(menu4);
        jmb.add(menu5);
        jmb.add(menu6);
        // 将菜单添加到窗体上
        this.setJMenuBar(jmb);
        this.setBackground(Color.WHITE);
        setVisible(true);
        jb1.addActionListener(new ActionListener() {//清屏
            public void actionPerformed(ActionEvent e) {
                Graphics g = getGraphics();
                g.clearRect(0, 95, 650, 700);
            }
        });
        jb2.addActionListener(new ActionListener() {//清除字符
        	public void actionPerformed(ActionEvent e) {
        		Graphics g = getGraphics();
        		g.clearRect(0, 100, 400, 100);
        	}
        });       
        zhengtaiyun.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Runnable runnable = new CloudModel();
                Thread thread = new Thread(runnable);
                thread.start();
                
            }
        });
        fenxing.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	Runnable runnable = new Koch();
            	Thread thread = new Thread(runnable);
            	thread.start();
            	
            }
        });
        K_means.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                KMeansPaint k=new KMeansPaint();
                k.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);//关闭窗口的方式，不写会调用默认的system.exit(0)全部销毁窗口
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
        TIN.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	new DrawFrame();
            }});

    }



    public static void main (String[] args)

    {
        new GraphicsFrame(800, 800);
    }

}
