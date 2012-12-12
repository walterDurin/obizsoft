package ru.lanit.dibr.utils.gui;

import javax.swing.*;
import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;
import javax.swing.text.BadLocationException;

import ru.lanit.dibr.utils.core.*;

import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.awt.*;
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
    private String find = null;
//    private boolean isFormatted = true;

    private AbstractFilter invertedFilter = null;
    private AbstractFilter directFilter = null;

//    private String blockFilter = null;
//    private boolean inverseBlockFilter = false; //if set true then block contained @blockFilter will be hidden
//    private StringBuilder buffer = new StringBuilder();
//    private StringBuffer currentBlock = new StringBuffer();

    private int startFrom = 0;
    private int offset = 0;


    public LogPanel(LogSource logSource, String blockPattern) {
        super(new JTextArea());
        area = ((JTextArea) getViewport().getView());

        //area.putClientProperty(sun.swing.SwingUtilities2.AA_TEXT_PROPERTY_KEY, new Boolean(false));

//        ((Graphics2D)getGraphics()).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
//                RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

        area.setEditable(false);
//        if(System.getProperty("os.name").contains("OS X")) {
//            area.setFont(new Font("Courier", 0, 13));
//        } else {
        area.setFont(new Font("Courier New", 0, 12));
//        }
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
            //filtersChain.addFilterToQueue(new XmlFormatFilter(blockPattern));
            String nextLine;
            area.addKeyListener(this);
            area.addCaretListener(this);

//        this.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
//            public void adjustmentValueChanged(AdjustmentEvent e) {
//                    if(getVerticalScrollBar().isVisible()) {
//                    System.out.println("getVerticalScrollBar().getValue(): " + getVerticalScrollBar().getValue());
//                    System.out.println("getVerticalScrollBar().getHeight()" + getVerticalScrollBar().getVisibleAmount());
//                    System.out.println("getVerticalScrollBar().isVisible()" + getVerticalScrollBar().isVisible());
//                    System.out.println("getVerticalScrollBar().getMaximum()" + getVerticalScrollBar().getMaximum());
//                    System.out.println((getVerticalScrollBar().getValue() + getVerticalScrollBar().getHeight()) - getVerticalScrollBar().getMaximum());
//                    setAutoScroll((getVerticalScrollBar().getValue() + getVerticalScrollBar().getHeight()) - getVerticalScrollBar().getMaximum() > - 50);
//                }
//            }
//        });


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
            //area.setCaretPosition(area.getDocument().getLength() - nextLine.length());
            getVerticalScrollBar().setValue(getVerticalScrollBar().getMaximum());
        }
        getParent().repaint();
        repaint();
    }

    public void stop() {
        stopped = true;
    }

    public void setAutoScroll(boolean autoScroll) {
//        System.out.println("autoscroll changed to: '" + autoScroll +"'");
        this.autoScroll = autoScroll;
    }

    public void keyPressed(KeyEvent ke) {

        if ((ke.getKeyCode() == KeyEvent.VK_F5)) { //Нажали F5
            filtersChain.setPaused(true);
            filtersChain.reset();
            area.setText("");
            filtersChain.setPaused(false);
//        } else if ((ke.getKeyCode() == 88)) { //Нажали X
//            if(isFormatted=!isFormatted) {
//                setFilter(new XmlFormatFilter(blockPattern));
//            } else {
//                removeFilter(new XmlFormatFilter(blockPattern));
//            }
        } else if ((ke.getKeyCode() == 27)) { //Нажали PgUp
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
        } else if ((ke.getKeyCode() == 70) && ((ke.getModifiers() == KeyEvent.CTRL_MASK) || (ke.getModifiers() == (KeyEvent.CTRL_MASK | KeyEvent.SHIFT_MASK)))) {  //  Нажали Ctrl + F
            find = (String) JOptionPane.showInputDialog(this, "FIND:\n", "Find", JOptionPane.INFORMATION_MESSAGE, null, null, null);
            System.out.println("find");
            startFrom = area.getCaretPosition();
            findWord(ke.getModifiers() == (KeyEvent.CTRL_MASK | KeyEvent.SHIFT_MASK));
        } else if (ke.getKeyCode() == KeyEvent.VK_F3) { // F3 (+Shift)
            findWord(ke.getModifiers() == KeyEvent.SHIFT_MASK);
        } else if ((ke.getKeyCode() == 71) && (ke.getModifiers() == KeyEvent.CTRL_MASK || ke.getModifiers() == (KeyEvent.CTRL_MASK | KeyEvent.SHIFT_MASK))) {  // GREP filter
            boolean inverseGrep = ke.getModifiers() == (KeyEvent.CTRL_MASK | KeyEvent.SHIFT_MASK);
            String grepPattern = (String) JOptionPane.showInputDialog(this, "GREP:\n", "Grep", JOptionPane.INFORMATION_MESSAGE, null, null, null);
            System.out.println("Grep entered: '" + grepPattern + "'");

            if(grepPattern.isEmpty()) {
                if(inverseGrep) {
                    invertedFilter = null;
                } else {
                    directFilter = null;
                }
            }

            if(inverseGrep) {
                if(invertedFilter==null || !(invertedFilter instanceof GrepFilter)) {
                    invertedFilter = new GrepFilter(grepPattern, true);
                } else {
                    ((GrepFilter)invertedFilter).addStringToSearch(grepPattern);
                }
                if(directFilter!=null && !(directFilter instanceof GrepFilter)) {
                    directFilter = null;
                }
            } else {
                if(directFilter==null || !(directFilter instanceof GrepFilter)) {
                    directFilter = new GrepFilter(grepPattern, false);
                } else {
                    ((GrepFilter) directFilter).addStringToSearch(grepPattern);
                }

            }

            resetFilters();

        } else if ((ke.getKeyCode() == 66) && blockPattern != null && (ke.getModifiers() == KeyEvent.CTRL_MASK || ke.getModifiers() == (KeyEvent.CTRL_MASK | KeyEvent.SHIFT_MASK))) {  //BLOCK filter
            //ToDo: если blockPattern==null - Сообщить об этом и предложить его ввести. Показав что нибудь по дефолту. А потом сделать визард с проверкой по строке из лога.
            boolean inverseBlock = ke.getModifiers() == (KeyEvent.CTRL_MASK | KeyEvent.SHIFT_MASK);
            String blockSearchPattern = (String) JOptionPane.showInputDialog(this, "Block filter:\n", "Block filter", JOptionPane.INFORMATION_MESSAGE, null, null, null);
            System.out.println("blockFilter entered: '" + blockSearchPattern + "'");

            if(blockPattern.isEmpty()) {
                if(inverseBlock) {
                    invertedFilter = null;
                } else {
                    directFilter = null;
                }
            }

            if(inverseBlock) {
                if(invertedFilter==null || !(invertedFilter instanceof BlockFilter)) {
                    invertedFilter = new BlockFilter(blockPattern, blockSearchPattern, true);
                } else {
                    ((BlockFilter) invertedFilter).addStringToSearch(blockSearchPattern);
                }
            } else {
                if(directFilter==null || !(directFilter instanceof BlockFilter)) {
                    directFilter = new BlockFilter(blockPattern, blockSearchPattern, false);
                } else {
                    ((BlockFilter) directFilter).addStringToSearch(blockSearchPattern);
                }
            }

            resetFilters();

        } else {
            System.out.println(ke.getKeyCode());
        }
    }

    private void resetFilters() {
        logSource.setPaused(true);
        filtersChain = logSource;
        area.setText("");
        if(invertedFilter!=null) {
            filtersChain = invertedFilter.apply(filtersChain);
        }
        if(directFilter!=null) {
            filtersChain = directFilter.apply(filtersChain);
        }
        logSource.reset();
        logSource.setPaused(false);
    }

    private void findWord(boolean isBackWard) {
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
        //To change body of implemented methods use File | Settings | File Templates.
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
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void mouseExited(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
