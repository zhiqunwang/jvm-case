package org.elvis.wang.jvm4;

import java.util.HashMap;

/**Stop-The-World  GC造成全局停顿
 * @author zhiqun.wang
 * @since JDK 1.7
 */
public class MyThread extends Thread {
    HashMap<Long,byte[]> map=new HashMap<Long,byte[]>();

    public void run(){
        try{
            while(true){
                if(map.size()*512/1024/1024>=450){
                    System.out.println("=====准备清理=====:"+map.size());
                    map.clear();
                }

                for(int i=0;i<1024;i++){
                    map.put(System.nanoTime(), new byte[512]);
                }
                Thread.sleep(1);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        PrintThread t2 = new PrintThread();
        t2.start();
        MyThread t1 = new MyThread();
        t1.start();
    }



}
