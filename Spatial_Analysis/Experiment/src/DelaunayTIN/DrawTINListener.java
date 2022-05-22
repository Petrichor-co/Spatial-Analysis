package DelaunayTIN;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DrawTINListener implements ActionListener {

    /**
     * ������
     */
    private DrawFrame mDrawFrame;

    /**
     * ���췽��
     * @param mDrawFrame
     */
    public DrawTINListener(DrawFrame mDrawFrame){
        this.mDrawFrame = mDrawFrame;
    }

    /**
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(this.mDrawFrame.getFilePath() == null){
            JOptionPane.showMessageDialog(null,"�㻹δ���κ�DEM�����ļ�","����",JOptionPane.ERROR_MESSAGE);
        }else{
            DrawCanvas.newInstance().drawTIN();
        }
    }
}
