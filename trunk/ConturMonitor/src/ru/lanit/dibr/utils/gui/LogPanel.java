package ru.lanit.dibr.utils.gui;

import com.jcraft.jsch.*;

import javax.swing.*;
import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.BadLocationException;

import ru.lanit.dibr.utils.utils.MyUserInfo;

import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: VTaran
 * Date: 16.08.2010
 * Time: 15:56:23
 */
public class LogPanel extends JScrollPane implements KeyListener, CaretListener, MouseListener {
	private Host host;
	private String logPath;
	private boolean stopped = false;
	private JTextArea area;
	private boolean autoScroll = true;
	private String find = null;
	private int startFrom = 0;
	private int offset = 0;


	public LogPanel(Host host, String logPath) {
		super(new JTextArea());
		area = ((JTextArea)getViewport().getView());
		area.setEditable(false);
        area.setFont(new Font("Courier New", 0, 12));
        area.setBackground(new Color(0, 0, 0));
        area.setForeground(new Color(187, 187, 187));
        area.setSelectedTextColor(new Color(0, 0, 0));
        area.setSelectionColor(new Color(187, 187, 187));
        area.addMouseListener(this);
        this.host = host;
		this.logPath = logPath;

	}

	public void connect() throws JSchException, IOException, BadLocationException {
		JSch jsch=new JSch();
        Session session=jsch.getSession(host.getUser(), host.getHost(), host.getPort());
        session.setConfig("StrictHostKeyChecking", "no");
        if(host.getPem()!=null) {
            jsch.addIdentity(host.getPem());
        } else {
            UserInfo ui=new MyUserInfo(host.getPassword());
            session.setUserInfo(ui);
        }

		session.connect(30000);   // making a connection with timeout.
		ChannelExec channel= (ChannelExec) session.openChannel("exec");
		channel.setCommand("tail -f " + logPath);
		BufferedReader reader = new BufferedReader(new InputStreamReader(channel.getInputStream(), host.getDefaultEncoding()));
		String nextLine;

		channel.connect(3*1000);

		area.addKeyListener(this);
		area.addCaretListener(this);

		while((nextLine = reader.readLine())!=null && !stopped) {
			area.append("\n"+nextLine);
			if (autoScroll)
				area.setCaretPosition(area.getDocument().getLength()-nextLine.length());
//			try {
//				Robot robot = new Robot(area.getGraphicsConfiguration().getDevice());
//				robot.keyPress(java.awt.event.KeyEvent.VK_CONTROL);
//				robot.keyPress(java.awt.event.KeyEvent.VK_END);
//				robot.keyRelease(java.awt.event.KeyEvent.VK_END);
//				robot.keyRelease(java.awt.event.KeyEvent.VK_CONTROL);
//			} catch (AWTException e) {
//				e.printStackTrace();
//			}
			getParent().repaint();
			repaint();
		}

	}

	public void stop() {
		stopped = true;
	}

	public void setAutoScroll(boolean autoScroll) {
		this.autoScroll = autoScroll;
	}

	public void keyPressed(KeyEvent ke) {
		if((ke.getKeyCode() == 70 )&& (ke.getModifiers()==KeyEvent.CTRL_MASK) ) {
			find = (String)JOptionPane.showInputDialog(this,"FIND:\n","Find",JOptionPane.INFORMATION_MESSAGE,null,null,null);
			System.out.println("find");
			findWord();
		} else if(ke.getKeyCode() == KeyEvent.VK_F3) {
			if(ke.getModifiers()==KeyEvent.SHIFT_MASK) {
				findWordBackward();
			} else {
      			findWord();
			}
    	}
	}

	private void findWord() {
		offset = area.getText().indexOf(find,startFrom);
		if(offset > -1)
		{
		  area.setFocusable(true);
		  area.select(offset,find.length()+offset );
		  startFrom = find.length()+offset+1;
		}
		else JOptionPane.showMessageDialog(this,"No (more) matches");
	}

	private void findWordBackward() {
		offset = area.getText().lastIndexOf(find, startFrom - find.length() - 2);
		if(offset > -1) {
		  area.setFocusable(true);
		  area.select(offset,find.length()+offset );
		  startFrom = find.length()+offset+1;
		}
		else JOptionPane.showMessageDialog(this,"No (more) matches");
	}

	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}

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
        StringSelection ss = new StringSelection(area.getSelectedText());
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
    }

    public void mouseEntered(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void mouseExited(MouseEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
