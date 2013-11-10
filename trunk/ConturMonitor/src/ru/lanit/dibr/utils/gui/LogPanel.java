package ru.lanit.dibr.utils.gui;

import javax.swing.*;
import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;
import javax.swing.text.BadLocationException;

import com.sun.deploy.net.proxy.StaticProxyManager;
import ru.lanit.dibr.utils.core.*;

import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by IntelliJ IDEA.
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

    private AbstractFilter grepInvertedFilter = null;
    private AbstractFilter grepDirectFilter = null;
    private AbstractFilter blockInvertedFilter = null;
    private AbstractFilter blockDirectFilter = null;

    private int startFrom = 0;
    private int offset = 0;
    boolean lastSearchDirectionIsForward = true;

    public LogPanel(LogSource logSource, String blockPattern) {
        super(new JTextArea());
        area = ((JTextArea) getViewport().getView());

        area.setEditable(false);
        area.setFont(new Font("Courier New", 0, 12));
        area.setBackground(new Color(0, 0, 0));
        area.setForeground(new Color(187, 187, 187));
        area.setSelectedTextColor(new Color(0, 0, 0));
        area.setSelectionColor(new Color(187, 187, 187));
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
                            if(autoScroll) {
                                getVerticalScrollBar().setValue(getVerticalScrollBar().getMaximum());
                            }
                            if(needRepaint.getAndSet(false)) {
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
        } else if (ke.getKeyCode() == KeyEvent.VK_F5) { // F5
            resetFilters();
        } else if (ke.getKeyCode() == 67) { // Key 'C'
            logSource.setPaused(true);
            area.setText("");
            logSource.setPaused(false);
        } else if ((ke.getKeyCode() == 71) && (ke.getModifiers() == KeyEvent.CTRL_MASK || ke.getModifiers() == (KeyEvent.CTRL_MASK | KeyEvent.SHIFT_MASK))) {  // GREP filter
            boolean inverseGrep = ke.getModifiers() == (KeyEvent.CTRL_MASK | KeyEvent.SHIFT_MASK);
            String grepPattern = (String) JOptionPane.showInputDialog(this, "GREP:\n", "Grep", JOptionPane.INFORMATION_MESSAGE, null, null, null);
            System.out.println("Grep entered: '" + grepPattern + "'");

            if(grepPattern.isEmpty()) {
                if(inverseGrep) {
                    grepInvertedFilter = null;
                } else {
                    grepDirectFilter = null;
                }
            } else  if(inverseGrep) {
                if(grepInvertedFilter ==null || !(grepInvertedFilter instanceof GrepFilter)) {
                    grepInvertedFilter = new GrepFilter(grepPattern, true);
                } else {
                    ((GrepFilter) grepInvertedFilter).addStringToSearch(grepPattern);
                }
                if(grepDirectFilter !=null && !(grepDirectFilter instanceof GrepFilter)) {
                    grepDirectFilter = null;
                }
            } else {
                if(grepDirectFilter ==null || !(grepDirectFilter instanceof GrepFilter)) {
                    grepDirectFilter = new GrepFilter(grepPattern, false);
                } else {
                    ((GrepFilter) grepDirectFilter).addStringToSearch(grepPattern);
                }

            }

            resetFilters();

        } else if ((ke.getKeyCode() == 66) && blockPattern != null && (ke.getModifiers() == KeyEvent.CTRL_MASK || ke.getModifiers() == (KeyEvent.CTRL_MASK | KeyEvent.SHIFT_MASK))) {  //BLOCK filter
            //ToDo: если blockPattern==null - Сообщить об этом и предложить его ввести. Показав что нибудь по дефолту. А потом сделать визард с проверкой по строке из лога.
            boolean inverseBlock = ke.getModifiers() == (KeyEvent.CTRL_MASK | KeyEvent.SHIFT_MASK);
            String blockSearchPattern = (String) JOptionPane.showInputDialog(this, "Block filter:\n", "Block filter", JOptionPane.INFORMATION_MESSAGE, null, null, null);
            System.out.println("blockFilter entered: '" + blockSearchPattern + "'");

            if(blockSearchPattern.isEmpty()) {
                if(inverseBlock) {
                    blockInvertedFilter = null;
                } else {
                    blockDirectFilter = null;
                }
            } else if(inverseBlock) {
                if(blockInvertedFilter ==null || !(blockInvertedFilter instanceof BlockFilter)) {
                    blockInvertedFilter = new BlockFilter(blockPattern, blockSearchPattern, true);
                } else {
                    ((BlockFilter) blockInvertedFilter).addStringToSearch(blockSearchPattern);
                }
            } else {
                if(grepDirectFilter ==null || !(grepDirectFilter instanceof BlockFilter)) {
                    grepDirectFilter = new BlockFilter(blockPattern, blockSearchPattern, false);
                } else {
                    ((BlockFilter) grepDirectFilter).addStringToSearch(blockSearchPattern);
                }
            }

            resetFilters();

        } else {
            System.out.println(ke.getKeyCode());
        }
    }

    public void performFind() {
        find = (String) JOptionPane.showInputDialog(this, "FIND:\n", "Find", JOptionPane.INFORMATION_MESSAGE, null, null, null);
        startFrom = area.getCaretPosition();
        findWord(false);
    }

    private void resetFilters() {
        logSource.setPaused(true);
        filtersChain = logSource;
        area.setText("");
        if(blockInvertedFilter !=null) {
            filtersChain = blockInvertedFilter.apply(filtersChain);
        }
        if(blockDirectFilter !=null) {
            filtersChain = blockDirectFilter.apply(filtersChain);
        }
        if(grepInvertedFilter !=null) {
            filtersChain = grepInvertedFilter.apply(filtersChain);
        }
        if(grepDirectFilter !=null) {
            filtersChain = grepDirectFilter.apply(filtersChain);
        }
        logSource.reset();
        logSource.setPaused(false);

        autoScroll = true;
    }

    private void findWord(boolean isBackWard) {
        if(lastSearchDirectionIsForward==isBackWard) {
            lastSearchDirectionIsForward = !isBackWard;
            findWord(isBackWard);
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
                area.setFocusable(true);
                area.select(offset, find.length() + offset);
                startFrom = offset + (isBackWard ? -1 : (find.length() + 1));
                return;
            }
            startFrom = isBackWard ? (area.getText().length() - 1) : 0;
        }
        JOptionPane.showMessageDialog(this, "No matches found!");
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
