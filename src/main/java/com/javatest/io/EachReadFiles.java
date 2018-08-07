package com.javatest.io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ShenShuaihu on 2018/8/6.
 */
public class EachReadFiles {

    private List<String> filelist = new ArrayList<String>();

    public void readFileList(String strPath) {

        File file = new File(strPath);

        file.listFiles();
        String name = file.getAbsolutePath();
      //  System.out.println(name);

        File[] files = file.listFiles();
        if (files == null) {
            return;
        }
        for (int i = 0; i < files.length; i++) {
            // 递归遍历
            if (files[i].isDirectory()) {
                // 是目录

                String fileName =  files[i].getAbsolutePath();
                System.out.println("目录："+fileName.substring(35));
                readFileList(files[i].getAbsolutePath());

             // filelist.add("目录："+files[i].getAbsolutePath().substring(name.length()));
            } else {
                // 文件
                String fileName = files[i].getAbsolutePath();
              //  filelist.add("文件："+fileName.substring(name.length()));
                System.out.println("文件："+fileName.substring(35));
            }
        }
    }
    public static void main(String[] args) {
      EachReadFiles erf = new EachReadFiles();
      erf.readFileList("src");
      //  System.out.println(erf.filelist.toString());

    }
}
