package ru.lanit.dibr.utils.gui;

import javax.swing.*;
import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

import ru.lanit.dibr.utils.CmdLineConfiguration;
import ru.lanit.dibr.utils.core.*;
import ru.lanit.dibr.utils.gui.forms.Filters;
import ru.lanit.dibr.utils.utils.Utils;

import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * User: VTaran
 * Date: 16.08.2010
 * Time: 15:56:23
 */
public class LogPanel extends JScrollPane implements KeyListener, CaretListener, MouseListener {
    private LogSource logSource;
    private Source filtersChain;
    private String blockPattern;
    private boolean stopped = false;
    private JTextArea area;
    private boolean autoScroll = true;
    private AtomicBoolean needRepaint = new AtomicBoolean(true);
    private String find = null;


    private Filter grepInvertedFilter = new GrepFilter(true);
    private Filter grepDirectFilter = new GrepFilter(false);
    private Filter blockInvertedFilter;
    private Filter blockDirectFilter;

    private int startFrom = 0;
    private int offset = 0;
    boolean lastSearchDirectionIsForward = true;

    public Filters filtersWindow;

    public LogPanel(LogSource logSource, String blockPattern) {
        super(new JTextArea());
        this.logSource = logSource;
        this.blockPattern = blockPattern;

        blockInvertedFilter = new BlockFilter(blockPattern, true);
        blockDirectFilter = new BlockFilter(blockPattern, false);

        filtersWindow = new Filters("Filters", this, blockDirectFilter, blockInvertedFilter, grepDirectFilter, grepInvertedFilter);

        area = ((JTextArea) getViewport().getView());

        area.setEditable(false);
        area.setFont(new Font("Courier New", 0, CmdLineConfiguration.fontSize));
//        area.setBackground(new Color(0, 0, 0));
        area.setBackground(new Color(36, 17, 11));
//        area.setForeground(new Color(187, 187, 187));
        area.setForeground(new Color(214, 203, 176));
//        area.setSelectedTextColor(new Color(0, 0, 0));
        area.setSelectedTextColor(new Color(74, 247, 51));
        area.setSelectionColor(new Color(77, 95, 114));
//        area.setSelectionColor(new Color(187, 187, 187));
        area.addMouseListener(this);

        area.setWrapStyleWord(true);
        area.setLineWrap(true);
    }

