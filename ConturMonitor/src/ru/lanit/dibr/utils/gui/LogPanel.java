package ru.lanit.dibr.utils.gui;

import javax.swing.*;
import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

import ru.lanit.dibr.utils.core.*;
import ru.lanit.dibr.utils.gui.forms.Filters;

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

        blockInvertedFilter = new BlockFilter(blockPattern, true);
        blockDirectFilter = new BlockFilter(blockPattern, false);

        filtersWindow = new Filters("Filters", this, blockDirectFilter, blockInvertedFilter, grepDirectFilter, grepInvertedFilter);

        area = ((JTextArea) getViewport().getView());

        area.setEditable(false);
        area.setFont(new Font("Courier New", 0, 12));
//        area.setBackground(new Color(0, 0, 0));
        area.setBackground(new Color(36, 17, 11));
//        area.setForeground(new Color(187, 187, 187));
        area.setForeground(new Color(214, 203, 176));
//        area.setSelectedTextColor(new Color(0, 0, 0));
        area.setSelectedTextColor(new Color(53, 56, 204));
        area.setSelectionColor(new Color(247, 247, 134));
//        area.setSelectionColor(new Color(187, 187, 187));
        area.addMouseListener(this);

        area.setWrapStyleWord(true);
        area.setLineWrap(true);

        this.logSource = logSource;
        this.blockPattern = blockPattern;
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
                            if(autoScroll) {
                                getVerticalScrollBar().setValue(getVerticalScrollBar().getMaximum());
                            }
                            if(needRepaint.getAndSet(false) || 8==cnt++) {
                                cnt=0;
                                getParent().repaint();
                                repaint();
                            }
                            Thread.sleep(100);
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

        if ((ke.getKeyCode() == 27)) { //Нажали PgUp
            //TODO fix: tight coupling with parent Frame (LogFrame)
            Container container = getParent();
            while (!JFrame.class.isAssignableFrom(container.getClass())) {
                container = container.getParent();
            }
            WindowEvent closingEvent = new WindowEvent((Window) container, WindowEvent.WINDOW_CLOSING);
            Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(closingEvent);

        } else if ((ke.getKeyCode() == 33)) { //Нажали PgUp
            setAutoScroll(false);
        } else if ((ke.getKeyCode() == 87)) { //Нажали W
            area.setLineWrap(!area.getLineWrap());
        } else if ((ke.getKeyCode() == 35) && (ke.getModifiers() == KeyEvent.CTRL_MASK)) { //Нажали Cntrl + PgDown
            setAutoScroll(true);
        } else if ((ke.getKeyCode() == KeyEvent.VK_F) && ((ke.getModifiers() == KeyEvent.CTRL_MASK) || (ke.getModifiers() == (KeyEvent.CTRL_MASK | KeyEvent.SHIFT_MASK)))) {  //  Нажали Ctrl + F
            performFind();
        } else if (ke.getKeyCode() == KeyEvent.VK_F3) { // F3 (+Shift)
            findWord(ke.getModifiers() == KeyEvent.SHIFT_MASK);
        } else if (ke.getKeyCode() == KeyEvent.VK_F1) { // F1
            new HotKeysInfo();
        } else if (ke.getKeyCode() == KeyEvent.VK_F5) { // [Shift +] F5
            if(ke.getModifiers()==KeyEvent.SHIFT_MASK) {
                clearFilters();
            } else {
                resetFilters();
            }

        } else if (ke.getKeyCode() == 67) { // Key 'C'
            logSource.setPaused(true);
            area.setText("");
            logSource.setPaused(false);

        } else if ((ke.getKeyCode() == 71) && (ke.getModifiers() == KeyEvent.CTRL_MASK || ke.getModifiers() == (KeyEvent.CTRL_MASK | KeyEvent.SHIFT_MASK))) {  // GREP filter
            boolean inverseGrep = ke.getModifiers() == (KeyEvent.CTRL_MASK | KeyEvent.SHIFT_MASK);
            addGrepFilter(inverseGrep);

        } else if ((ke.getKeyCode() == 66) && blockPattern != null && (ke.getModifiers() == KeyEvent.CTRL_MASK || ke.getModifiers() == (KeyEvent.CTRL_MASK | KeyEvent.SHIFT_MASK))) {  //BLOCK filter
            //ToDo: если blockPattern==null - Сообщить об этом и предложить его ввести. Показав что нибудь по дефолту. А потом сделать визард с проверкой по строке из лога.
            boolean inverseBlock = ke.getModifiers() == (KeyEvent.CTRL_MASK | KeyEvent.SHIFT_MASK);
            addBlockFilter(inverseBlock);

        } else {
            System.out.println(ke.getKeyCode());
        }
    }

    public void addBlockFilter(boolean inverseBlock) {
        addFilter(inverseBlock?blockInvertedFilter:blockDirectFilter, "Block filter");
    }

    public void addGrepFilter(boolean inverseGrep) {
        addFilter(inverseGrep ? grepInvertedFilter : grepDirectFilter, "Grep filter");
    }

    private void addFilter(Filter filter, String title) {
        String pattern = (String) JOptionPane.showInputDialog(this, title + ":\n", title, JOptionPane.INFORMATION_MESSAGE, null, null, null);
        System.out.println(title + " entered: '" + pattern + "'");

        if(pattern.isEmpty()) {
                filter.invalidate();
        } else {
            filter.addStringToSearch(pattern);
        }
        resetFilters();
    }

    public void performFind() {
        find = (String) JOptionPane.showInputDialog(this, "FIND:\n", "Find", JOptionPane.INFORMATION_MESSAGE, null, null, null);
        startFrom = area.getCaretPosition();
        if (highlightFound()>0)
            findWord(false);
    }

    public void clearFilters() {
        blockInvertedFilter.invalidate();
        blockDirectFilter.invalidate();
        grepInvertedFilter.invalidate();
        grepDirectFilter.invalidate();
        resetFilters();
    }

    public void resetFilters() {
        logSource.setPaused(true);
        filtersChain = logSource;
        area.setText("");
        if(blockInvertedFilter.isValid()) {
            filtersChain = blockInvertedFilter.apply(filtersChain);
        }
        if(blockDirectFilter.isValid()) {
            filtersChain = blockDirectFilter.apply(filtersChain);
        }
        if(grepInvertedFilter.isValid()) {
            filtersChain = grepInvertedFilter.apply(filtersChain);
        }
        if(grepDirectFilter.isValid()) {
            filtersChain = grepDirectFilter.apply(filtersChain);
        }
        logSource.reset();
        logSource.setPaused(false);

        autoScroll = true;
    }

    public boolean findWord(boolean isBackWard) {
        setAutoScroll(false);
        if(lastSearchDirectionIsForward==isBackWard) {
            lastSearchDirectionIsForward = !isBackWard;
            if(!findWord(isBackWard))
                return false;
        } else {
            lastSearchDirectionIsForward = !isBackWard;
        }
        for (int i = 0; i < 2; i++) {
            if (isBackWard) {
                offset = area.getText().lastIndexOf(find, startFrom);
            } else {
                offset = area.getText().indexOf(find, startFrom);
            }
            if (offset > -1) {
                area.setFocusCycleRoot(true);
                area.requestFocusInWindow();
                area.select(offset, find.length() + offset);
                area.setCaretPosition(offset);
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
        while(pos >=0) {
            pos = area.getText().indexOf(find, pos);
            System.out.println("pos: " + pos);
            if (pos > -1) {
                try {
                    cnt++;
                    h.addHighlight(pos ,find.length() + pos, DefaultHighlighter.DefaultPainter);
                    int y = area.getHeight()/(area.getText().length()/pos);
                    area.getGraphics().drawLine(0,y,25,y);
                    pos+=find.length();
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
        if(e.getClickCount()==2) {
            try {
                int  ss = area.getSelectionStart();
                ss = area.getText().lastIndexOf("\n", ss) + 1;
                int se  = area.getText().indexOf("\n", ss);
                System.out.println("DblClk! \n" + area.getText(ss, se-ss) );
            } catch (BadLocationException e1) {
                e1.printStackTrace();
            }

        }
        if (e.getClickCount() == 2 && blockPattern != null) {
            try {
                Pattern compiledBlockPattern = Pattern.compile("\\s*\\d{1,6}: .*" + this.blockPattern + ".*");
                boolean isBeginFound = false;
                boolean isEndFound = false;
                int firstStartPos, firstEndPos;
                int secondStartPos, secondEndPos;
                firstStartPos = firstEndPos = secondStartPos = secondEndPos = area.getCaretPosition();
                String first, second;
                for (int i = 0; i < 250; i++) {
                    if (!isBeginFound) {
                        firstStartPos = area.getText().lastIndexOf("\n", firstEndPos - 1) + 1;
                        if (firstStartPos < 0) {
                            break;
                        }
                        first = area.getText().substring(firstStartPos, firstEndPos);
                        if (compiledBlockPattern.matcher(first).matches()) {
                            System.out.println("start line found: \"" + first + "\"");
                            isBeginFound = true;
                        } else {
                            firstEndPos = firstStartPos - 1;
                        }
                    }

                    if (!isEndFound) {
                        secondEndPos = area.getText().indexOf("\n", secondStartPos + 1);
                        if (secondEndPos < 0) {
                            break;
                        }
                        second = area.getText().substring(secondStartPos, secondEndPos);
                        if (compiledBlockPattern.matcher(second).matches()) {
                            System.out.println("second line found: \"" + second + "\"");
                            isEndFound = true;
                        } else {
                            secondStartPos = secondEndPos + 1;
                        }
                    }

                    if(isBeginFound && isEndFound) {
                        String block = area.getText().substring(firstStartPos, secondStartPos);
                        System.out.println("found block: " + block);
                        new PopupBlock("123", block);
                        break;
                    }

                }
            } catch (PatternSyntaxException ex) {
                JOptionPane.showMessageDialog(this, "Block pattern is wrong!");
            }
        }
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
        if (area.getSelectedText() != null) {
            String selected = area.getSelectedText();
            selected = removeLineNumbers(selected);
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
}
