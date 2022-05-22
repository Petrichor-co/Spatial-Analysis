package DelaunayTIN;
import javax.swing.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;


public class DrawFrame extends JFrame{

	private static final long serialVersionUID = 1L;

	/**
	 * �򿪵�DEM�����ļ���·��
	 */
	private String filePath;

	/**
	 * ��ͼ����
	 */
	private DrawCanvas drawArea;

	/**
	 * �����ڲ˵���
	 */
	private JMenuBar mainMenuBar;

	/**
	 * �����ڹ��췽��
	 */
	public DrawFrame(){
		init();
		this.setName("�����������㷨");
		this.setTitle("�����������㷨");
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setJMenuBar(mainMenuBar);
		this.add(drawArea);
		this.setVisible(true);
	}


	/**
	 *���ڳ�ʼ�����ڹ��췽���б�����
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
		 * �ļ��˵���
		 */
		JMenu fileMenu = new JMenu("�ļ�");
		JMenuItem importDateItem = new JMenuItem("����DEM���ݵ�");
		importDateItem.addActionListener(new OpenFileListener(this));
		fileMenu.add(importDateItem);

		/**
		 * ��ͼ�˵���
		 */
		JMenu drawMenu = new JMenu("��ͼ");
		JMenuItem drawTINItem = new JMenuItem("����������");
        drawTINItem.addActionListener(new DrawTINListener(this));
		drawMenu.add(drawTINItem);

		/**
		 * ���߲˵���
		 */
		JMenu toolMenu = new JMenu("����");
		JMenuItem largeTwoTimesItem = new JMenuItem("�Ŵ�����");
		largeTwoTimesItem.addActionListener(new LargeTwoTimesListener());
		JMenuItem scaleTwoTimesItem = new JMenuItem("��С����");
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

