package KochCurve;

import javax.swing.JOptionPane;

public class Koch implements Runnable{
	double distance;
	
    public void draw(int n) {
	Turtle t = new Turtle();
	t.move(40,300);//起始点位置
	t.penDown();
	drawKoch(t, distance ,n);
       
    }
 
    public void drawKoch(Turtle t, double len, int n){
	if (n < 1)  t.go(len);
	else{
	    // for (int a = 60; a <= 420; a += 180){	   
	    for (int i = 0; i < 3; i++){
		int a = 60 + 180*i;
	
		drawKoch(t, len / 3, n - 1);
		t.rotate(a);
	    }
 
	    drawKoch(t, len / 3, n - 1);
	}
    }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String o1 =JOptionPane.showInputDialog("请输入迭代次数：");
		String o2 =JOptionPane.showInputDialog("请输入长度：");
    	int diedai = Integer.parseInt(o1);
    	distance=Double.parseDouble(o2);
	    for(int n = 1; n<=diedai; n++)
	    draw(n);
	}
}

