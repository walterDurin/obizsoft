package test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Vova on 15.02.2015.
 */
public class LogGenerator {

    public static void main(String[] args) throws IOException, InterruptedException {
        File oldLog = new File("test.log");
        if(oldLog.exists()) {
            oldLog.delete();
        }
        FileWriter writer  = new FileWriter("test.log");
        long num = 0;
        while(true) {
            Thread.sleep(1500);
            String s = "\n[" + System.currentTimeMillis() + "] line \n№" + num++;
            System.out.println(s);
            writer.append(s);
            writer.flush();
        }

    }
}
