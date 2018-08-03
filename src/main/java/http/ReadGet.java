package http;

import com.sun.deploy.net.FailedDownloadException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by ShenShuaihu on 2018/7/18.
 */
public class ReadGet extends  Thread {
    @Override
    public void run() {

        try {


            URL url = new URL("http://fanyi.youdao.com/openapi.do?keyfrom=xml&key=343166845&type=data&doctype=<doctype>&version=1.1&q=love\n");
            URLConnection connection = url.openConnection();
            InputStream inputStream = url.openStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line ;
            StringBuffer buffer = new StringBuffer();
            while ((line = bufferedReader.readLine()) != null){
                buffer.append(line);
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();

            System.out.println("-------");

            System.out.println(buffer.toString());

        } catch (MalformedURLException e) {
        } catch (IOException e) {
        } catch ( Exception e) {
            e.printStackTrace();
        }


        super.run();
    }

    public static void main(String[] args) {
        ReadGet rg = new ReadGet();
        rg.start();
    }
}
