package Kmeans;

/*
 * KMeansPaint
 * 实现图形界面部分
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class KMeansPaint extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	static int Flag = 0; //End judge
	static int n;//点的数目
    int stepCount = 0,countflag=0;
    private final int FREAME_X = 20;
    private final int FREAME_Y = 80;
    private final int FREAME_WIDTH = 650;// 横  
    private final int FREAME_HEIGHT = 550;// 纵  
    // 原点坐标  
    private final int Origin_X = FREAME_X + 50;
    private final int Origin_Y = FREAME_Y + FREAME_HEIGHT - 50;

    // X,Y轴终点坐标  
    private final int XAxis_X = FREAME_X + FREAME_WIDTH - 30;
    private final int XAxis_Y = Origin_Y;
    private final int YAxis_X = Origin_X;
    private final int YAxis_Y = FREAME_Y;

    //分度值
    private final int INTERVAL = 30;

    JLabel cluster1 =new JLabel("No.1Cluster:");
    JLabel cluster2 =new JLabel("No.2Cluster:");
    JLabel cluster3 =new JLabel("No.3Cluster:");
    JLabel countResult = new JLabel();
    JLabel tip;
    JButton bt1 = new JButton("加载数据");
    JButton bt2 = new JButton("自动演示");
    JButton bt3 = new JButton("单步演示");
    JButton bt4 = new JButton("查看数据");
    JTextArea lab = new JTextArea();

    JFrame newF = new JFrame("数据集");
    KMeansCalc km=new KMeansCalc();
    private MyCanva trendChartCanvas = new MyCanva();

    public KMeansPaint(){    	
        super("K-均值聚类算法演示系统");
        System.out.println("请输入随机生成的点的个数：");
     	String o=JOptionPane.showInputDialog("请输入随机生成的点的个数：");
     	n = Integer.parseInt(o);
     	tip = new JLabel("注：随机生成 "+n+" 个坐标数据，3个初始中心点坐标，均为整数。");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(300, 200, 1000, 700);
        bt1.setBounds(XAxis_X+60,Origin_Y-110, 100, 40);
        bt4.setBounds(XAxis_X+200,Origin_Y-110, 100, 40);
        bt2.setBounds(XAxis_X+60,Origin_Y-50, 100, 40);
        bt3.setBounds(XAxis_X+200,Origin_Y-50, 100, 40);
        cluster1.setBounds(XAxis_X+60, YAxis_Y, 200, 40);
        cluster2.setBounds(XAxis_X+60, YAxis_Y+50, 200, 40);
        cluster3.setBounds(XAxis_X+60, YAxis_Y+100, 200, 40);
        cluster1.setFont(new java.awt.Font("Dialog", 1, 15));
        cluster2.setFont(new java.awt.Font("Dialog", 1, 15));
        cluster3.setFont(new java.awt.Font("Dialog", 1, 15));
        countResult.setBounds(XAxis_X+60, YAxis_Y+150, 400, 40);
        countResult.setFont(new java.awt.Font("Dialog", 1, 17));
        tip.setBounds(Origin_X, Origin_Y+30, 400, 20);
        this.add(bt1);
        this.add(bt2);
        this.add(bt3);
        this.add(bt4);
        this.add(cluster1);
        this.add(cluster2);
        this.add(cluster3);
        this.add(countResult);
        this.add(tip);
        this.add(trendChartCanvas, BorderLayout.CENTER);
        this.setVisible(true);

        bt1.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                stepCount=0;
                countflag=1;
                km.ReSet();
                km.KMeansInit();
                repaint();
            }

        });

        bt2.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                new Thread(new Runnable() {
                    public void run() {
                        while (KMeansCalc.flag<KMeansCalc.k){
                            km.KMeansHandle();
                            stepCount++;
                            repaint();
                        }
                    }
                }).start();
            }

        });

        bt3.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                if (KMeansCalc.flag<KMeansCalc.k){
                    km.KMeansHandle();
                    stepCount++;
                    repaint();
                }
            }

        });

        bt4.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                if(countflag!=0){
                    lab.setText("");
                    lab.setFont(new java.awt.Font("Dialog", 0, 15));
                    lab.setLineWrap(true);
                    lab.setText(KMeansCalc.getData());
                    JScrollPane jscrollPane = new JScrollPane(lab);
                    newF.add(jscrollPane);
                    newF.setBounds(1300, 100, 350, 600);
                    newF.setVisible(true);
                }

            }

        });

    }


    class MyCanva extends JPanel {
        private static final long serialVersionUID = 1L;
        public void paintComponent(Graphics g) {
            Graphics2D g2D = (Graphics2D) g;
            Color c = new Color(65, 197, 200);
            g.setColor(c);
            super.paintComponent(g);

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

            DecimalFormat df = new DecimalFormat("######0.00");  //小数点保留两位
            cluster1.setText("No.1Cluster: ("+df.format(KMeansCalc.means[0].getX())+","+df.format(KMeansCalc.means[0].getY())+")");
            cluster2.setText("No.2Cluster: ("+df.format(KMeansCalc.means[1].getX())+","+df.format(KMeansCalc.means[1].getY())+")");
            cluster3.setText("No.3Cluster: ("+df.format(KMeansCalc.means[2].getX())+","+df.format(KMeansCalc.means[2].getY())+")");

            //标签文字
            if(countflag==0){
                countResult.setText("点击加载数据按钮载入数据");
            }
            else if(stepCount==0){
                //绘制初始元组
                for(int i=0;i<KMeansCalc.tuples.size();i++){
                    g.fillOval(Origin_X+Math.round((KMeansCalc.tuples.get(i).getX())*INTERVAL)-5,
                            Origin_Y-Math.round((KMeansCalc.tuples.get(i).getY())*INTERVAL)-5, 5, 5);//初始点的大小
                }
                countResult.setText("请选择自动演示或单步演示按钮");
            }
            else{
                //重绘元组颜色
                for(int i=0;i<KMeansCalc.k;i++){
                    for(int j=0;j<KMeansCalc.clusters[i].size();j++){
                        if(i==0){
                            g.setColor(Color.magenta);
                        }
                        else if(i==1){
                            g.setColor(Color.red);
                        }
                        else{
                            g.setColor(Color.yellow);
                        }
                        g.fillOval(Origin_X+Math.round((KMeansCalc.clusters[i].get(j).getX())*INTERVAL)-5,
                                Origin_Y-Math.round((KMeansCalc.clusters[i].get(j).getY())*INTERVAL)-5, 5, 5);//重绘的点的大小

                    }
                }

                if(KMeansCalc.flag<KMeansCalc.k){
                    countResult.setText("正在运算中...");
                }
                else{
                    countResult.setText("计算结束,一共进行了  "+stepCount+" 次迭代。");
                }
            }

            //绘制中心点及连线
            for(int i=0;i<KMeansCalc.k;i++){
                g.setColor(Color.PINK);
                g.fillOval(Origin_X+Math.round((KMeansCalc.means[i].getX())*INTERVAL)-5,
                        Origin_Y-Math.round((KMeansCalc.means[i].getY())*INTERVAL)-5, 10, 10);
                for(int j=0;j<KMeansCalc.clusters[i].size();j++){
                    g.drawLine(Origin_X+Math.round((KMeansCalc.means[i].getX())*INTERVAL),
                            Origin_Y-Math.round((KMeansCalc.means[i].getY())*INTERVAL)
                            ,Origin_X+Math.round((KMeansCalc.clusters[i].get(j).getX())*INTERVAL),
                            Origin_Y-Math.round((KMeansCalc.clusters[i].get(j).getY())*INTERVAL));
                }
            }

        }
    }

    public static void main(String[] args){
        new KMeansPaint();
    }
}
