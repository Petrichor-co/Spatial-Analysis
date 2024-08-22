package DelaunayTIN;

import java.io.*;

public class FileUtil {

	private FileReader fr1;
	private BufferedReader br1;
	private FileReader fr2;
	private BufferedReader br2;

	/**
	 * @param filePath
	 * @return DEM点的坐标
	 */
	public  Point[] readAndHandleFile(String filePath){

		Point[] DEMPoints = new Point[getFileLineCount(filePath)];
		try{
			fr1 = new FileReader(filePath);
			br1 = new BufferedReader(fr1);
			String line = null;
			try {
				int j = 0;
				while((line = br1.readLine()) != null){
					String[] lines = line.split(" ");
					DEMPoints[j++] = new Point(Double.parseDouble(lines[0]),Double.parseDouble(lines[1]));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally {
			try {
				fr1.close();
				br1.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return DEMPoints;

	}

	/**
	 * @param filePath(文件路径)
	 * @return 文件的行数
	 */
	private int getFileLineCount(String filePath){
		int lineCount = 0;
		try{
			fr2 =  new FileReader(filePath);
			br2 = new BufferedReader(fr2);
			try {
				while(br2.readLine() != null){
                    lineCount++;
                }
			} catch (IOException e) {
				e.printStackTrace();
			}
			return lineCount;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			try {
				br2.close();
				fr2.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return lineCount;
	}
}

