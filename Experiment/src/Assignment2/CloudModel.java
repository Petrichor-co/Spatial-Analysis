package Assignment2;

import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.*;

public class CloudModel implements Runnable{
    private static double Ex;
    private static double En;
    private static double He;
    private static double N;
    //private static Scanner cin = new Scanner(System.in);
    

    static class WriteExcel extends Thread{//线程
        GaussianModel GM ;
        public void run()
        {
            try {
                GM.write_Excel("D:\\学习资料、作业\\空间分析\\data.xlsx");
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        public void start(GaussianModel _GM)
        {
            super.start();
            GM = _GM;
        }
    }

    
    public static void main(String args[]) {
    	new CloudModel();    	
    }

	@Override
	public void run() {
		// TODO Auto-generated method stub
			System.out.println("请输入参数：(提示：20 5 0.3 10240)");
			String  o=JOptionPane.showInputDialog("请输入参数：","20 5 0.3 10240");
	    	String [] k =o.split(" ");
	        double []l=new double[k.length];
	        for (int i = 0; i <k.length ; i++) {
	            l[i]=Double.parseDouble(k[i]);
	        }
	        Ex = l[0];
	        En = l[1];
	        He = l[2];
	        N = l[3];
	        GaussianModel GM = new GaussianModel(Ex,En,He,N);
	        WriteExcel WE = new WriteExcel();
	        WE.start(GM);
	        GM.print_x_u();
	        GM.get_Ctb();
	        GM.print_Ctb();
	        new Paint();
		
    	
		
	}
}