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
	public static ArrayList<SlopePoint> slope_degrees = new ArrayList<SlopePoint>();//坡度点集合
    
	public GP gp;  //画图板
	public Text cp; //控制板
	
	public GF (int height, int width)
	{
 
		setTitle("DEM图像绘制");
 
		setSize(width, height);
		
		setLocationRelativeTo(null);//设置居中
 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置关闭进程
 
		setLayout(new BorderLayout(5, 5));
 
		gp = new GP(this);
		this.add(gp, BorderLayout.CENTER);//CENTER、EAST、WEST、NORTH、SOUTH
		
        
		setVisible(true);
		 
		
		
	}



}