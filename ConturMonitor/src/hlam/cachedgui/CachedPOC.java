package hlam.cachedgui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.*;

/**
 * Created by Vova on 06.05.14.
 */
public class CachedPOC {
    private JPanel panel1;
    private JTextArea textArea1;
    private JScrollPane scrollPane;
    private JScrollBar scrollBar1;
    private String fileName;

    //    private BufferedReader reader;
    private FileInputStream inputStreamReader;
    private RandomAccessFile randomAccessFile;

    private java.util.List<Long> linesStarts = new ArrayList<Long>();
    private java.util.List<Integer> linesLens = new ArrayList<Integer>();

    private java.util.List<Long> wrappedLinesStarts = new ArrayList<Long>();
    private java.util.List<Integer> wrappedLinesLens = new ArrayList<Integer>();

    int currentFirstLine = 0;
    private int lrcnt;


    public CachedPOC(String fileName) throws IOException {
        this.fileName = fileName;
//        init();
        JFrame frame = new JFrame("tst");
        frame.add(panel1);
        //frame.setSize(1500, 1300);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public CachedPOC init() throws IOException {
//        panel1.add(textArea1);
        randomAccessFile = new RandomAccessFile(fileName, "r");


        return this;
    }


    private void analyseFile() throws IOException {
        inputStreamReader = new FileInputStream(fileName);
//        reader = new BufferedReader(inputStreamReader);
        int charVal;
        int prevCharVal = 0;
        long readedChars = 0;
        long lastLineEnd = 0;
        while ((charVal = inputStreamReader.read()) >= 0) {
            readedChars++;
            if (prevCharVal == '\n' || charVal == '\r') {
                continue;
            }
            if (charVal == '\n' || charVal == '\r') {
                linesStarts.add(lastLineEnd);
                linesLens.add((int) (readedChars - lastLineEnd));
                lastLineEnd = readedChars;
            }
        }
//        System.out.println("linesStarts = " + linesStarts.size());
    }

    private void wrapLinesToWindow(int lineLen) {
        int curLineLen;
        for (int i = 0; i < linesStarts.size(); i++) {
            curLineLen = linesLens.get(i);
            wrappedLinesStarts.add(linesStarts.get(i));
            if (curLineLen > lineLen) {
                wrappedLinesLens.add(lineLen);
            } else {
                wrappedLinesLens.add(curLineLen);
            }

            for (int wrappedLine = 0; wrappedLine < curLineLen / lineLen; wrappedLine++) {
                wrappedLinesStarts.add(linesStarts.get(i) + (wrappedLine + 1) * lineLen);
                if (lineLen * (wrappedLine + 2) < curLineLen) {
                    wrappedLinesLens.add(lineLen);
                } else {
                    wrappedLinesLens.add(curLineLen - lineLen * (wrappedLine + 1));
                }
            }
        }
        scrollBar1.setMaximum(wrappedLinesStarts.size()-lrcnt);
//        System.out.println("wrappedLinesStarts = " + wrappedLinesStarts.size());
    }

    public void loadFile() throws IOException {
        analyseFile();
        textArea1.setFont(new Font("Courier new", 0, 12));
        int mcnt = textArea1.getWidth() / textArea1.getFontMetrics(textArea1.getFont()).charWidth('m');
        lrcnt = textArea1.getHeight() / textArea1.getFontMetrics(textArea1.getFont()).getHeight();
        wrapLinesToWindow(mcnt);

        readWindow();
//        for (int j = 0; j < lrcnt; j++) {
//            for (int i = 0; i < mcnt; i++) {
//                text.append("m");
//            }
//            text.append('\n');
//        }


        textArea1.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getModifiers() == 0) {
                    if ((e.getKeyCode() == KeyEvent.VK_PAGE_UP)) {
                        currentFirstLine -= lrcnt;
                        readWindow();
                    } else if ((e.getKeyCode() == KeyEvent.VK_PAGE_DOWN)) {
                        currentFirstLine += lrcnt;
                        readWindow();
                    }
                } else if (e.getModifiers() == KeyEvent.CTRL_MASK) {
                    if ((e.getKeyCode() == KeyEvent.VK_END)) {
                        currentFirstLine = wrappedLinesStarts.size() - lrcnt;
                        readWindow();
                    } else if ((e.getKeyCode() == KeyEvent.VK_HOME)) {
                        currentFirstLine = 0;
                        readWindow();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        scrollBar1.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
//                int extent = scrollBar1.getModel().getExtent();
                currentFirstLine = scrollBar1.getValue();
                readWindow();
            }
        });
    }

    private void checkAndFixLineNum() {
        if (currentFirstLine < 0) {
            currentFirstLine = 0;
        } else if (currentFirstLine > wrappedLinesStarts.size() - lrcnt) {
            currentFirstLine = wrappedLinesStarts.size() - lrcnt;
        }
    }

    StringBuffer text = new StringBuffer();
    StringBuffer line = new StringBuffer();
    private void readWindow() {
        checkAndFixLineNum();
        scrollBar1.setValue(currentFirstLine);
        try {
            text.setLength(0);
            for (int j = currentFirstLine; j < currentFirstLine + lrcnt; j++) {
//                System.out.println("wrappedLinesStarts[" + j + "] = " + wrappedLinesStarts.get(j));
                randomAccessFile.seek(wrappedLinesStarts.get(j));
                line.setLength(0);
//                System.out.println("wrappedLinesLens.get[" + j + "] = " + wrappedLinesLens.get(j));
                for (int k = 0; k < wrappedLinesLens.get(j); k++) {
                    line.append((char) randomAccessFile.readByte());
                }
                text.append(line.toString().replaceAll("[\n\r]", "")).append("\n");
            }
            textArea1.setText(text.substring(0, text.length() - 1));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        new CachedPOC("SystemOut.log").init().loadFile();
    }
}
