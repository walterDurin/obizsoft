package hlam.cachedgui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Vova on 06.05.14.
 */
public class CachedPOC {
    private JPanel panel1;
    private JTextArea textArea1;
    private JScrollPane scrollPane;
    private String fileName;
    private ArrayList<Integer> lnpos = new ArrayList<Integer>();

    public CachedPOC(String fileName) {
        this.fileName = fileName;
        JFrame frame = new JFrame("tst");
        frame.add(panel1);
        frame.setSize(500, 300);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public CachedPOC init() {
//        panel1.add(textArea1);
        return this;
    }


    private void analyseFile() throws IOException {
        FileReader file = new FileReader(fileName);
        char buff[] = new char[8192];
        int prevReaded = 0;
        int readed = 0;
        while((readed = (file.read(buff)))>=0) {
            for(int i = 0; i < readed; i++) {
                if(buff[i]=='\n') {

                }
            }
        }
    }

    public void loadFile() throws FileNotFoundException {
        analyseFile();
        textArea1.setFont(Font.getFont(Font.MONOSPACED));
        int mcnt = textArea1.getWidth()/textArea1.getFontMetrics(textArea1.getFont()).charWidth('m');
        int lrcnt = textArea1.getHeight()/textArea1.getFontMetrics(textArea1.getFont()).getHeight();
        StringBuffer text = new StringBuffer();
        for (int j = 0; j < lrcnt; j++) {
            for (int i = 0; i < mcnt; i++) {
                text.append("m");
            }
            text.append('\n');
        }
        textArea1.setText(text.deleteCharAt(text.length() - 1).toString());
    }

    public static void main(String[] args) throws FileNotFoundException {
        new CachedPOC("SystemOut.log").init().loadFile();
    }
}
