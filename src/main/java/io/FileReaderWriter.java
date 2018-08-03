package io;

import java.io.*;

/**
 * Created by ShenShuaihu on 2018/7/17.
 */
public class FileReaderWriter {
    public static void main(String[] args) {

        try {
            FileReader fr = new FileReader("java.txt");
            BufferedReader br = new BufferedReader(fr);

            FileWriter fw = new FileWriter("java_n.txt");
            BufferedWriter bw = new BufferedWriter(fw);

            String line;
            while ((line = br.readLine()) != null){
                bw.write(line+"\n");
            }
            bw.flush();
            bw.close();
            fw.close();
            br.close();
            fr.close();
            System.out.println("ee");
        }catch (FileNotFoundException e){
            e.getStackTrace();
        }catch (IOException e){
            e.getStackTrace();
        }
        System.out.println("sssee");
    }
}