    public void connect() throws Exception {
        try {
            logSource.startRead();
            filtersChain = logSource;
            String nextLine;
            area.addKeyListener(this);
            area.addCaretListener(this);

            new Thread(new Runnable() {
                public void run() {
                    while (!stopped) {
                        try {
                            int cnt = 0;
                            if (autoScroll) {
                                getVerticalScrollBar().setValue(getVerticalScrollBar().getMaximum());
                            }
                            if (needRepaint.getAndSet(false) || (cnt++) == 8) {
                                cnt = 0;
                                getParent().repaint(0);
                                repaint(0);
                            }
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                }
            }, "Repaint and Scroll").start();

            this.addMouseWheelListener(new MouseAdapter() {
                @Override
                public void mouseWheelMoved(MouseWheelEvent e) {
                    //System.out.println(e);
                    setAutoScroll(!getVerticalScrollBar().isVisible() || (getVerticalScrollBar().getValue() + getVerticalScrollBar().getHeight()) == getVerticalScrollBar().getMaximum());
                }
            });


            while ((nextLine = filtersChain.readLine()) != null && !stopped) {
                if (nextLine == LogSource.SKIP_LINE) { //сравнение именно по ссылке, а не по значению!
                    continue;
                }
                appendLine(nextLine);
            }
        } finally {
            logSource.close();
        }
    }

    private void appendLine(String nextLine) {
        area.append("\n" + nextLine);
        if (autoScroll) {
            getVerticalScrollBar().setValue(getVerticalScrollBar().getMaximum());
        }
        needRepaint.set(true);
    }

    public void stop() {
        stopped = true;
    }

    public void setAutoScroll(boolean autoScroll) {
        this.autoScroll = autoScroll;
    }

    public void keyPressed(KeyEvent ke) {

        if (ke.getModifiers() == 0) {
            if ((ke.getKeyCode() == 27)) { //Нажали PgUp
                //TODO fix: tight coupling with parent Frame (LogFrame)
                Container container = getParent();
                while (!JFrame.class.isAssignableFrom(container.getClass())) {
                    container = container.getParent();
                }
                WindowEvent closingEvent = new WindowEvent((Window) container, WindowEvent.WINDOW_CLOSING);
                Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closingEvent);

            } else if ((ke.getKeyCode() == KeyEvent.VK_PAGE_UP)) { //Нажали PgUp
                setAutoScroll(false);
            } else if ((ke.getKeyCode() == KeyEvent.VK_W)) { //Нажали W - перенос строк
                area.setLineWrap(!area.getLineWrap());
            } else if ((ke.getKeyCode() == KeyEvent.VK_N) && (logSource instanceof SshSource)) { //Нажали N - номера строк
                logSource.setPaused(true);
                area.setText("");
                autoScroll = true;
                ((SshSource) logSource).setWriteLineNumbers(!((SshSource) logSource).isWriteLineNumbers());
                logSource.reset();
                logSource.setPaused(false);
            } else if (ke.getKeyCode() == KeyEvent.VK_F1) { // F1
                new HotKeysInfo();
            } else if (ke.getKeyCode() == KeyEvent.VK_C) { // Key 'C'
                logSource.setPaused(true);
                area.setText("");
                logSource.setPaused(false);
            }
        }
        if(ke.getModifiers() == 0 || ke.getModifiers() == KeyEvent.SHIFT_MASK) {
            if (ke.getKeyCode() == KeyEvent.VK_F3) { // F3 (+Shift)
                findWord(ke.getModifiers() == KeyEvent.SHIFT_MASK);
            } else if (ke.getKeyCode() == KeyEvent.VK_F5) { // [Shift +] F5
                if (ke.getModifiers() == KeyEvent.SHIFT_MASK) {
                    clearFilters();
                } else {
                    resetFilters();
                }
            }
        } else if ((ke.getModifiers() == KeyEvent.CTRL_MASK ) || ( ke.getModifiers() == (KeyEvent.SHIFT_MASK | KeyEvent.CTRL_MASK))) {
            boolean isOnlyShiftAdditionalPressed = (ke.getModifiers()&KeyEvent.SHIFT_MASK) != 0;
            if(!isOnlyShiftAdditionalPressed) {
                if ((ke.getKeyCode() == KeyEvent.VK_HOME)) { //Нажали Cntrl + Home
                    setAutoScroll(false);
                    area.setCaretPosition(0);
                } else if ((ke.getKeyCode() == KeyEvent.VK_END)) { //Нажали Cntrl + End
                    setAutoScroll(true);
                } else if (ke.getKeyCode() == KeyEvent.VK_S ) { // Key Ctrl + 'S'
                    logSource.setPaused(true);
                    findSimilar();
                    logSource.setPaused(false);

                }
            }
            if (ke.getKeyCode() == KeyEvent.VK_F) {  //  Нажали Ctrl [+Shift] + F
                performFind(isOnlyShiftAdditionalPressed);
            } else if (ke.getKeyCode() == KeyEvent.VK_G) {  // GREP filter
                addGrepFilter(isOnlyShiftAdditionalPressed);
            } else if ((ke.getKeyCode() == KeyEvent.VK_B) && blockPattern != null ) {  //BLOCK filter
                //ToDo: если blockPattern==null - Сообщить об этом и предложить его ввести. Показав что нибудь по дефолту. А потом сделать визард с проверкой по строке из лога.
                addBlockFilter(isOnlyShiftAdditionalPressed);
            }
        } else {
            System.out.println(ke.getKeyCode());
        }
    }

    public void addBlockFilter(boolean inverseBlock) {
        addFilter(inverseBlock ? blockInvertedFilter : blockDirectFilter, "Block filter");
    }

    public void addGrepFilter(boolean inverseGrep) {
        addFilter(inverseGrep ? grepInvertedFilter : grepDirectFilter, "Grep filter");
    }

    private void addFilter(Filter filter, String title) {
        String pattern = (String) JOptionPane.showInputDialog(this, title + ":\n", title, JOptionPane.INFORMATION_MESSAGE, null, null, null);
        System.out.println(title + " entered: '" + pattern + "'");

        if (pattern.isEmpty()) {
            filter.disable();
        } else {
            filter.addStringToSearch(pattern);
        }
        filtersWindow.addTo(filter);
        resetFilters();
    }

    public void performFind(boolean isBackWard) {
        find = (String) JOptionPane.showInputDialog(this, "FIND:\n", "Find", JOptionPane.INFORMATION_MESSAGE, null, null, null);
        startFrom = area.getCaretPosition();
        if (highlightFound() > 0)
            findWord(isBackWard);
    }

    public void clearFilters() {
        blockInvertedFilter.disable();
        blockDirectFilter.disable();
        grepInvertedFilter.disable();
        grepDirectFilter.disable();
        resetFilters();
    }

    public void resetFilters() {
        logSource.setPaused(true);
        filtersChain = logSource;
        area.setText("");
        if (blockInvertedFilter.isActive()) {
            filtersChain = blockInvertedFilter.apply(filtersChain);
        }
        if (blockDirectFilter.isActive()) {
            filtersChain = blockDirectFilter.apply(filtersChain);
        }
        if (grepInvertedFilter.isActive()) {
            filtersChain = grepInvertedFilter.apply(filtersChain);
        }
        if (grepDirectFilter.isActive()) {
            filtersChain = grepDirectFilter.apply(filtersChain);
        }
        logSource.reset();
        logSource.setPaused(false);

        autoScroll = true;
    }

    public boolean findWord(boolean isBackWard) {
        setAutoScroll(false);
        if (lastSearchDirectionIsForward == isBackWard) {
            lastSearchDirectionIsForward = !isBackWard;
            if (!findWord(isBackWard))
                return false;
        } else {
            lastSearchDirectionIsForward = !isBackWard;
        }
        for (int i = 0; i < 2; i++) {
            if (isBackWard) {
                //offset = area.getText().lastIndexOf(find, startFrom);
                offset = Utils.lastIndexOf(area.getText(), false, startFrom, find);
            } else {
//                offset = area.getText().indexOf(find, startFrom);
                offset = Utils.indexOf(area.getText(), false, startFrom, find);
            }
            if (offset > -1) {
                area.setFocusCycleRoot(true);
                area.requestFocusInWindow();
//                area.select(offset, find.length() + offset);
                area.setCaretPosition(offset+find.length());
                area.moveCaretPosition(offset);
                startFrom = offset + (isBackWard ? -1 : (find.length() + 1));
                return true;
            }
            startFrom = isBackWard ? (area.getText().length() - 1) : 0;
        }
        JOptionPane.showMessageDialog(this, "No matches found!");
        return false;
    }

    public int highlightFound() {
        autoScroll = false;
        Highlighter h = area.getHighlighter();
        h.removeAllHighlights();
        int pos = 0;
        int cnt = 0;
        while (pos >= 0) {
            //pos = area.getText().indexOf(find, pos);
            pos = Utils.indexOf(area.getText(), false, pos, find);
            System.out.println("pos: " + pos);
            if (pos > -1) {
                try {
                    cnt++;
                    h.addHighlight(pos, find.length() + pos, DefaultHighlighter.DefaultPainter);
                    int y = area.getHeight() / (area.getText().length() / pos);
                    area.getGraphics().drawLine(0, y, 25, y);
                    pos += find.length();
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }
        }
        JOptionPane.showMessageDialog(this, cnt + " matches found");
        return cnt;
    }


    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void caretUpdate(CaretEvent e) {
        startFrom = e.getDot();
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            try {
                int ss = area.getSelectionStart();
                ss = area.getText().lastIndexOf("\n", ss) + 1;
                int se = area.getText().indexOf("\n", ss);
                System.out.println("DblClk! \n" + area.getText(ss, se - ss));
            } catch (BadLocationException e1) {
                e1.printStackTrace();
            }

        }
        if (e.getClickCount() == 2 && blockPattern != null) {
            try {

                String block = findBlockAroundCursor();

                if (block != null) {
                    int firstLineOfBlockEndPos = block.indexOf('\n');
                    if (firstLineOfBlockEndPos < 0) {
                        firstLineOfBlockEndPos = block.length();
                    }
                    String title = block.substring(0, firstLineOfBlockEndPos);
                    new PopupBlock(title, block);
                }

            } catch (PatternSyntaxException ex) {
                JOptionPane.showMessageDialog(this, "Block pattern is wrong!");
            } catch (Exception e1) {
                System.out.println("Can't create block popup window!");
                e1.printStackTrace();
            } finally {
                logSource.setPaused(false);
            }

        }
    }

    private String findBlockAroundCursor() {
        setAutoScroll(false);
        logSource.setPaused(true);

        Pattern compiledBlockPattern = getBlockPatern();
        boolean isBeginFound = false;
        boolean isEndFound = false;
        int firstStartPos, firstEndPos;
        int secondStartPos, secondEndPos;
        int cursorPos = area.getCaretPosition() - 1;
        if (area.getText().charAt(cursorPos) == '\n') {
            cursorPos--;
        }
        firstStartPos = secondStartPos = cursorPos;
        String first, second;
        String block = null;
        for (int i = 0; i < 500; i++) {
            if (!isBeginFound) {
                firstStartPos = area.getText().lastIndexOf("\n", firstStartPos) + 1;
                firstEndPos = area.getText().indexOf("\n", firstStartPos);
                System.out.println("firstStartPos = " + firstStartPos);
                System.out.println("firstEndPos = " + firstEndPos);
                if (firstStartPos < 0) {
                    System.out.println("Begin detected. Use begining of log.");
                    firstStartPos = 0;
                    isBeginFound = true;
                } else {
                    if (firstEndPos < 0) {
                        System.out.println("End detected. Use end of log.");
                        firstEndPos = secondStartPos = area.getText().length();
                        isEndFound = true;
                    }
                    first = area.getText().substring(firstStartPos, firstEndPos);
                    System.out.println("first = " + first);
                    if (compiledBlockPattern.matcher(first).matches()) {
                        System.out.println("start line found: \"" + first + "\"");
                        isBeginFound = true;
                    } else {
                        firstStartPos = firstStartPos - 2;
                    }
                }
            }

            if (!isEndFound) {
                secondStartPos = area.getText().indexOf("\n", secondStartPos) + 1;
                secondEndPos = area.getText().indexOf("\n", secondStartPos);
                System.out.println("secondStartPos = " + secondStartPos);
                System.out.println("secondEndPos = " + secondEndPos);
                if (secondEndPos < 0 || secondStartPos < 0) {
                    System.out.println("End detected. Use end of log.");
                    isEndFound = true;
                    secondStartPos = area.getText().length();
                } else if (secondStartPos == area.getText().length()) {
                    isEndFound = true;
                } else if (secondEndPos != secondStartPos) {
                    second = area.getText().substring(secondStartPos, secondEndPos);
                    System.out.println("second = " + second);
                    if (compiledBlockPattern.matcher(second).matches()) {
                        System.out.println("second line found: \"" + second + "\"");
                        isEndFound = true;
                    } else {
                        secondStartPos = secondEndPos;
                    }
                }
            }

            if (isBeginFound && isEndFound) {
                block = area.getText().substring(firstStartPos, secondStartPos);
                System.out.println("found block: " + block);
                break;
            }

        }
        logSource.setPaused(false);
        return block;
    }

    private Pattern getBlockPatern() {
        String patternPrefix = "";
        if (logSource instanceof SshSource && ((SshSource) logSource).isWriteLineNumbers()) {
            patternPrefix = "\\s*\\d{1,6}: ";
        }
        return Pattern.compile(patternPrefix + this.blockPattern + ".*");
    }


    public void findSimilar() {
        System.out.println("LeviDistance\tBlock Size1\tBlock size2");
        Pattern compiledBlockPattern = getBlockPatern();
        String blockAroundCursor = findBlockAroundCursor();
        String log = area.getText();
        int b0size = blockAroundCursor.length();
        int lineStart = 0;
        int curBlockStartPos = 0;
        int lineEnd = 0;
        StringBuffer blockBuffer = new StringBuffer();
        StringBuffer result = new StringBuffer();
        while ((lineEnd = log.indexOf('\n', lineStart)) >= 0) {
            String line = log.substring(lineStart, lineEnd);
            if (compiledBlockPattern.matcher(line).matches()) {
                int b1size = blockBuffer.length();
                double m1 = (b0size + b1size + 100) / (Math.abs(b0size - b1size + 0.0));
                if (m1 > 10) { //Разница размеров блоков не менее чем в 10 раз меньше суммы
                    int distance = Utils.getLevenshteinDistance(blockAroundCursor, blockBuffer.toString(), 1000);
                    double m2 = Math.abs((b0size - 100) / (distance + 0.0));
                    if (m2 > 5) {
                        result.append("--------------------------------------------------------------------------------------------------------------------------------\n");
                        //result.append(distance + "\t" + blockAroundCursor.length() + "\t" + blockBuffer.length() + "\t" + m1 + "\t" + m2);
                        result.append(blockBuffer).append("\n");
                    }
                }
                blockBuffer.setLength(0);
                curBlockStartPos = lineEnd;
            }
            blockBuffer.append(line).append("\n");
            lineStart = lineEnd + 1;
        }

        System.out.println(result);
        try {
            new PopupBlock("Similar blocks", result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
        if (area.getSelectedText() != null) {
            String selected = area.getSelectedText();
            if (logSource instanceof SshSource && ((SshSource) logSource).isWriteLineNumbers()) {
                selected = removeLineNumbers(selected);
            }
            setAutoScroll(false);
            StringSelection ss = new StringSelection(selected);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
        }
    }

    private String removeLineNumbers(String selected) {
        selected = selected.replaceAll("^[\\s\\d]*:\\s", "");
        selected = selected.replaceAll("\n[\\s\\d]*:\\s", "\n");
        return selected;
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }


    public String getLogSourceName() {
        return logSource.getName();
    }
}
