package Assignment1;
import java.awt.Color;
import javax.swing.JPanel;
public class GraphicPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	public GraphicsFrame gf;
	
	public GraphicPanel (GraphicsFrame _gf)
	{
		super();
		
		gf = _gf;
 
		this.setBackground(Color.white);
 
		
	}
}