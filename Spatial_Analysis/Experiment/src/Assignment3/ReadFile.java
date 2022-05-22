package Assignment3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadFile {

	/**
	 * 读取数据，并将数据存储到集合中
	 * @return
	 */
	public static ArrayList<ArrayList<point>> readFile() {//返回值ArrayList<ArrayList<Point>>
		File file = new File("D:\\学习资料、作业\\空间分析\\实验\\Experiment\\src\\Assignment3\\DEM-before.txt");
		BufferedReader reader = null;
		ArrayList<ArrayList<point>> sum = null;
		try {
			reader = new BufferedReader(new FileReader(file));//读取数据
			String tempString = null;
			int count = 0;
			//将所有点存储到集合中
			sum = new ArrayList<ArrayList<point>>();//sum是<ArrayList<Point>型的集合
			ArrayList<point> points = null;//创建points集合
			
			while((tempString = reader.readLine())!=null) {
				//System.out.println("tempString的值为: "+tempString);
				String[] str = tempString.split(",");//String型数组str，用“,”作为分隔符(split)
				Double[] filePoint = new Double[str.length-1];//Double型数组filePoint
				//存储数据点的集合
				points = new ArrayList<point>();
				for(int i = 0;i<str.length-1;i++) {
					filePoint[i] = new Double(str[i+1]);//从文件中取数据给filePoint赋值
					//System.out.println(filePoint[i]);
				}
				
				if(count<144) {//文件中一共有144个点
					points.add(new point(filePoint[0], filePoint[1], filePoint[2]));
					count++;
					sum.add(points);
				}
				
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return sum;
		
		
	}

	public static void main(String[] args) {
		readFile();
	}
}
