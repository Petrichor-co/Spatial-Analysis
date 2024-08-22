package DelaunayTIN;
import java.awt.*;

public class DrawCanvas extends Canvas {
	private static final long serialVersionUID = -7223465505617385995L;
	
	private static DrawCanvas mInstance = null;
	private float ScaleSize =1.0f;

	private int width;
	private int height;

	private boolean IS_POINTS_DRAWED = false;
	private boolean IS_TIN_DRAWED = false;
	private boolean IS_DRAWTIN_THREAD_OPEN= true;

	private Point[] DEMPoints = null;

	private double xMax = Double.MIN_VALUE;
	private double xMin = Double.MAX_VALUE;
	private double yMax = Double.MIN_VALUE;
	private double yMin = Double.MAX_VALUE;

	private double xCenter;
	private double yCenter;

	/**
	 * ����ģʽ
	 * @return ���ҽ��е�һ��ʵ��
	 */
	synchronized public static DrawCanvas newInstance(){
		if(mInstance == null){
			mInstance = new DrawCanvas();
		}
		return mInstance ;
	}

	/**
	 * ˽�й��췽��
	 */
	private DrawCanvas(){
		this.setBackground(new Color(0,0,0));
	}

	/**
	 * @param g
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		this.width = this.getWidth();
		this.height = this.getHeight();

		/**
		 * ���������ͷ
		 */
		DrawArrowHead.drawAL(50,height-100,150,height-100,g);
		DrawArrowHead.drawAL(100,height-50,100,height-150,g);

		/**
		 * ���������
		 */
		g.setColor(new Color(0,255,0));
		if(IS_POINTS_DRAWED){
			getWrap();
			for(int i =0 ; i< DEMPoints.length; i++) {
				g.drawOval((int)((DEMPoints[i].X-xCenter)/ScaleSize+width/2),
						(int)((DEMPoints[i].Y-yCenter)/ScaleSize+height/2), 1, 1);
			}
		}
		/**
		 * ���㲢����TIN
		 */
		if(IS_TIN_DRAWED){

			if(IS_DRAWTIN_THREAD_OPEN){
				IS_DRAWTIN_THREAD_OPEN=false;
				/**
				 * ����TINʱ������һ���µ��̣߳�����UI�̱߳�����
				 */
				new Thread(){
					@Override
					public void run() {
						DrawTIN mDrawTIN = DrawTIN.newInstance(DEMPoints,xCenter,yCenter,ScaleSize,width,height);
						mDrawTIN.getTIN();
						repaint();
					}
				}.start();
			}
            else
				DrawTIN.newInstance(DEMPoints,xCenter,yCenter,ScaleSize,width,height).draw(g,xCenter,yCenter,ScaleSize,width,height);

		}

		/**
		 * ���ƾ��ο�
		 */
		g.setColor(new Color(200,20,20));
		g.drawRect((int)(-100/ScaleSize+width/2),(int)(-100/ScaleSize+height/2),(int) (200/ScaleSize), (int)(200/ScaleSize));

	}

	/**
	 * չ�������
	 */
	public void drawDEMPoints(){
		IS_POINTS_DRAWED = true;
		repaint();
	}

	/**
	 *����������
	 */
    public void drawTIN(){
		IS_TIN_DRAWED = true;
		repaint();
	}

	/**
	 * �õ�DEM��ķ�Χ�����ĵ�
	 */
	private void getWrap(){
		for(int i = 0; i<DEMPoints.length; i++){
			if(xMin > DEMPoints[i].X){
				xMin = DEMPoints[i].X;
			}
			if(xMax<DEMPoints[i].X){
				xMax = DEMPoints[i].X;
			}
			if(yMin > DEMPoints[i].Y){
				yMin = DEMPoints[i].Y;
			}
			if(yMax<DEMPoints[i].Y){
				yMax = DEMPoints[i].Y;
			}
		}
		xCenter = (xMin+xMax)/2.0;
		yCenter = (yMin+yMax)/2.0;
	}

	public float getScaleSize() {
		return ScaleSize;
	}

	public void setScaleSize(float scaleSize) {
		ScaleSize = scaleSize;
	}

	public Point[] getDEMPoints() {
		return DEMPoints;
	}

	public void setDEMPoints(Point[] DEMPoints) {
		this.DEMPoints = DEMPoints;
	}
}

