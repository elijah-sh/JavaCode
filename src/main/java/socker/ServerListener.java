package socker;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by ShenShuaihu on 2018/7/19.
 */
public class ServerListener extends  Thread {

    @Override
    public void run() {
        // port 1-65535
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            while (true){
                Socket socket = serverSocket.accept();
                // 建立连接
                JOptionPane.showMessageDialog(null,"建立聊天");
                // 将 socket 传递给新的线程
               // new ChartSocket(socket).start(); //匿名方式 不可用
                ChartSocket cs = new ChartSocket(socket);
                cs.start();
                CharManager.getChatManager().add(cs);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new ServerListener().start();
    }
}
