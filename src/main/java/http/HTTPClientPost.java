package http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by ShenShuaihu on 2018/7/18.
 */
public class HTTPClientPost extends Thread{
    HttpClient client = HttpClients.createDefault();

    @Override
    public void run() {
        HttpGet   get = new HttpGet("http://www.iciba.com/love");

        try {
            HttpResponse response =  client.execute(get);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity,"UTF-8");

            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new HTTPClientPost().start();
    }
}
