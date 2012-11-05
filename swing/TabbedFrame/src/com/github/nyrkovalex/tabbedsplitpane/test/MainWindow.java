package com.github.nyrkovalex.tabbedsplitpane.test;

import com.github.nyrkovalex.tabbedsplitpane.gui.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 11/4/12
 * Time: 12:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class MainWindow {
    private JPanel rootPane = null;
    private StatedSplitPane splitPane = null;
    private JToolBar toolBar = null;
    private List<Tab> tabs = null;
    JTabbedPane firstTab = null;
    JTabbedPane secondTab = null;
    JTabbedPane activePane = null;

    /**
     * Holy fucking mess here
     */
    public MainWindow() {
        tabs = new ArrayList<Tab>();
    }

    public static void main(String[] args) {
        final MainWindow window = new MainWindow();
        window.createUIComponents();
        window.show();
    }

    private void show() {
        JFrame frame = new JFrame("Test frame");
        frame.add(rootPane);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setVisible(true);
    }

    private void createUIComponents() {
        rootPane = new JPanel();
        rootPane.setLayout(new BoxLayout(rootPane, BoxLayout.Y_AXIS));

        createToolBar();

        createTabPane();

        rootPane.add(toolBar);
        rootPane.add(splitPane);
        rootPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                splitPane.setPreferredSize(new Dimension(rootPane.getWidth(), rootPane.getHeight() - toolBar.getHeight()));
                splitPane.setSplitterLocation();
            }
        });
    }

    private void createTabPane() {
        FocusAdapter focusAdapter = new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                activePane = (JTabbedPane) e.getSource();
            }
        };

//        ActionListener firstMoveListstener = new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                ((Tab)((TabHeaderActionItem)e.getSource()).getComponent()).move(secondTab);
//            }
//        };
//
//        ActionListener closeListener = new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Tab tab = ((Tab)((TabHeaderActionItem)e.getSource()).getComponent());
//                tab.close();
//                tabs.remove(tab);
//                if (tabs.size() < 2) {
//                    splitPane.setState(new SplitPaneNotSplittedState(splitPane));
//                }
//            }
//        };

        MouseAdapter firstMoveAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Tab tab = (Tab) ((TabHeaderActionItem) e.getSource()).getComponent();
                moveTab(tab, secondTab);
            }
        };

        MouseAdapter closeAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Tab tab = ((Tab) ((TabHeaderActionItem) e.getSource()).getComponent());
                tab.close();
                tabs.remove(tab);
                if (tabs.size() < 2) {
                    splitPane.joinTabs();
                }
            }
        };

        TabHeaderFactory firstFactory = new LabelTabHeaderFactory(firstMoveAdapter, closeAdapter);
        firstTab = new ActiveHeaderTabbedPane(SwingConstants.BOTTOM, firstFactory);
        firstTab.addFocusListener(focusAdapter);

//        ActionListener secondMoveListener = new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                ((Tab)((TabHeaderActionItem)e.getSource()).getComponent()).move(firstTab);
//            }
//        };

        MouseAdapter secondMoveAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Tab tab = (Tab) ((TabHeaderActionItem) e.getSource()).getComponent();
                moveTab(tab, firstTab);
            }
        };

        TabHeaderFactory secondFactory = new LabelTabHeaderFactory(secondMoveAdapter, closeAdapter);
        secondTab = new ActiveHeaderTabbedPane(SwingConstants.BOTTOM, secondFactory);
        secondTab.addFocusListener(focusAdapter);

        splitPane = new StatedSplitPane(firstTab, secondTab);
        splitPane.joinTabs();
        splitPane.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private void moveTab(Tab tab, JTabbedPane destination) {
        JTabbedPane pane = tab.getTabbedPane();
        tab.move(destination);
        if (pane.getTabCount() < 1) {
            moveAllTabs(firstTab);
        } else {
            splitPane.splitHorizontal();
        }
    }

    private void moveAllTabs(JTabbedPane tabbedPane) {
        for (Tab t : tabs) {
            t.move(tabbedPane);
        }
        splitPane.joinTabs();
    }

    private void createToolBar() {
        toolBar = new JToolBar();

        JButton newTabButton = new JButton("+");
        newTabButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (activePane == null) {
                    createNewTab(firstTab);
                } else {
                    createNewTab(activePane);
                }
            }
        });
        toolBar.add(newTabButton);

        JButton horizontalSplitButton = new JButton("||");
        horizontalSplitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                splitPane.splitHorizontal();
            }
        });
        toolBar.add(horizontalSplitButton);

        JButton verticalSplitButton = new JButton("=");
        verticalSplitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                splitPane.splitVertical();
            }
        });
        toolBar.add(verticalSplitButton);

        JButton joinButton = new JButton("><");
        joinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveAllTabs(firstTab);
            }
        });
        toolBar.add(joinButton);

        JButton moveRightButton = new JButton(">>");
        moveRightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Component c = firstTab.getSelectedComponent();
                if (c != null) {
                    moveTab((Tab) c, secondTab);
                }
            }
        });
        toolBar.add(moveRightButton);

        JButton moveLeftButton = new JButton("<<");
        moveLeftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Component c = secondTab.getSelectedComponent();
                if (c != null) {
                    moveTab((Tab) c, firstTab);
                }
            }
        });

        toolBar.add(moveLeftButton);
        toolBar.add(Box.createHorizontalGlue());
        toolBar.setFloatable(false);
    }

    private void createNewTab(JTabbedPane tabbedPane) {
        String title = "Tab #" + String.valueOf(tabs.size());
        Tab tab = new Tab(tabbedPane, title);
        tab.add(new JLabel("I am a Tab!"));
        tabs.add(tab);
    }
}
