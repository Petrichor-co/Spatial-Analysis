package Assignment1;
import java.awt.BorderLayout;
import javax.swing.JFrame;
public class GraphicsFrame extends JFrame{

	private static final long serialVersionUID = 1L;
 
	public GraphicPanel gp;  //»­Í¼°å
	
	public ControlPanel cp; //¿ØÖÆ°å
 
	public GraphicsFrame (int height, int width)
	{
 
		setTitle("»ù±¾»­Í¼¿ò¼Ü");
 
		setSize(width, height);
		
		setLocationRelativeTo(null);
 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
		setLayout(new BorderLayout(5, 5));
 
		gp = new GraphicPanel(this);
		this.add(gp, BorderLayout.CENTER);//CENTER¡¢EAST¡¢WEST¡¢NORTH¡¢SOUTH
		
		cp = new ControlPanel(this);
		this.add(cp, BorderLayout.NORTH);
 
		setVisible(true);
		
	}
    
	public static void main (String[] args)
	{
		
		GraphicsFrame gf = new GraphicsFrame(512, 768);

	}
}
