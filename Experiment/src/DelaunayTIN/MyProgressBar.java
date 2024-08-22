package DelaunayTIN;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyProgressBar{

    private JProgressBar mJProgressBar;
    private JFrame mJFrame;
    private JLabel mJLabel;
    private long startTime=0;


    /**
     * ���췽��
     */
    public MyProgressBar(){
        mJFrame= new JFrame("����������");
        mJFrame.setBounds(400,400,400,80);

        mJProgressBar = new JProgressBar();
        mJProgressBar.setOrientation(JProgressBar.HORIZONTAL);
        mJProgressBar.setMinimum(0);
        mJProgressBar.setMaximum(100);
        mJProgressBar.setBackground(Color.WHITE);
        mJProgressBar.setForeground(new Color(20,255,20));
        mJProgressBar.setBorderPainted(true);

        mJLabel = new JLabel("Ŀǰ����ɽ��ȣ�0%",JLabel.CENTER);

        mJFrame.add(new JLabel("���������ɽ��ȣ�"),BorderLayout.NORTH);
        mJFrame.add(mJProgressBar,BorderLayout.CENTER);
        mJFrame.add(mJLabel,BorderLayout.SOUTH);

        mJFrame.setVisible(true);
    }

    /**
     * ˢ�½�����
     * @param value
     * @param currentTime
     */
    public void update(int value, long currentTime){

        float second = ((currentTime-startTime)/1000.0f);
        int minute = (int)(second/60);

        mJProgressBar.setValue(value);

        mJLabel.setText("Ŀǰ����ɽ��ȣ�"+Integer.toString(value)+"%"+"    �Ѻ�ʱ��"+
                Integer.toString(minute)+"����"+Integer.toString((int)second%60)+"��");
        mJLabel.setForeground(new Color(20,20,255));
    }

    public JProgressBar getJProgressBar() {
        return mJProgressBar;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

}
