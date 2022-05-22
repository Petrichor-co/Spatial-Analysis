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

    private static final long serialVersionUID = 1L;

    public GraphicsFrame gf; //ͼ�ο��

    public GraphicPanel gp;  //ͼ�����


    public ControlPanel(GraphicsFrame _gf) {

        super();

        gf = _gf;

        gp = gf.gp;


        ButtonGroup bg = new ButtonGroup(); //��ť

        final JRadioButton pointsButton = new JRadioButton("��", false);

        final JRadioButton linesButton = new JRadioButton("��", false);

        final JRadioButton trianglesButton = new JRadioButton("������",false);

        final JRadioButton polygonsButton = new JRadioButton("�����", false);


        
        bg.add(pointsButton);

        bg.add(linesButton);

        bg.add(trianglesButton);

        bg.add(polygonsButton);

        //setLayout(new GridLayout(20,1,2,2));   //����һ�����ڣ���������ָ�������������Լ����ˮƽ������һ���������񲼾�

        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        
//        GridBagConstraints s= new GridBagConstraints();//����һ��GridBagConstraints��
//        s.fill = GridBagConstraints.BOTH;//ʹ�����ȫ��������ʾ����
//        s.gridwidth=1;//�÷������������ˮƽ��ռ�õĸ����������Ϊ0����˵��������Ǹ��е����һ��
//        s.weightx = 0;//�÷����������ˮƽ��������ȣ����Ϊ0��˵�������죬��Ϊ0�����Ŵ�������������죬0��1֮��
//        s.weighty=0;//�÷������������ֱ��������ȣ����Ϊ0��˵�������죬��Ϊ0�����Ŵ�������������죬0��1֮��
        //�������......
        

        //ʵ������ť����
        add(pointsButton);

        add(linesButton);

        add(trianglesButton);

        add(polygonsButton);

        add(getPerimeter);

        add(getArea);

        add(lineLength);
        
        add(gravitycenter);


        //����������¼�
        gp.addMouseListener(new MouseListener() {


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

                    Graphics g = gf.getGraphics();

                    g.setColor(Color.red);

                    g.fillOval(x+7, y+30, 2, 2);
                    //����Բ��x-Ҫ�����Բ�����Ͻǵ� x ���� y-Ҫ�����Բ�����Ͻǵ� y ����  width - Ҫ�����Բ�Ŀ�� height - Ҫ�����Բ�ĸ߶�

                } else if (linesButton.isSelected()) {//�߰�ť

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

                        g.fillOval(e.getX()+7, e.getY()+30, 2, 2);//fillOval����

                    }

                } else if (trianglesButton.isSelected()) {//�����ΰ�ť

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
                        repaint();
                        tri.clear();     

                    } else {

                        g = gf.getGraphics();

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
                        	g = gf.getGraphics();
                            g.setColor(Color.BLACK);
                            g.drawString("�߳���" + line,50,120);
                        }
                    });
                }
                
                if(flag==3) {
                	//��������ť��Ӧ�¼�
                    getArea.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            g = gf.getGraphics();
                            g.setColor(Color.BLACK);
                            g.drawString("�����" + area,100,100);
                        }
                    });
                    //����ܳ���ť��Ӧ�¼�
                    getPerimeter.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            g = gf.getGraphics();
                            g.setColor(Color.BLACK);
                            g.drawString("�ܳ���" + perimeter,100,80);
                        }
                    });
                    //������İ�ť��Ӧ�¼�
                    gravitycenter.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            g = gf.getGraphics();
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
                            g = gf.getGraphics();
                            g.setColor(Color.BLACK);
                            g.drawString("�����" + area,50,100);
                        }
                    });
                    //����ܳ���ť��Ӧ�¼�
                    getPerimeter.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            g = gf.getGraphics();
                            g.setColor(Color.BLACK);
                            g.drawString("�ܳ���" + perimeter,50,80);
                        }
                    });
                   //������İ�ť��Ӧ�¼�
                    gravitycenter.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            g = gf.getGraphics();
                            g.setColor(Color.BLUE);
                            g.drawString("���ģ�" + "X ���꣺"+gra.x+"  Y ���꣺"+gra.y,50,120);
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