package org.elvis.wang.jvm4;

import java.util.ArrayList;
import java.util.List;

/**
 * 场景：2017/12/18日早 周一 账务服务线上应用报警.其他业务系统都反应调用超时,线上拉取日志分析 各种连接间歇性的中断,包括DBConnection、memcache连接、zookeeper连接中断
 *       立马dump出内存,重启线上几台账务服务，系统恢复正常。
 *
 * 分析：一开始以为是数据库连接问题，分析数据库连接和IO都不高,当查看zabbix JVM内存情况，老年区一度达到最高长时间降不下来，初步判断是因为GC Stop-the-World造成
 *       查看CAT系统个接口调用情况，查询账户余额接口凌晨3点开始大批量频繁调用，基本确定是此接口造成
 *
 *       分析这接口逻辑代码,分析dump 初步判断是因为ArrayList 动态扩容引起的
 *
 *
 * -Xmx500M -Xms500M -Xmn200M -XX:+UseConcMarkSweepGC
 * -XX:+UseCMSInitiatingOccupancyOnly  -XX:CMSInitiatingOccupancyFraction=70
 * -XX:+CMSScavengeBeforeRemark
 *
 * jstat -gcutil <pid>  统计gc信息
 *
 * jstat -gcnew <pid>:年轻代对象的信息。
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
