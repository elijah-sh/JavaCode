package thread;

/**
 * Created by ShenShuaihu on 2018/7/18.
 */
public class MyThread extends Thread{

    private  String name;
    public MyThread(String name){       // 构造方法
        this.name = name;
    }
    @Override
    public void run() {
        for (int i=0;i<1000;i++){
            System.out.println(name+" : "+i);
        }
        super.run();
    }
}
