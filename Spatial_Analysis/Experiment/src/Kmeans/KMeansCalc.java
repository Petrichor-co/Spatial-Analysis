package Kmeans;

/*
 * KmeansCalc
 * ʵ��K-��ֵ�����㷨
 * Ĭ�Ͼ�������Ϊ3����
 * ��ʼ�����ݶ���100��
 * ��������Ϊ���ĵ㲻�ٱ仯
 */

import java.util.Random;
import java.util.Vector;

import javax.swing.JOptionPane;

import Kmeans.KMeansPaint;

public class KMeansCalc {
    static int k=3;  //�������ĸ���
    int i=0;
    
    int lable = 0;  //�ر�ǩ
    static int flag = 0;
    static StringBuilder data = new StringBuilder();
    static Vector<Tuple> tuples = new Vector<Tuple>();  //Ԫ�����꼯
    static Tuple means[]= new Tuple[k];  //���ĵ㼯��
    static Vector<Tuple> clusters[]=new Vector[k];  //�ؼ���
    Tuple meansLast[]= new Tuple[k];
    Tuple meanFrist[] = new Tuple[k];

    public KMeansCalc(){
        for(int i=0;i<100;i++){
            tuples.addElement(new Tuple(0,0));
        }
        for(i=0;i<k;i++){
            means[i] = new Tuple(0,0);
            meanFrist[i] = new Tuple(0,0);
            clusters[i] = new Vector<Tuple>();
            clusters[i].addElement(means[i]);
        }

    }

    //REset Data,bt1Action
    public void ReSet(){
        data.delete(0, data.length());
        tuples.clear();
        for(i=0;i<k;i++){
            means[i] = new Tuple(0,0);
            clusters[i] = new Vector<Tuple>();
            clusters[i].clear();
            clusters[i].addElement(means[i]);
        }
        flag=0;
    }

    //����ŷ�Ͼ���
    public double getDistXY(Tuple t1,Tuple t2){

        return Math.sqrt(Math.pow((t1.x-t2.x)*(t1.x-t2.x), 2)+Math.pow((t1.y-t2.y)*(t1.y-t2.y),2));
    }

    //�������ģ�������ǰԪ�������ĸ���
    public int clusterOfTuple(Tuple means[],Tuple tuple){
        double dist=getDistXY(means[0],tuple);
        double tmp;
        int label=0;
        for(int i=1;i<k;i++){
            tmp=getDistXY(means[i],tuple);
            if(tmp<dist) {dist=tmp;label=i;}
        }
        return label;
    }

    //��õ�ǰ�ص�����
    public Tuple getMeans(Vector<Tuple> cluster){
        float meansX=0,meansY=0;
        Tuple t = new Tuple();
        for (int i=0;i<cluster.size();i++)
        {
            meansX+=cluster.get(i).getX();
            meansY+=cluster.get(i).getY();
        }
        t.setX(meansX/cluster.size());
        t.setY(meansY/cluster.size());
        return t;
    }

    //bt4 Action
    public static String getData(){
        return data.toString();
    }

    //init(),bt1Action
    public void KMeansInit(){   	
        Random ran = new Random();
        tuples.addElement(new Tuple(ran.nextInt(19),ran.nextInt(17)));
        data.append("�������ݼ�\n������Ϊ�鿴����ʹ�ã��ڴ˴��޸�ֵ������Ӱ����򣩣�\n("+String.valueOf(tuples.get(0).getX())+","+
                String.valueOf(tuples.get(0).getY())+")\n");
        for(int i=1;i<KMeansPaint.n;i++){  //���Ը��ĵ�ĸ���
            boolean flag = false;//�Ѿ����ɵ�Ԫ���Ƿ���������ɵ������ظ�
            Tuple tem = new Tuple(ran.nextInt(19),ran.nextInt(17));
            for(int j = 0; j<i; j++) {
                if(tuples.get(j).getX() ==tem.getX()&&tuples.get(j).getY() == tem.getY()) {
                    flag = true;//�����ظ���
                }
            }
            if(!flag){//���ظ���
                tuples.addElement(tem);
                data.append("("+String.valueOf(tem.getX())+","+
                        String.valueOf(tem.getY())+")\n");
            }
            else{//�����ظ���i��һ��
                i--;
            }
        }
        meanFrist[0] = tuples.get(ran.nextInt(100));
        for(i=1;i<k;i++){
            meanFrist[i] = tuples.get(ran.nextInt(100));
            for(int j=0;j<i;j++){
                if(meanFrist[i]==meanFrist[i-1]){
                    i--;
                }
            }
        }
        for(i=0;i<k;i++){
            meansLast[i]= new Tuple();
            means[i].setX(meanFrist[i].getX());
            meansLast[i].x=means[i].x;
            means[i].y=meanFrist[i].getY();
            meansLast[i].y=means[i].y;

        }
        //����Ĭ�ϵ����ĸ��ظ�ֵ
        for(i=0;i!=tuples.size();++i){

            lable=clusterOfTuple(means,tuples.get(i));
            clusters[lable].addElement(tuples.get(i));
        }
        for(lable=0;lable<k;lable++){
            System.out.print("��"+(lable+1)+"���أ�\n");
            Vector<Tuple> t=clusters[lable];

            for (i=0;i<t.size();i++)
            {
                System.out.print("("+t.get(i).getX()+","+t.get(i).getY()+")"+"  ");
            }
            System.out.print("\n");
        }
    }

    //Later handle,bt2&bt3 Action
    public void KMeansHandle(){

        for (i=0;i<k;i++)                //����ֵͬʱ����һ�αȽ�
        {
            means[i]=getMeans(clusters[i]);
            if(means[i].x==meansLast[i].x&&means[i].y==meansLast[i].y)flag++;
            else { meansLast[i].x=means[i].x;  meansLast[i].y=means[i].y;}
        }
        if(flag==k)return;
        flag=0;
        for (i=0;i<k;i++) //���ÿ����
        {
            clusters[i].clear();
        }
        //�����µ����Ļ���µĴ�
        for(i=0;i!=tuples.size();++i){
            lable=clusterOfTuple(means,tuples.get(i));
            clusters[lable].add(tuples.get(i));
        }
        for(lable=0;lable<k;lable++){
            System.out.print("��"+(lable+1)+"���أ�\n");
            System.out.print("���ĵ�:("+means[lable].x+","+means[lable].y+")  \n");
            System.out.print("Ԫ���Ա��\n");
            Vector<Tuple> t=clusters[lable];
            for (i=0;i<t.size();i++)
            {
                System.out.print("("+t.get(i).getX()+","+t.get(i).getY()+")"+"  ");
            }
            System.out.print("\n");
        }
        System.out.print("\n");
        try{
            Thread.sleep(1000);
        }catch(Exception e){
            System.out.print("Thread.sleep Exception in Kmeans.java\n");
            System.exit(0);
        }
    }

}
