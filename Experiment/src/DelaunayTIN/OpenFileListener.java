package DelaunayTIN;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class OpenFileListener implements ActionListener{

	/**
	 * 主窗口界面
	 */
	private DrawFrame mDrawFrame;

	/**
	 * @param mDrawFrame
	 */
	public OpenFileListener(DrawFrame mDrawFrame){
		this.mDrawFrame = mDrawFrame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		    JFileChooser jfc=new JFileChooser();
	        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			//jfc.setCurrentDirectory(new File("C:\\Users\\Administrator\\Desktop));
	        jfc.setFileFilter(new FileFilter() {			
				@Override
				public String getDescription() {    
					return ".txt";
				}
				@Override
				public boolean accept(File f) {
					if (f.isDirectory())return true;
					return f.getName().endsWith(".txt");
				}
			});

	        jfc.showDialog(new JLabel(), "选择DEM坐标文件");

	        File file=jfc.getSelectedFile();
			FileUtil fileUtil = new FileUtil();

		    mDrawFrame.setFilePath(file.getAbsolutePath());

		    DrawCanvas.newInstance().setDEMPoints(fileUtil.readAndHandleFile(file.getAbsolutePath()));
			DrawCanvas.newInstance().drawDEMPoints();
			DrawCanvas.newInstance().repaint();

	}
}

