package Assignment3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class GF extends JFrame{
	ArrayList<DEMPoint> dem = Text.interpolation();
	private static final long serialVersionUID = 1L;
	public static ArrayList<SlopePoint> slope_degrees = new ArrayList<SlopePoint>();//�¶ȵ㼯��
    
	public GP gp;  //��ͼ��
	public Text cp; //���ư�
	
	public GF (int height, int width)
	{
 
		setTitle("DEMͼ�����");
 
		setSize(width, height);
		
		setLocationRelativeTo(null);//���þ���
 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//���ùرս���
 
		setLayout(new BorderLayout(5, 5));
 
		gp = new GP(this);
		this.add(gp, BorderLayout.CENTER);//CENTER��EAST��WEST��NORTH��SOUTH
		
        
		setVisible(true);
		 
		
		
	}



}