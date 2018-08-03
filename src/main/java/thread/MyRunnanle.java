package thread;

/**
 * Created by ShenShuaihu on 2018/7/18.
 */
public class MyRunnanle  implements Runnable{

    private String name;
    public MyRunnanle(String name){
        this.name = name;
    }
    public void run() {
        for (int i = 0 ;i<1000;i++){
            System.out.println(name+" : "+ i);
        }
    }
}
