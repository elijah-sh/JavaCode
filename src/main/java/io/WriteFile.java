package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by ShenShuaihu on 2018/7/18.
 */
public class WriteFile extends Thread {
    File file;
    int block ;
    int L = 100;

    public WriteFile(File f , int b){
        this.file = f;
        this.block = b;
    }

    @Override
    public void run() {

        try {
            RandomAccessFile raf = new RandomAccessFile(file,"rw");
            raf.seek((block-1)*L);          // 读取位置
            raf.writeBytes("This is block ---"+block);
            raf.writeBytes("\n");
        } catch (FileNotFoundException e) {
        } catch (IOException e){
            e.printStackTrace();
        }


    }
}
