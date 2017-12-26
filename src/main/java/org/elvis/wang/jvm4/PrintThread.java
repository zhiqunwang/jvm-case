package org.elvis.wang.jvm4;

/**
 * @author zhiqun.wang
 * @since JDK 1.7   每秒打印十条
 */
public  class PrintThread extends Thread {

    public static final long  startTime = System.currentTimeMillis();

    public void run(){
        try{
            while(true){
                long t = System.currentTimeMillis()-startTime;
                System.out.println("time:"+t);
                Thread.sleep(100);
            }
        }catch(Exception e){
           e.printStackTrace();
        }

    }

}
