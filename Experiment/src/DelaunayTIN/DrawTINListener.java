package DelaunayTIN;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DrawTINListener implements ActionListener {

    /**
     * 主窗口
     */
    private DrawFrame mDrawFrame;

    /**
     * 构造方法
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
            JOptionPane.showMessageDialog(null,"你还未打开任何DEM坐标文件","警告",JOptionPane.ERROR_MESSAGE);
        }else{
            DrawCanvas.newInstance().drawTIN();
        }
    }
}
