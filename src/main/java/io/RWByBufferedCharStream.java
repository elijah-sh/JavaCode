package io;
import java.io.*;

/**
 * Created by ShenShuaihu on 2018/7/17.
 */
public class RWByBufferedCharStream {

   public static void main(String[] args){
       System.out.print("begin");

       try{
           FileInputStream fis = new FileInputStream("java.txt");
           FileOutputStream fos = new FileOutputStream("java_new.txt");
           InputStreamReader isr = new InputStreamReader(fis,"UTF-8");
           OutputStreamWriter osw = new OutputStreamWriter(fos,"UTF-8");

           BufferedReader br = new BufferedReader(isr);
           BufferedWriter bw = new BufferedWriter(osw);

           String input;
           while ((input = br.readLine())!=null){
              bw.write(input);
           }
           br.close();
           bw.close();
           isr.close();
           osw.close();
           fos.close();
           System.out.print("end:" );

       }catch (  FileNotFoundException e){
       }catch (  IOException e){
            e.getStackTrace();
           System.out.print("error:"+e.getStackTrace());

       }
   }

}

