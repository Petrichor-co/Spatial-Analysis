package Assignment3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class WriteFile {

	public static void main(String[] args) throws IOException {
		File file = new File("D:\\学习资料、作业\\空间分析\\data-Cloud-after.txt");
		ArrayList<DEMPoint> dem = Text.interpolation();
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		for(int i=0;i<dem.size();i++) {
			bw.write(dem.get(i).toString());
			bw.newLine();
		}
		bw.close();
		System.out.println("操作成功!");
	}
}
