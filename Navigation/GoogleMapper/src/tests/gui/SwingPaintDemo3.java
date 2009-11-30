package tests.gui;

import org.apache.commons.io.FileUtils;

import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseMotionAdapter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class SwingPaintDemo3 {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        System.out.println("Created GUI on EDT? "+
        SwingUtilities.isEventDispatchThread());
        JFrame f = new JFrame("Swing Paint Demo");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new MyPanel());
        f.pack();
        f.setVisible(true);
    }
}

class MyPanel extends JPanel {

    private int squareX = 50;
    private int squareY = 50;
    private int squareW = 20;
    private int squareH = 20;

    public MyPanel() {

        setBorder(BorderFactory.createLineBorder(Color.black));

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                moveSquare(e.getX(),e.getY());
            }
        });

        addMouseMotionListener(new MouseMotionListener() {
            public void mouseDragged(MouseEvent e) {
                moveSquare(e.getX(),e.getY());
            }

            public void mouseMoved(MouseEvent e) {                
            }
        });

    }

    private void moveSquare(int x, int y) {
        int OFFSET = 1;
        if ((squareX!=x) || (squareY!=y)) {
            repaint(squareX,squareY,squareW+OFFSET,squareH+OFFSET);
            squareX=x;
            squareY=y;
            repaint(squareX,squareY,squareW+OFFSET,squareH+OFFSET);
        }
    }


    public Dimension getPreferredSize() {
        return new Dimension(600,500);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawString("This is my custom Panel!",10,20);
        g.setColor(Color.RED);
        g.fillRect(squareX,squareY,squareW,squareH);
        g.setColor(Color.BLACK);
        g.drawRect(squareX,squareY,squareW,squareH);

        try {
            byte imageData[] = FileUtils.readFileToByteArray(new File("mapYa.png"));
            BufferedImage tile = ImageIO.read(new ByteArrayInputStream(imageData));
            g.drawImage(tile,
                        10 , 10, 266, 266,     /* dst rectangle */
//                        0, 0, 128, 128, /* src area of image */
                        null);        
            imageData = FileUtils.readFileToByteArray(new File("traffikYa.png"));
            tile = ImageIO.read(new ByteArrayInputStream(imageData));
            g.drawImage(tile,
                        10 , 10, 266, 266,     /* dst rectangle */
//                        0, 0, 128, 128, /* src area of image */
                        null);
        } catch (IOException e) {
        }

    }
}