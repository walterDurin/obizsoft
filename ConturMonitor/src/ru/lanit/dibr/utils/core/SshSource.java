package ru.lanit.dibr.utils.core;

import com.jcraft.jsch.*;
import ru.lanit.dibr.utils.gui.configuration.Host;
import ru.lanit.dibr.utils.gui.configuration.LogFile;
import ru.lanit.dibr.utils.utils.SshUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Vova
 * Date: 13.11.12
 * Time: 2:12
 */
public class SshSource implements LogSource {

    private boolean isClosed = false;
    private boolean paused = false;
    private boolean writeLineNumbers = false;

    List<String> buffer;

    int readedLines;
    private Host host;
    private LogFile logFile;
    BufferedReader reader = null;
    ChannelExec channel = null;
    Session session = null;

    public SshSource(Host host, LogFile logFile) {
        this.host = host;
        this.logFile = logFile;
    }

    public void startRead() throws Exception {
        //checkClosed();
        readedLines = 0;
        paused = false;
        buffer = new ArrayList<String>();

        session = host.connect();
        isClosed = false;
        channel = (ChannelExec) session.openChannel("exec");
        String linesCount = SshUtil.exec(host, "wc -l " + logFile.getPath() + " | awk \"{print $1}\"").getData().trim();
        System.out.println("Lines count in log file: " + linesCount);
        channel.setCommand("tail -1000f " + logFile.getPath());
        //channel.setCommand("tail -c +0 -f " + logFile.getPath()); //Так можно загрузить весь файл
        reader = new BufferedReader(new InputStreamReader(channel.getInputStream(), host.getDefaultEncoding()));
        channel.connect(3 * 1000);

        final Thread readThread = new Thread(new Runnable() {
            public void run() {
                String nextLine;
                try {
                    while ((nextLine = reader.readLine()) != null && !isClosed) {
//                        buffer.add(String.format("%6d: %s", (buffer.size()+1), nextLine));
                        buffer.add(nextLine);
                    }
                } catch (IOException e) {
                    try {
                        close();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    e.printStackTrace();
                }
            }
        });

        new Thread(new Runnable() {
            public void run() {
                while(channel.isConnected()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Connection closed!");
                readThread.interrupt();
                try {
                    close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "Conn monitor").start();
        readThread.start();

    }

    public String readLine() throws IOException {
        try {
            while (paused && !isClosed) {
                System.out.println("I'm asleep..");
                Thread.sleep(200);
            }
            if(isClosed) {
                throw new IOException("Connection lost.");
            }
            if (buffer.size() > readedLines) {
                if(writeLineNumbers) {
                    return String.format("%6d: %s", readedLines + 1, buffer.get(readedLines++));
                } else {
                    return buffer.get(readedLines++);
                }
            } else {
                Thread.sleep(200);
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
        if (channel != null) {
            channel.sendSignal("KILL");
            channel.setCommand("cat " + logFile.getPath());
        }
    }

    public void close() throws Exception {
        isClosed = true;
        if (reader != null)
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        if (channel != null && channel.isConnected()) {
            channel.disconnect();
        }
        if (session != null && session.isConnected()) {
            session.disconnect();
        }
    }

    public void setPaused(boolean paused) {
        System.out.println("set paused: " + paused);
        this.paused = paused;
    }

    public boolean isWriteLineNumbers() {
        return writeLineNumbers;
    }

    public void setWriteLineNumbers(boolean writeLineNumbers) {
        this.writeLineNumbers = writeLineNumbers;
    }

    public boolean isPaused() {
        return paused;
    }

    @Override
    public String getName() {
        return host.getHost()+logFile.getPath()+logFile.getName();
    }
}
