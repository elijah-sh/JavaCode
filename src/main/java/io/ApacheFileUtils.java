package io;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by ShenShuaihu on 2018/7/18.
 */
public class ApacheFileUtils {
    public static void main(String[] args) {

        try {
            File file  = new File("java.txt");
            File file2  = new File("javacopy.txt");
//            String input = FileUtils.readFileToString(file,"UTF-8");
//            System.out.println(" input："+input );
            FileUtils.copyFile(file,file2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
