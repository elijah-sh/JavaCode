package socker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by ShenShuaihu on 2018/7/19.
 */
public class ChartSocket extends Thread {
    Socket socket;
    public ChartSocket(Socket s){
        this.socket = s;
    }
    public void  out (String out){
        try {
            socket.getOutputStream().write(out.getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream(),"UTF-8"
                    ));
            String line =null;
            while ((line = br.readLine()) != null){
                CharManager.getChatManager().publish(this,line);
            }
            br.close();;
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
        
}
