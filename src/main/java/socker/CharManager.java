package socker;

import java.util.Vector;

/**
 * Created by ShenShuaihu on 2018/7/19.
 */
/* 单例模式有以下特点：
            　　1、单例类只能有一个实例。
            　　2、单例类必须自己创建自己的唯一实例。
            　　3、单例类必须给所有其他对象提供这一实例。*/
public class CharManager {
    // 类的单例模式
        private  CharManager(){}
        public static final CharManager cm = new CharManager();
        public  static CharManager getChatManager(){
            return cm;
        }
        Vector<ChartSocket> vector = new Vector<ChartSocket>();

        public void  add(ChartSocket cs ){
            vector.add(cs);
        }
        public void  publish (ChartSocket cs,String out){
            for (int i = 0; i<vector.size(); i++){
                ChartSocket chartSocket = vector.get(i);
                if (!cs.equals(chartSocket)){
                    chartSocket.out(out);
                }
            }
        }

}
