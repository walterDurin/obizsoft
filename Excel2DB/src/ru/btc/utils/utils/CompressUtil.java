package ru.btc.utils.utils;

import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.rarfile.FileHeader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * User: Vladimir
 * Date: 05.02.14
 * Time: 13:02
 */
public class CompressUtil {

    private static Log log = LogFactory.getLog(CompressUtil.class);

    public static InputStream getFirstItemInputStream(String fileName) throws IOException, RarException {
        InputStream inputStream;File file = new File(fileName);
        if(!file.exists()) {
            log.info("Can't open file \"" + file.getAbsolutePath() + "\"");
        } else if (!file.canRead()) {
            log.info("Can't read file \"" + file.getAbsolutePath() + "\"");
        }

        if(fileName.toUpperCase().endsWith(".ZIP")) {
            log.info("Found ZIP archive. Try to open first entry.");
            ZipFile zipFile = new ZipFile(file);
            ZipEntry entry = zipFile.entries().nextElement();
            log.info("Found entry: \"" + entry + "\".");
            inputStream = zipFile.getInputStream(entry);
        } else if(fileName.toUpperCase().endsWith(".RAR")) {
            log.info("Found RAR archive. Try to open first entry.");
            Archive archive = new Archive(file);
            inputStream = getRAREntryInputStream(archive, archive.getFileHeaders().get(0), 8192, false, 500);
        } else {
            inputStream = new FileInputStream(file);
        }
        return inputStream;
    }

    private static InputStream getRAREntryInputStream(final Archive archive, final FileHeader hd, int pipeSize, final boolean checkCrc, final long waitTimeout) throws RarException, IOException {

        final PipedInputStream in = new PipedInputStream(pipeSize);
        final PipedOutputStream out = new PipedOutputStream(in);

        Thread extractorThread = new Thread(new Runnable() {
            public void run()  {
                try {

                    if (waitTimeout > 0) {
                        try {
                            log.info("Waiting for " + waitTimeout + " ms before loading archive entry " + hd.getFileNameString());
                            Thread.sleep(waitTimeout);
                        }
                        catch (InterruptedException ex) {
                            log.warn("Waiting for " + waitTimeout + " ms before loading archive entry " + hd.getFileNameString());
                        }
                    }

                    archive.extractFile(hd, out, checkCrc);

                }
                catch (RarException e) {
                    log.warn("Error extracting RAR archive entry " + hd.getFileNameString(), e);
                }
                finally {
                    try {
                        out.close();
                    }
                    catch (IOException e) {
                        log.warn("Error closing RAR archive entry stream " + hd.getFileNameString(), e);
                    }
                }
            }
        });

        extractorThread.start();
        return in;

    }




}
