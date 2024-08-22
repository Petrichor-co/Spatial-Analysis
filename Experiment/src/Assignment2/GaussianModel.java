package Assignment2;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GaussianModel {
	private double Ex;
    private double En;
    private double He;
    private double N;
    public static double[] x = new double[20480];
    public static double[] u = new double[20480];
    private double[] ctb = new double[20480];
    private double per067,per067_;
    private double per100,per100_;
    private double per200,per200_;
    private double per300,per300_;

    private Random random = new Random();
    public GaussianModel(double _Ex, double _En, double _He, double _N)
    {
        Ex = _Ex;
        En = _En;
        He = _He;
        N = _N;
        getCloudDrops();
    }
    final public void print_x_u()
    {
        System.out.println("\nData:");
        for (int i = 0 ; i < N ; ++i)
            System.out.println(+x[i]+","+u[i]);
        System.out.println();
        return ;
    }

    private void getCloudDrops()
    {
        /*
         * 产生服从N(a,b)随机数
         * Math.sqrt(b)*random.nextGaussian()+a; 
        */
        for(int i = 0 ; i < N ; ++i)
        {
            double En_ = He*random.nextGaussian()+En;
            x[i] = En_*random.nextGaussian()+Ex;
            u[i] = Math.exp(-1*Math.pow(x[i]-Ex, 2.0)/(2*Math.pow(En_, 2.0)));
        }
    }

    public void get_Ctb() 
    {
        // ------------------ 贡献值算法
        // 知识表示中的不确定性P4;
        for(int i = 0 ; i < N ; ++i)
            ctb[i] = u[i] * x[i] * Math.sqrt(2 * Math.PI) * En;

        double sum = 0 ;
        double sum_ = 0 ;
        int count = 0 ;
        for (int i = 0 ; i < N ; ++i)
            sum += ctb[i];
//      骨干元素贡献值计算
        for(int i = 0 ; i < N ; ++i)
        {
            if(x[i] >= Ex - 0.67*En && x[i] <= Ex + 0.67*En)
            {
                sum_ += ctb[i];
                count ++ ;
            }
        }
        per067 = sum_/sum ;
        per067_ = ((double)count)/((double)N);
//      基本元素
        sum_ = 0 ;
        count = 0 ;
        for(int i = 0 ; i < N ; ++i)
        {
            if(x[i] >= Ex - En && x[i] <= Ex + En)
            {
                sum_ += ctb[i];
                count ++ ;
            }
        }
        per100 = sum_/sum;
        per100_ = ((double)count)/((double)N);
//      外围元素;
        sum_ = 0 ;
        count = 0 ;
        for(int i = 0 ; i < N ; ++i)
        {
            if(x[i] >= Ex - 2*En && x[i] <= Ex + 2*En)
            {
                sum_ += ctb[i];
                count ++ ;
            }
        }
        per200 = sum_/sum;
        per200_ = ((double)count)/((double)N);
//      弱外围元素;
        per300 = (sum - sum_)/sum;
        per300_ = ((double)(N-count))/((double)N);
        return ;
    }

    final public void print_Ctb()
    {
        System.out.println("骨干元素：[Ex-0.67En,Ex+0.67En]");
        System.out.println("样本比例："+per067_*100+"%");
        System.out.println("贡献值：    "+per067*100+"%\n");

        System.out.println("基本元素：[Ex-En,Ex+En]");
        System.out.println("样本比例："+per100_*100+"%");
        System.out.println("贡献值：    "+per100*100+"%\n");

        System.out.println("外围元素：[Ex-2En,Ex-En]+[Ex+En,Ex+2En]");
        System.out.println("样本比例："+(per200_ - per100_)*100+"%");
        System.out.println("贡献值：    "+(per200 - per100)*100+"%\n");

        System.out.println("弱外围元素：[Ex-3En,Ex-2En]+[Ex+2En,Ex+3En]");
        System.out.println("样本比例："+per300_*100+"%");
        System.out.println("贡献值：    "+per300*100+"%\n");
    }

    public void write_Excel(String _url) throws FileNotFoundException
    {
        /*
         * 数据写入到Excel中
         * */       
        String url = "D:\\学习资料、作业\\空间分析\\data.xlsx";
        url += _url ;
        FileOutputStream out = new FileOutputStream(url);
        XSSFWorkbook wb = new XSSFWorkbook();

        //创建1页;
        XSSFSheet sheets = wb.createSheet("u(x)");
        for (int k = 0; k < N; k++) {
            XSSFRow rows = sheets.createRow(k);// 创建行数
            //写入单元格
            //int totalRows = sheets.getLastRowNum() + 1;
            rows.createCell(0).setCellValue(x[k]);
            rows.createCell(1).setCellValue(u[k]);
        }
        try {
            wb.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
           try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
           System.out.println("\n数据写入Excel成功!!!\n");
    }
    
    
    
    
}


