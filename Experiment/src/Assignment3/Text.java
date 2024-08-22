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
	public static ArrayList<DEMPoint> dem = new ArrayList<DEMPoint>();//��ֵ��ĸ����㼯��
	public static ArrayList<SlopePoint> slope_degrees = new ArrayList<SlopePoint>();//�¶ȵ㼯��
	Point gra1 = new Point();//n-1 �������
	Point gra2 = new Point();//n �������
	Point gra = new Point();//���ĵ�����
    double perimeter1=0; //n-1 ���ߵı߳���
    double perimeter=0;
    double line=0;
    int[][] location =new int[100][2];
    double sum=0;
    double area=0;
    public int flag=-1;
    JButton getArea=new JButton("���");
    JButton getPerimeter=new JButton("�ܳ�");
    JButton lineLength=new JButton("�߳�");
    JButton gravitycenter=new JButton("����");
	JButton jb1;
	JToolBar jtb;// ������
    // �˵������
    JMenuBar jmb;
    JMenu menu1, menu2, menu3, menu4, menu5;
    JMenuItem fanjvli, podu, poxiang, item4, item5, item6, item7;
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
		double[] dh = new double[sum.size()];//y������
		List<point> points = null;
		//ArrayList<DEMPoint> dem = new ArrayList<DEMPoint>();//��ֵ��ĸ����㼯��
		System.out.println("��������� "+demPoints.size());
		while(count<demPoints.size()) {
			DEMPoint p = demPoints.get(count++);//ȡ��ÿһ��������
			double px = p.getX();//������x
			double py = p.getY();//������y
			double[] d = new double[sum.size()];//����Ϊsum���ȵ�����
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
						wz = wz + w;//Ȩ�صĺ�
						whz = whz + wh;//�߳�ֵ�ĺ�						
					}
					
				}
				if(num>=5)break;
			}
			System.out.println("���ڼ���� "+count+" ��"+"��ǰ������Ȩ�صĺͣ�"+wz+"  ��ǰ������̵߳ĺͣ�"+whz);
			DecimalFormat df = new DecimalFormat("###.000");//��ʽ��ʮ�������֣��������� ����С�� 0������#����
			double ph = Double.parseDouble(df.format(whz/wz));//������h
			dem.add(new DEMPoint(px, py, ph));
	        
		}
		System.out.println("�������Ȩ��ֵ�ɹ���");
		//System.out.println("dem��ֵΪ��"+dem);
		return dem;
	}
	       
	public Text() {
//		Panel gp =new Panel();
//		gp.setPreferredSize(new Dimension(200, 800));
//        gp.setBackground(Color.WHITE);
//        this.add(gp);
        
		setTitle("�γ����");
		setSize(800, 800);
        setLocationRelativeTo(null);//���þ���        
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//���ùرս���		
        jb1 = new JButton("����");
        
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
		
		// ����������
        jtb = new JToolBar();
        jtb.add(jb1);
        jtb.add(getPerimeter);
        jtb.add(getArea);
        jtb.add(lineLength);        
        jtb.add(gravitycenter);
        this.add(jtb, BorderLayout.NORTH);

        //�����˵���
        jmb = new JMenuBar();
        menu1 = new JMenu("��ֵ�㷨(F)");
        menu1.setMnemonic('F');// �������Ƿ�
        menu2 = new JMenu("������������ȡ(E)");
        menu2.setMnemonic('E');
        
        fanjvli = new JMenuItem("�������Ȩ��");
        podu = new JMenuItem("�¶Ȼ���");
        poxiang = new JMenuItem("�������");
        //�����ı�������
        jta = new JTextArea();
     
        // ���˵���ӵ��˵�����
        menu1.add(fanjvli);
        menu2.add(podu);
        menu2.addSeparator();// ��ӷָ���
        menu2.add(poxiang);
        
        // ���˵���ӵ��˵�����
        jmb.add(menu1);
        jmb.add(menu2);

        // ���˵���ӵ�������
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

      //����������¼�
        this.addMouseListener(new MouseListener() {


            List<Point> li = new ArrayList<Point>();//�߼���

            List<Point> tri = new ArrayList<Point>();//�����μ���

            List<Point> pol = new ArrayList<Point>();//����μ���

            Graphics g; //Graphics���ṩ�����ļ���ͼ�λ��Ʒ�������Ҫ�У����߶Ρ������Ρ���Բ��������ɫ��ͼ�Ρ�����Բ����Բ����������εȡ�



            //������¼�
            public void mouseClicked(MouseEvent e) {

                if (pointsButton.isSelected()) {//�㰴ť��ѡ��

                    int x, y;

                    x = e.getX();

                    y = e.getY();

                    g = getGraphics();

                    g.setColor(Color.red);

                    g.fillOval(x+7, y+30, 2, 2);
                    //����Բ��x-Ҫ�����Բ�����Ͻǵ� x ���� y-Ҫ�����Բ�����Ͻǵ� y ����  width - Ҫ�����Բ�Ŀ�� height - Ҫ�����Բ�ĸ߶�

                } else if (linesButton.isSelected()) {//�߰�ť

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

                        g.fillOval(e.getX()+7, e.getY()+30, 2, 2);//fillOval����

                    }

                } else if (trianglesButton.isSelected()) {//�����ΰ�ť

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
                        //�����ܳ�
                        for (int i = 0; i <= tri.size()-1; i++) {
                            if(i<tri.size()-1) {
                                perimeter1 += Math.sqrt(Math.pow(tri.get(i + 1).x - tri.get(i).x, 2) + Math.pow(tri.get(i + 1).y - tri.get(i).y, 2));
                            }
                            if(i==tri.size()-1) {
                                perimeter = perimeter1+Math.sqrt(Math.pow(tri.get(i).x - tri.get(0).x, 2) + Math.pow(tri.get(i).y - tri.get(0).y, 2));
                            }
                    }
                        System.out.println("�������ܳ��Ѽ�¼��");
                        //�������
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
                    if(area<0) area=-area; //ת��Ϊ����
                    System.out.println("����������Ѽ�¼��");    
                        //��������     
                            gra.x+= (tri.get(0).x + tri.get(1).x + tri.get(2).x)/3;
                            gra.y+= (tri.get(0).y + tri.get(1).y + tri.get(2).y)/3;
                    
                        flag=3;                
                        //repaint();
                        tri.clear();     

                    } else {

                        g = getGraphics();

                        g.setColor(Color.red);

                        g.fillOval(e.getX()+7, e.getY()+30, 2, 2);//fillOval����

                    }

                } else if (polygonsButton.isSelected()) {//�����

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
                        //�����ܳ�
                        for (int i = 0; i <= pol.size()-1; i++) {
                            if(i<pol.size()-1) {
                                perimeter1 += Math.sqrt(Math.pow(pol.get(i + 1).x - pol.get(i).x, 2) + Math.pow(pol.get(i + 1).y - pol.get(i).y, 2));
                            }
                            if(i==pol.size()-1) {
                                perimeter = perimeter1+Math.sqrt(Math.pow(pol.get(i).x - pol.get(0).x, 2) + Math.pow(pol.get(i).y - pol.get(0).y, 2));
                            }
                    }
                        System.out.println("������ܳ��Ѽ�¼��");
                        //���ö�ά����������
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
                        System.out.println("���������Ѽ�¼��");
                        if(area<0) area=-area; //ת��Ϊ����
                        //��ӡ������ĸ���������
                        System.out.println("��ĸ���Ϊ��"+pol.size());
                        for (int i = 0; i <=pol.size()-1 ; i++) {
                            System.out.println("["+"X"+(i+1)+"="+pol.get(i).x+","+"Y"+(i+1)+"="+pol.get(i).y+"]");
                        }
                        //��������
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
                //////////////////////��ť��Ӧ�¼�����////////////////////////////
                if(flag==2) {
                	lineLength.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                        	g = getGraphics();
                            g.setColor(Color.BLACK);
                            g.drawString("�߳���" + line,50,200);
                        }
                    });
                }
                
                if(flag==3) {
                	//��������ť��Ӧ�¼�
                    getArea.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            g = getGraphics();
                            g.setColor(Color.BLACK);
                            g.drawString("�����" + area,50,250);
                        }
                    });
                    //����ܳ���ť��Ӧ�¼�
                    getPerimeter.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            g = getGraphics();
                            g.setColor(Color.BLACK);
                            g.drawString("�ܳ���" + perimeter,50,300);
                        }
                    });
                    //������İ�ť��Ӧ�¼�
                    gravitycenter.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            g = getGraphics();
                            g.setColor(Color.BLUE);
                            g.drawString("���ģ�" + "X ���꣺"+gra.x+"  Y ���꣺"+gra.y,100,120);
                        }
                    });
                }
                
                if(flag==4) {
                	//��������ť��Ӧ�¼�
                    getArea.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            g = getGraphics();
                            g.setColor(Color.BLACK);
                            g.drawString("�����" + area,50,350);
                        }
                    });
                    //����ܳ���ť��Ӧ�¼�
                    getPerimeter.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            g = getGraphics();
                            g.setColor(Color.BLACK);
                            g.drawString("�ܳ���" + perimeter,50,400);
                        }
                    });
                   //������İ�ť��Ӧ�¼�
                    gravitycenter.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            g = getGraphics();
                            g.setColor(Color.BLUE);
                            g.drawString("���ģ�" + "X ���꣺"+gra.x+"  Y ���꣺"+gra.y,50,450);
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