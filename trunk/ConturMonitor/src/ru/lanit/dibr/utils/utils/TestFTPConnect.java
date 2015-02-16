package ru.lanit.dibr.utils.utils;

import it.sauronsoftware.ftp4j.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by Vova on 16.02.2015.
 */
public class TestFTPConnect {
    public static void main(String[] args) throws FTPException, IOException, FTPIllegalReplyException, InterruptedException, FTPAbortedException, FTPDataTransferException, FTPListParseException {
        String path = "tst.log";
        File outFile = new File("fromFTP.log");
        FTPClient client = new FTPClient();
        client.setType(FTPClient.TYPE_BINARY);
        client.connect("127.0.0.1", 2221);
        client.login("user", "password");
        long size = client.fileSize(path);
        new ByteArrayOutputStream();
        for(int i = 0; i < 20 ; i++) {
            client.list();
            java.util.Date md = client.modifiedDate(path);
            System.out.println("Modification date :" + md + "; and size: " + size) ;
            if(client.fileSize(path)!=size) {
                client.download(path,outFile,size);
                size = client.fileSize(path);
            }
            Thread.sleep(1000);
        }
    }
}
