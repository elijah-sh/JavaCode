package thread;

/**
 * Created by ShenShuaihu on 2018/7/18.
 *  并行执行
 */
public class ThreadDome01 {
    public static void main(String[] args) {

      /*  MyThread t1 = new MyThread("t1");
        MyThread t2 = new MyThread("t2");
        // 线程是通过start启动的
        t1.start();
        t2.start();*/


      MyRunnanle r1 = new MyRunnanle("A");
      MyRunnanle r2 = new MyRunnanle("B");
            Thread t1 = new Thread(r1);
            Thread t2 = new Thread(r2);
            t1.start();
            t2.start();
    }
}
