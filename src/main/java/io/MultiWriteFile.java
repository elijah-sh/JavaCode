package io;

import java.io.File;

/**
 * Created by ShenShuaihu on 2018/7/18.
 */
public class MultiWriteFile {
    static File file = new File("test.txt");

    public static void main(String[] args) {
        if (file.exists()){
            file.delete();
        }
        new WriteFile(file,1).start();
        new WriteFile(file,2).start();
        new WriteFile(file,3).start();
        new WriteFile(file,4).start();
        new WriteFile(file,5).start();
        new WriteFile(file,6).start();
    }
}
