package DelaunayTIN;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LargeTwoTimesListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		    DrawCanvas drawArea = DrawCanvas.newInstance();
		    drawArea.setScaleSize(drawArea.getScaleSize()/2);
			drawArea.repaint();
	}
}

