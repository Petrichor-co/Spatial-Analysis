package AssignmentAll;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadNewFile {
	
	/**
	 * ��ȡ���ݣ��������ݴ洢��������
	 * @return
	 */
	public static ArrayList<ArrayList<point>> readnewFile() {//����ֵArrayList<ArrayList<Point>>
		File file = new File("D:\\ѧϰ���ϡ���ҵ\\�ռ����\\ʵ��\\Experiment\\src\\AssignmentAll\\data-Cloud_ɢ������.txt");
		BufferedReader reader = null;
		ArrayList<ArrayList<point>> sum = null;
		try {
			reader = new BufferedReader(new FileReader(file));//��ȡ����
			String tempString = null;
			int count = 0;
			//�����е�洢��������
			sum = new ArrayList<ArrayList<point>>();//sum��<ArrayList<Point>�͵ļ���
			ArrayList<point> points = null;//����points����
			
			while((tempString = reader.readLine())!=null) {
				System.out.println("tempString��ֵΪ: "+tempString);
				String[] str = tempString.split(" |	");//String������str���á� ����Ϊ�ָ���(split)
				//System.out.println("str���ȣ�"+str.length);
				Double[] filePoint = new Double[str.length];//Double������filePoint
				//�洢���ݵ�ļ���
				points = new ArrayList<point>();
				for(int i = 0;i<str.length;i++) {
					filePoint[i] = new Double(str[i]);//���ļ���ȡ���ݸ�filePoint��ֵ
					//System.out.println(filePoint[i]);
				}
				
				if(count<9357) {//�ļ���һ����9357����
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
				} catch (IOException e) {
				}
			}
		}
		return sum;
		
		
	}

	public static void main(String[] args) {
		readnewFile();
	}
}
