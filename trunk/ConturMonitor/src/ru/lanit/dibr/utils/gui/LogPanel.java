package ru.lanit.dibr.utils.gui;

import javax.swing.*;
import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;

import ru.lanit.dibr.utils.core.FilteredSource;
import ru.lanit.dibr.utils.core.LogSource;

import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: VTaran
 * Date: 16.08.2010
 * Time: 15:56:23
 */
public class LogPanel extends JScrollPane implements KeyListener, CaretListener, MouseListener {
    private LogSource logSource;
    private FilteredSource filtersChain;
    private String blockPattern;
    private boolean stopped = false;
    private JTextArea area;
    private boolean autoScroll = true;
    private String find = null;
    private String grep = null;
    private String blockFilter = null;
    private boolean inverseBlockFilter = false; //if set true then block contained @blockFilter will be hidden
    private boolean inverseGrep = false; //if set true then lines contained @grep will be hidden
    private int startFrom = 0;
    private int offset = 0;
    private StringBuilder buffer = new StringBuilder();
    private StringBuffer currentBlock = new StringBuffer();


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
        this.logSource = logSource;
        this.blockPattern = blockPattern;
    }

    public void connect() throws Exception {
        try {
            logSource.startRead();
            filtersChain = new FilteredSource(logSource);
            String nextLine;
            area.addKeyListener(this);
            area.addCaretListener(this);

//        this.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
//            public void adjustmentValueChanged(AdjustmentEvent e) {
//                System.out.println("e.getValue(): " + e.getValue());
//                System.out.println("getVerticalScrollBar().getHeight()" + getVerticalScrollBar().getHeight());
//                System.out.println("getVerticalScrollBar().isVisible()" + getVerticalScrollBar().isVisible());
//                System.out.println("getVerticalScrollBar().getMaximum()" + getVerticalScrollBar().getMaximum());
//                System.out.println((e.getValue() + getVerticalScrollBar().getHeight()) - getVerticalScrollBar().getMaximum());
//                setAutoScroll(!getVerticalScrollBar().isVisible() || (e.getValue() + getVerticalScrollBar().getHeight()) == getVerticalScrollBar().getMaximum());
//            }
//        });


            this.addMouseWheelListener(new MouseAdapter() {
                @Override
                public void mouseWheelMoved(MouseWheelEvent e) {
                    //System.out.println(e);
                    setAutoScroll(!getVerticalScrollBar().isVisible() || (getVerticalScrollBar().getValue() + getVerticalScrollBar().getHeight()) == getVerticalScrollBar().getMaximum());
                }
            });


            while ((nextLine = logSource.readLine()) != null && !stopped) {
                buffer.append(nextLine).append("\n");
                if (blockFilter != null) {
                    if (nextLine.matches("\n?" + blockPattern + ".*") && currentBlock != null) {
                        if ((currentBlock.indexOf(blockFilter) >= 0) ^ inverseBlockFilter) {
                            appendLine(currentBlock.toString());
                        }
                        currentBlock = new StringBuffer();
                    }
                } else if (grep == null || (nextLine.contains(grep) ^ inverseGrep)) {
                    appendLine(nextLine);
                }
            }
        } finally {
            logSource.close();
        }
    }

    private void appendLine(String nextLine) {
        area.append("\n" + nextLine);
        if (autoScroll)
            area.setCaretPosition(area.getDocument().getLength() - nextLine.length());
        getParent().repaint();
        repaint();
    }

    public void stop() {
        stopped = true;
    }

    public void setAutoScroll(boolean autoScroll) {
        //System.out.println("autoscroll changed to: '" + autoScroll +"'");
        this.autoScroll = autoScroll;
    }

    public void keyPressed(KeyEvent ke) {

        if ((ke.getKeyCode() == 33)) { //Нажали PgUp
            setAutoScroll(false);
        } else if ((ke.getKeyCode() == 35) && (ke.getModifiers() == KeyEvent.CTRL_MASK)) { //Нажали Cntrl + PgDown
            setAutoScroll(true);
        } else if ((ke.getKeyCode() == 70) && (ke.getModifiers() == KeyEvent.CTRL_MASK)) {  //  Нажали Ctrl + F
            find = (String) JOptionPane.showInputDialog(this, "FIND:\n", "Find", JOptionPane.INFORMATION_MESSAGE, null, null, null);
            System.out.println("find");
            findWord();
        } else if (ke.getKeyCode() == KeyEvent.VK_F3) { // F3 (+Shift)
            if (ke.getModifiers() == KeyEvent.SHIFT_MASK) {
                findWordBackward();
            } else {
                findWord();
            }
        } else if ((ke.getKeyCode() == 71) && (ke.getModifiers() == KeyEvent.CTRL_MASK || ke.getModifiers() == (KeyEvent.CTRL_MASK | KeyEvent.SHIFT_MASK))) {
            inverseGrep = ke.getModifiers() == (KeyEvent.CTRL_MASK | KeyEvent.SHIFT_MASK);
            grep = (String) JOptionPane.showInputDialog(this, "GREP:\n", "Grep", JOptionPane.INFORMATION_MESSAGE, null, null, null);
            System.out.println("Grep entered: '" + grep + "'");
            if (grep != null && grep.trim().length() > 0) {
                blockFilter = null;
                grep = grep.trim();
                int idx1 = 0;
                int idx2 = 0;
                StringBuilder filteredString = new StringBuilder();
                String nextLine = "";
                while ((idx2 = buffer.indexOf("\n", idx2 + 1)) >= 0) {
                    nextLine = buffer.substring(idx1, idx2);
                    if (nextLine.contains(grep) ^ inverseGrep) {
                        filteredString.append(nextLine);
                    }
                    idx1 = idx2;
                }
                area.setText(filteredString.toString());
                if (autoScroll)
                    area.setCaretPosition(filteredString.lastIndexOf("\n") + 1);
                getParent().repaint();
                repaint();
            } else {
                grepOff();
            }

        } else if ((ke.getKeyCode() == 66) && blockPattern != null && (ke.getModifiers() == KeyEvent.CTRL_MASK || ke.getModifiers() == (KeyEvent.CTRL_MASK | KeyEvent.SHIFT_MASK))) {
            inverseBlockFilter = ke.getModifiers() == (KeyEvent.CTRL_MASK | KeyEvent.SHIFT_MASK);
            blockFilter = (String) JOptionPane.showInputDialog(this, "Block filter:\n", "Block filter", JOptionPane.INFORMATION_MESSAGE, null, null, null);
            System.out.println("blockFilter entered: '" + blockFilter + "'");
            if (blockFilter != null && blockFilter.trim().length() > 0) {
                grep = null;
                blockFilter = blockFilter.trim();
                int idx1 = 0;
                int idx2 = 0;
                StringBuilder filteredString = new StringBuilder();
                StringBuffer block = null;
                String nextLine = "";
                while ((idx2 = buffer.indexOf("\n", idx2 + 1)) >= 0) {
                    nextLine = buffer.substring(idx1, idx2);
                    idx1 = idx2;
                    if (nextLine.matches("\\n?" + blockPattern + ".*") && block != null) {
                        if ((block.indexOf(blockFilter) >= 0) ^ inverseBlockFilter) {
                            filteredString.append(block);
                        }
                        block = new StringBuffer();
                    }
                    if (block == null) {
                        block = new StringBuffer();
                    }
                    block.append(nextLine);
                }
                area.setText(filteredString.toString());
                if (autoScroll)
                    area.setCaretPosition(filteredString.lastIndexOf("\n") + 1);
                getParent().repaint();
                repaint();
            } else {
                blockFilterOff();
            }

        } else {
            System.out.println(ke.getKeyCode());
        }
    }

    private void blockFilterOff() {
        blockFilter = null;
        area.setText(buffer.toString());
        if (autoScroll)
            area.setCaretPosition(buffer.lastIndexOf("\n") + 1);
        getParent().repaint();
        repaint();
    }

    private void grepOff() {
        grep = null;
        area.setText(buffer.toString());
        if (autoScroll)
            area.setCaretPosition(buffer.lastIndexOf("\n") + 1);
        getParent().repaint();
        repaint();
    }

    private void findWord() {
        offset = area.getText().indexOf(find, startFrom);
        if (offset > -1) {
            area.setFocusable(true);
            area.select(offset, find.length() + offset);
            startFrom = find.length() + offset + 1;
        } else JOptionPane.showMessageDialog(this, "No (more) matches");
    }

    private void findWordBackward() {
        offset = area.getText().lastIndexOf(find, startFrom - find.length() - 2);
        if (offset > -1) {
            area.setFocusable(true);
            area.select(offset, find.length() + offset);
            startFrom = find.length() + offset + 1;
        } else JOptionPane.showMessageDialog(this, "No (more) matches");
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void caretUpdate(CaretEvent e) {
        startFrom = e.getDot();
    }

    public void mouseClicked(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void mousePressed(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void mouseReleased(MouseEvent e) {
        if (area.getSelectedText() != null) {
            setAutoScroll(false);
            StringSelection ss = new StringSelection(area.getSelectedText());
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
        }
    }

    public void mouseEntered(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void mouseExited(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
