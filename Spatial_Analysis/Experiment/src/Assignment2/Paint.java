package Assignment2;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.text.DecimalFormat;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import Kmeans.KMeansCalc;

public class Paint extends JFrame {

    int stepCount = 0,countflag=0;
    private final int FREAME_X = 20;//用于调整坐标轴位置
    private final int FREAME_Y = 80;
    private final int FREAME_WIDTH = 650;// 横  
    private final int FREAME_HEIGHT = 550;// 纵  
    // 原点坐标  
    private final int Origin_X = FREAME_X + 50;// X轴原点 70
    private final int Origin_Y = FREAME_Y + FREAME_HEIGHT - 150;// Y轴原点 580

    // X,Y轴终点坐标  
    private final int XAxis_X = FREAME_X + FREAME_WIDTH - 30;//670
    private final int XAxis_Y = Origin_Y;//580
    private final int YAxis_X = Origin_X;//70
    private final int YAxis_Y = FREAME_Y;//80

    //分度值
    private final int INTERVAL = 100;
    
    
	private static final long serialVersionUID = 1L;

	public Paint()  {
		JPanel jp=new JPanel();
		jp.setBackground(Color.white);
		jp.setPreferredSize(new Dimension(800,600));
		jp.setVisible(true);
		this.add(jp,BorderLayout.CENTER);
		
		this.setTitle("正态云模型");

        this.setSize(800,600);

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);//仅关闭此窗口

		
		this.setVisible(true);
		//继承了JFrame的类是先初始化构造函数(构造函数里面有Graphics graphics=getGraphics()),之后再调用init()函数实现设置setVisible（true）;因而getGraphics()返回都是null。
		/////////画正态云点///////////////////////////////
        Graphics g =jp.getGraphics();
        for(int i=0;i<10240;i++) {
			int u0=(int)(GaussianModel.u[i]*100);
			System.out.println(u0);
//			if(i == 10238) {				 
//				try {
//					Thread.sleep(5000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}				
//			}
			int x=(int)(GaussianModel.x[i]*10+Origin_X);
			int u=(int)(GaussianModel.u[i]*100+Origin_Y);
			int u2=Origin_Y*2-u;			
			g.setColor(Color.RED);
			g.fillOval(x, u2, 2, 2);
        }
		////////////画坐标轴////////////////////////////////	
        Graphics2D g2D = (Graphics2D) g;
        Color c = new Color(65, 197, 200);
        g.setColor(c);
        // 画坐标轴  
        g2D.setStroke(new BasicStroke(Float.parseFloat("2.0F")));// 轴线粗度  
        // X轴以及方向箭头  
        g.drawLine(Origin_X, Origin_Y, XAxis_X, XAxis_Y);// x轴线的轴线  
        g.drawLine(XAxis_X, XAxis_Y, XAxis_X - 5, XAxis_Y - 5);// 上边箭头  
        g.drawLine(XAxis_X, XAxis_Y, XAxis_X - 5, XAxis_Y + 5);// 下边箭头  

        // Y轴以及方向箭头  
        g.drawLine(Origin_X, Origin_Y, YAxis_X, YAxis_Y);
        g.drawLine(YAxis_X, YAxis_Y, YAxis_X - 5, YAxis_Y + 5);
        g.drawLine(YAxis_X, YAxis_Y, YAxis_X + 5, YAxis_Y + 5);

        // 画X轴上的时间刻度（从坐标轴原点起，每隔TIME_INTERVAL(时间分度)像素画一时间点，到X轴终点止）  
        g.setColor(Color.BLUE);
        g2D.setStroke(new BasicStroke(Float.parseFloat("1.0f")));

        // X轴刻度依次变化情况  
        int xyString=0;
        for (int i = Origin_X; i < XAxis_X; i += INTERVAL) {
            g.drawString(" " + xyString, i - 10, Origin_Y + 20);
            xyString++;
        }
        g.drawString("X", XAxis_X + 5, XAxis_Y + 5);
        xyString=0;
        // 画Y轴上刻度  
        for (int i = Origin_Y; i > YAxis_Y; i -= INTERVAL) {
            g.drawString(xyString + " ", Origin_X - 30, i + 3);
            xyString++;
        }
        g.drawString("Y", YAxis_X - 5, YAxis_Y - 5);// 刻度小箭头值
  
			
		
		
	}
	
	
}
