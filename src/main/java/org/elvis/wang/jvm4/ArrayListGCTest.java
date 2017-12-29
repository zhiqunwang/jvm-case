package org.elvis.wang.jvm4;

import java.util.ArrayList;
import java.util.List;

/**
 * 场景：ArrayList动态扩容导致old区不断CMS回收，但是老年代就是降不下来（应用网络，DB全部间断）
 * 分析：
 * -Xmx500M -Xms500M -Xmn200M -XX:+UseConcMarkSweepGC
 * -XX:+UseCMSInitiatingOccupancyOnly  -XX:CMSInitiatingOccupancyFraction=70
 * -XX:+CMSScavengeBeforeRemark
 *
 * jstat -gcutil <pid>  统计gc信息   jstat -gcnew <pid>:年轻代对象的信息。
 * jstat –class<pid> : 显示加载class的数量，及所占空间等信息。
 * jstat -compiler <pid>显示VM实时编译的数量等信息。
 *
 * jmap -histo:live [pid]
 * @author zhiqun.wang
 * @since JDK 1.7
 */
public class ArrayListGCTest {

    public static void main(String[] args){
        allocateMemory();
        try {
            Thread.sleep(1000000);
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
