package org.elvis.wang.jvm2;

import java.util.HashMap;

/**
 * zhiqun.wang  2017/12/8
 * 测试Perm区溢出引起的OOM
 * OOM 存放什么数据： 类型的常量池, 字段、方法信息 ,方法字节码
 * -XX:MaxPermSize=20M  起始大小
 * JDK7及以上的版本 类型的常量池 已移到堆内存中存放
 *
 */
public class PermOOM {

    public static void main(String[] args) throws ClassNotFoundException {
        // 设置类成员属性
        HashMap propertyMap = new HashMap();
        for(int i = 0;i<=100000;i++){
            propertyMap.put("id"+i, Class.forName("java.lang.Integer"));
            CglibBean bean = new CglibBean(propertyMap);

        }

    }
}
