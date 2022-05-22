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
    public static ArrayList<DEMPoint> dem = new ArrayList<DEMPoint>();//��ֵ��ĸ����㼯��
    public static ArrayList<SlopePoint> slope_degrees = new ArrayList<SlopePoint>();//�¶ȵ㼯��
    JButton jb1,jb2,jb3,jb4;
    JToolBar jtb;// ������
    // �˵������
    JMenuBar jmb;
    JMenu menu1, menu2, menu3, menu4, menu5,menu6,menu7,menu8,menu9;
    JMenuItem fanjvli, podu, poxiang,zhengtaiyun,fenxing,TIN,K_means;
    JTextArea jta;//�ı�������

    private static final long serialVersionUID = 1L;
    /**
     * ȷ������������
     * @return
     */
    public static ArrayList<DEMPoint> border(){
        //��ȡ�ļ�������
        ArrayList<ArrayList<point>> sum = ReadNewFile.readnewFile();
        System.out.println("��ȡ��ϣ�");
        double[] dx = new double[sum.size()];//x������
        double[] dy = new double[sum.size()];//y������
        List<point> points = null;//Point���͵ı�������x��y��h����ֵ
        //��dx dy��ֵ
        for(int i=0;i<sum.size();i++) {
            //System.out.print(i);
            //System.out.println(sum.get(i));
            points = new ArrayList<point>(sum.get(i));
            //System.out.println(points);
            List<Double> xd = points.stream().map(point::getX).collect(Collectors.toList());//��ȡx��ֵ
            dx[i] = xd.get(0);
            List<Double> yd = points.stream().map(point::getY).collect(Collectors.toList());//��ȡy��ֵ
            dy[i] = yd.get(0);
        }
        //�߽緶Χ
        System.out.println("����ȷ�������߽緶Χ......");
        double xmax = dx[0];
        double xmin = dx[0];
        double ymax = dy[0];
        double ymin = dy[0];
        for(int i=0;i<dx.length;i++) {
            //System.out.println("dx["+i+"]��ֵΪ��"+dx[i]);
            if(xmax<dx[i]) {
                xmax = dx[i];
            }
            if(xmin>dx[i]) {
                xmin = dx[i];
            }
        }
        for(int i=0;i<dy.length;i++) {
            //System.out.println("dy["+i+"]��ֵΪ��"+dy[i]);
            if(ymax<dy[i]) {
                ymax = dy[i];
            }
            if(ymin>dy[i]) {
                ymin = dy[i];
            }
        }
        System.out.println("x���ֵΪ��"+xmax+"  "+"x��СֵΪ��"+xmin);
        System.out.println("y���ֵΪ��"+ymax+"  "+"y��СֵΪ��"+ymin);
        int demXmin = (int) (xmin);
        int demYmin = (int) (ymin);

        //�߽糤,�����ߴ�ȡ 1
        int numX = (int) ((xmax - xmin)/5+1);//(n�����ֵΪ 1 �Ŀ�϶)+1 �������
        int numY = (int) ((ymax - ymin)/5+1);
        System.out.println("numX: "+numX+" "+"numY: "+numY);

        //�������������
        double[] demX = new double[numX];
        double[] demY = new double[numY];
        ArrayList<DEMPoint> demPoints = new ArrayList<DEMPoint>();//DEMPoint�������ݣ�����������������x��y            
        for(int i=0;i<numX;i++) {
            for(int j=0;j<numY;j++) {
                demX[i] = demXmin + i * 5;
                demY[j] = demYmin + j * 5;
                //System.out.println("���������꣺"+"demX["+i+"]: "+demX[i]+"demY["+i+"]: "+demY[j]);
                demPoints.add(new DEMPoint(demX[i], demY[j]));
                //System.out.println(demPoints);
            }

        }
        System.out.println("������������");
        return demPoints;
    }
    /**
     * ����ƽ��������Ȩ
     * @return
     */
    public static ArrayList<DEMPoint> interpolation() {
        int r;//�����뾶
        int num;//��������
        int count = 0;//�����������ݼ��е�����
        ArrayList<ArrayList<point>> sum = ReadNewFile.readnewFile();//ԭʼ���ݼ���
        ArrayList<DEMPoint> demPoints = border();//�����㼯��
        double[] dx = new double[sum.size()];//x������
        double[] dy = new double[sum.size()];//y������
        double[] dh = new double[sum.size()];//h������
        List<point> points = null;
        //ArrayList<DEMPoint> dem = new ArrayList<DEMPoint>();//��ֵ��ĸ����㼯��
        System.out.println("��������� "+demPoints.size());
        while(count<demPoints.size()) {
            DEMPoint p = demPoints.get(count++);//ȡ��ÿһ��������
            double px = p.getX();//������x
            double py = p.getY();//������y
            double[] d = new double[sum.size()];//����Ϊsum������
            r = 0;
            num = 0;
            //System.out.println("px = "+px+"  py = "+py+"sum.size(): "+sum.size());
            //ȡ��ÿһ��ԭʼ��
            for(int i=0;i<sum.size();i++) {
                points = new ArrayList<point>(sum.get(i));
                List<Double> xd = points.stream().map(point::getX).collect(Collectors.toList());
                dx[i] = xd.get(0);//ԭʼ��x
                List<Double> yd = points.stream().map(point::getY).collect(Collectors.toList());
                dy[i] = yd.get(0);//ԭʼ��y
                List<Double> hd = points.stream().map(point::getH).collect(Collectors.toList());
                dh[i] = hd.get(0);//ԭʼ��h
                //System.out.println("dh = "+dh[i]);
                //���㵱ǰ�����㵽ÿ��ԭʼ��ľ���
                d[i] = Math.sqrt((px-dx[i])*(px-dx[i])+(py-dy[i])*(py-dy[i]));
                //System.out.print(i+"  "+d[i]);
            }
            //System.out.println("��"+count+"�������㵽ԭʼ��ľ���Ϊ��"+d[count]);

            //����ƽ��������Ȩ
            double w = 0;//Ȩֵ
            double wz = 0;
            double wh = 0;
            double whz = 0;
            //�������Ȩ��ֵ�����㷨
            for(;r<100;r=r+5) {
                for(int i=0;i<sum.size();i++) {
                    if(d[i] < r) {
                        w = 1/d[i]*d[i];//����Ȩ��
                        wh = w*dh[i];//����Ȩ��*�߳�
                        num++;//num��0��ʼ�ӣ�Ҳ����˵���ֻ������μ�Ȩ����
                        wz = wz + w;//����Ȩ�صĺ�
                        whz = whz + wh;//����Ȩ��*�̵߳ĺ�
                    }

                }
                if(num>=5)break;
            }
            System.out.println("���ڼ���� "+count+" ��"+"��ǰ������ ����Ȩ�صĺͣ�"+wz+"  ��ǰ������ ����Ȩ��*�̵߳ĺͣ�"+whz);
            DecimalFormat df = new DecimalFormat("###.000");//��ʽ��ʮ�������֣��������� ����С�� 0������#����
            double ph = Double.parseDouble(df.format(whz/wz));//������h
            dem.add(new DEMPoint(px, py, ph));

        }
        System.out.println("�������Ȩ��ֵ�ɹ���");
        //System.out.println("dem��ֵΪ��"+dem);
        return dem;
    }

    public GraphicPanel gp;

    public ControlPanel cp;

    public GraphicsFrame (int height, int width)

    {

        setTitle("�γ����");

        setSize(width, height);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//ʹ�� System exit �����˳�Ӧ�ó��򡣽���Ӧ�ó�����ʹ�á�

        jb1 = new JButton("����");
        jb2 = new JButton("����ַ�");
        setLayout(new BorderLayout(5, 5));


//        JPanel gp1 = new JPanel();
//        gp1.setPreferredSize(new Dimension(500,500));
//        gp1.setBackground(Color.GREEN);
//        this.add(gp1,BorderLayout.SOUTH);
        
        gp = new GraphicPanel(this);

        this.add(gp, BorderLayout.CENTER);

        cp = new ControlPanel(this);

        this.add(cp, BorderLayout.EAST);

        // ����������
        jtb = new JToolBar();
        jtb.add(jb1);
        jtb.add(jb2);
        this.add(jtb, BorderLayout.NORTH);

        //�����˵���
        jmb = new JMenuBar();
        menu1 = new JMenu("��ֵ�㷨(F)");
        menu1.setMnemonic('F');// �������Ƿ�
        menu2 = new JMenu("������������ȡ(E)");
        menu2.setMnemonic('E');
        menu3 = new JMenu("��ͼ(G)");
        menu3.setMnemonic('G');
        menu4 = new JMenu("��ȷ���Է���(I)");
        menu4.setMnemonic('I');
        menu5 = new JMenu("KMeans(J)");
        menu5.setMnemonic('J');
        menu6 = new JMenu("TIN(H)");
        menu6.setMnemonic('H');

        fanjvli = new JMenuItem("�������Ȩ��");
        podu = new JMenuItem("�¶Ȼ���");
        poxiang = new JMenuItem("�������");
        zhengtaiyun = new JMenuItem("��̬���㷨");
        fenxing = new JMenuItem("Koch�����㷨");
        TIN=new JMenuItem("����TIN");
        K_means=new JMenuItem("K��ֵ���������");
        //�����ı�������
        jta = new JTextArea();
        // ���˵���ӵ��˵�����
        menu1.add(fanjvli);
        menu2.add(podu);
        menu2.addSeparator();// ��ӷָ���
        menu2.add(poxiang);
        menu4.add(zhengtaiyun);
        menu4.add(fenxing);
        menu5.add(K_means);
        menu6.add(TIN);
        // ���˵���ӵ��˵�����
        jmb.add(menu1);
        jmb.add(menu2);
        jmb.add(menu4);
        jmb.add(menu5);
        jmb.add(menu6);
        // ���˵���ӵ�������
        this.setJMenuBar(jmb);
        this.setBackground(Color.WHITE);
        setVisible(true);
        jb1.addActionListener(new ActionListener() {//����
            public void actionPerformed(ActionEvent e) {
                Graphics g = getGraphics();
                g.clearRect(0, 95, 650, 700);
            }
        });
        jb2.addActionListener(new ActionListener() {//����ַ�
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
                k.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);//�رմ��ڵķ�ʽ����д�����Ĭ�ϵ�system.exit(0)ȫ�����ٴ���
            }
        });
        fanjvli.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                interpolation();
                Graphics g = getGraphics();//Graphics����Ҫ����̳�JFrame����JPanel���������²���ʵ������������������������ϵͳ�Զ����õġ�
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
                int s=72;//��index=72��ʼ
                for (int ii = 0; ii<60; ii++) {//60��
                    for(int j=0;j<69;j++) {//69��
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
                int s=72;//��index=72��ʼ
                for (int ii = 0; ii<60; ii++) {//60��
                    for(int j=0;j<69;j++) {//69��
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
                            G.setColor(new Color(135,206,250));//ǳ��
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
