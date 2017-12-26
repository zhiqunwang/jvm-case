package org.elvis.wang.jvm2;


/**
 * 栈溢出 递归方法
 * 局部变量、操作数栈、常量池指针
 *
 * 1.每一次调用方法创建一个帧并压栈
 * 2.线程私有
 *
 *
 * 栈上分配问题
 *
 * Java栈 – 栈上分配
   小对象（一般几十个bytes），在没有逃逸的情况下，可以直接分配在栈上
   直接分配在栈上，可以自动回收，减轻GC压力
   大对象或者逃逸对象无法栈上分配
   java.lang.StackOverflowError

   线程在调用每个方法的时候，都会创建相应的栈帧并压栈,在退出方法是移除栈帧。并且栈是私有的，也需要占用空间

   线程会对一个没有volatile的变量进行临时存储，这就导致线程栈的空间增大
 */
public class OverflowTest {
    private volatile int i=0;
    private volatile int b=0;
    private volatile int c=0;

/*	private  int i=0;
	private  int b=0;
	private  int c=0;*/

    public static void main(String[] args) {
        OverflowTest o=new OverflowTest();
        try {
            o.deepTest();
        } catch (Throwable e) {
            System.out.println("over flow deep:"+o.i);
            e.printStackTrace();
        }
    }
    private void deepTest() {
        ++i;
        ++b;
        ++c;
        deepTest();
    }
}
