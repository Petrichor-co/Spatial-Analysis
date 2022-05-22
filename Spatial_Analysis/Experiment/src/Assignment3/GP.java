package Assignment3;

import java.awt.Color;
import javax.swing.JPanel;
public class GP extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	public GF gf;
	
	public GP (GF _gf )
	{
		super();
	
		gf = _gf;

		this.setBackground(Color.WHITE);
 
		
	}
	
}
