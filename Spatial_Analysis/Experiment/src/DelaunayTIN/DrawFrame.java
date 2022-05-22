package DelaunayTIN;
import javax.swing.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;


public class DrawFrame extends JFrame{

	private static final long serialVersionUID = 1L;

	/**
	 * 打开的DEM坐标文件的路径
	 */
	private String filePath;

	/**
	 * 绘图区域
	 */
	private DrawCanvas drawArea;

	/**
	 * 主窗口菜单项
	 */
	private JMenuBar mainMenuBar;

	/**
	 * 主窗口构造方法
	 */
	public DrawFrame(){
		init();
		this.setName("三角网生成算法");
		this.setTitle("三角网生成算法");
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setJMenuBar(mainMenuBar);
		this.add(drawArea);
		this.setVisible(true);
	}


	/**
	 *窗口初始化，在构造方法中被调用
	 */
	private void init(){
		drawArea = DrawCanvas.newInstance();
		drawArea.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				int count = e.getWheelRotation();
				if(count < 0){
					drawArea.setScaleSize(drawArea.getScaleSize()/1.2f);
					drawArea.repaint();
				}else{
					drawArea.setScaleSize(drawArea.getScaleSize()*1.2f);
					drawArea.repaint();
				}
			}
		});

		/**
		 * 文件菜单项
		 */
		JMenu fileMenu = new JMenu("文件");
		JMenuItem importDateItem = new JMenuItem("导入DEM数据点");
		importDateItem.addActionListener(new OpenFileListener(this));
		fileMenu.add(importDateItem);

		/**
		 * 绘图菜单项
		 */
		JMenu drawMenu = new JMenu("绘图");
		JMenuItem drawTINItem = new JMenuItem("绘制三角网");
        drawTINItem.addActionListener(new DrawTINListener(this));
		drawMenu.add(drawTINItem);

		/**
		 * 工具菜单项
		 */
		JMenu toolMenu = new JMenu("工具");
		JMenuItem largeTwoTimesItem = new JMenuItem("放大两倍");
		largeTwoTimesItem.addActionListener(new LargeTwoTimesListener());
		JMenuItem scaleTwoTimesItem = new JMenuItem("缩小两倍");
		scaleTwoTimesItem.addActionListener(new ScaleTwoTimesListener());
		toolMenu.add(largeTwoTimesItem);
		toolMenu.add(scaleTwoTimesItem);
        	
		mainMenuBar = new JMenuBar();
		mainMenuBar.add(fileMenu);
		mainMenuBar.add(drawMenu);
		mainMenuBar.add(toolMenu);

	}


	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}

