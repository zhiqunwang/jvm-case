package org.elvis.wang.jvm4;

import java.util.ArrayList;
import java.util.List;

/**
 * -Xmx500M -Xms500M -Xmn200M -XX:+UseConcMarkSweepGC
 * -XX:+UseCMSInitiatingOccupancyOnly  -XX:CMSInitiatingOccupancyFraction=70
 * @author zhiqun.wang
 * @since JDK 1.7
 */
public class ArrayListGCTest {

    public static void main(String[] args){
        allocateMemory();
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void allocateMemory(){

        int size = 1024 * 1024 *480;//480M
        int len = size / (20 * 1024);//
        List<byte[]> list = new ArrayList<byte[]>();
        for (int i = 0;i<len;i++){
            try{
                byte[] bytes = new byte[20*1024];
                list.add(bytes);
                System.out.print("Xmx=");
                System.out.println(Runtime.getRuntime().maxMemory()/1024.0/1024+"M");
                System.out.print("free mem=");
                System.out.println(Runtime.getRuntime().freeMemory()/1024.0/1024+"M");
                System.out.print("total mem=");
                System.out.println(Runtime.getRuntime().totalMemory()/1024.0/1024+"M");
            }catch(OutOfMemoryError e){
                 e.printStackTrace();
            }

        }
    }

}
