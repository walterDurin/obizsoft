package ru.lanit.dibr.utils.core;

import com.jcraft.jsch.*;
import ru.lanit.dibr.utils.gui.configuration.Host;
import ru.lanit.dibr.utils.gui.configuration.LogFile;
import ru.lanit.dibr.utils.utils.MyUserInfo;

import javax.swing.text.TableView;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Vova
 * Date: 13.11.12
 * Time: 2:12
 */
public class TestSource implements LogSource {

    private boolean isClosed = false;
    private boolean paused = false;
    List<String> buffer = new ArrayList<String>();
    private long SLEEP = 100;
    private boolean writeLineNumbers = false;

    private File fileToRead;
    int readedLines = 0;
    //    StringBuffer buffer = new StringBuffer();
    BufferedReader reader = null;

    public TestSource(String filename, long sleep) {
        this(filename);
        SLEEP = 0;
    }
    public TestSource(String filename) {
        fileToRead = new File(filename);
        if(!fileToRead.exists() || !fileToRead.isFile() || !fileToRead.canRead()) {
            throw new RuntimeException("�� ���� ������� ����!");
        }
    }

    public void startRead() throws Exception {
        checkClosed();

        reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileToRead)));

        Thread readThread = new Thread(new Runnable() {
            public void run() {
                String nextLine;
                try {
                    while ((nextLine = reader.readLine()) != null && !isClosed) {
                        if(buffer.size() > 400 && SLEEP > 0) {
                            Thread.sleep(SLEEP);
                        }
                        if(writeLineNumbers) {
                            buffer.add(String.format("%6d: %s", (buffer.size()+1), nextLine));
                        } else {
                            buffer.add(nextLine);
                        }
                    }
                } catch (IOException e) {
                    try {
                        close();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        readThread.start();

    }

    private void checkClosed() {
        if (isClosed) {
            throw new RuntimeException("Reader is closed");
        }
    }

    public String readLine() throws IOException {
        try {
            while (paused) {
                System.out.println("I'm asleep..");
                Thread.sleep(SLEEP);
            }
            if (buffer.size() > readedLines) {
                return buffer.get(readedLines++);
            } else {
                Thread.sleep(SLEEP);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return SKIP_LINE;
    }

    public void reset() {
        readedLines = 0;
        //reader.reset();
    }

    public void reloadFull() throws Exception {
        //todo
    }

    public void close() throws Exception {
        isClosed = true;
        if (reader != null)
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    @Override
    public String getName() {
        return "Test Source";
    }

    @Override
    public boolean isWriteLineNumbers() {
        return writeLineNumbers;
    }

    @Override
    public void setWriteLineNumbers(boolean writeLineNumbers) {
        this.writeLineNumbers = writeLineNumbers;
    }

    public void setPaused(boolean paused) {
        System.out.println("set paused: " + paused);
        this.paused = paused;
    }

    public boolean isPaused() {
        return paused;
    }
}
