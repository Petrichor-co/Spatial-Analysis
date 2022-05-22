package Test;

public class EnumT {

	public enum Month {

		January(1,31),Febuary(2,28),March(3,31),April(4,30),May(5,31),June(6,30),July(7,31),August(8,31),September(9,30),October(10,31),November(11,30),December(12,31);
		private int code;
	    private int num;
	    private Month(int code,int num) {
	        this.code = code;
	        this.num = num;
	    }
	    
	    public int getCode() {
	        return code;
	    }
	    public int getnum() {
	        return num;
	    }
	    public void setCode(int code) {
	        this.code = code;
	    }
	    public void setName(int num) {
	        this.num = num;
	    } 
	}
	public static void main(String[] args) {
		Month[] month=Month.values();	
		
				for (int i = 0; i < month.length; i++) {
					switch(month[i]) {
					case January:
						System.out.print(month[i]+"：");
						System.out.println(month[i].num+"天");
						break;
					case Febuary:
						System.out.print(month[i]+"：");
						System.out.println(month[i].num+"天");
						break;
					case March:
						System.out.print(month[i]+"：");
						System.out.println(month[i].num+"天");
						break;
					case April:
						System.out.print(month[i]+"：");
						System.out.println(month[i].num+"天");
						break;
					case May:
						System.out.print(month[i]+"：");
						System.out.println(month[i].num+"天");
						break;
					case June:
						System.out.print(month[i]+"：");
						System.out.println(month[i].num+"天");
						break;
					case July:
						System.out.print(month[i]+"：");
						System.out.println(month[i].num+"天");
						break;
					case August:
						System.out.print(month[i]+"：");
						System.out.println(month[i].num+"天");
						break;
					case September:
						System.out.print(month[i]+"：");
						System.out.println(month[i].num+"天");
						break;
					case October:
						System.out.print(month[i]+"：");
						System.out.println(month[i].num+"天");
						break;
					case November:
						System.out.print(month[i]+"：");
						System.out.println(month[i].num+"天");
						break;
					case December:
						System.out.print(month[i]+"：");
						System.out.println(month[i].num+"天");
						break;
						
					}
				}
	}
}
