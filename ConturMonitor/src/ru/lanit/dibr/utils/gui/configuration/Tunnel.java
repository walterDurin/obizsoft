package ru.lanit.dibr.utils.gui.configuration;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import ru.lanit.dibr.utils.utils.Utils;

import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * User: Vova
 * Date: 22.10.13
 * Time: 0:39
 */
public class Tunnel {
    private Host host;
    private List<Portmap> portmaps;
    private boolean isConnected = false;
    private Session session;

    public Tunnel(Host host, List<Portmap> portmaps) {
        this.host = host;
        this.portmaps = portmaps;
    }

    @Override
    public String toString() {
        String str = "";
        for (Portmap portmap : portmaps) {
            str += portmap.toString();
        }
        return str + "; " + super.toString();
    }

    public synchronized void connect(final BlockingQueue<String> debugOutput, boolean useCompression) {
        if(isConnected) {
            Utils.writeToDebugQueue(debugOutput, "Tunnel already connected.");
            return;
        }
        try {
            Utils.writeToDebugQueue(debugOutput, "Create tunnel session..");
            session = host.createSession(debugOutput, useCompression);
            Utils.writeToDebugQueue(debugOutput, "Create tunnel port mappings..");
            for (Portmap portmap : portmaps) {
                session.setPortForwardingL(portmap.getLocalPort(), portmap.getDestHost(), portmap.getDestPort());
            }
            Utils.writeToDebugQueue(debugOutput, "Try to open tunnel on: " + host.getDescription());
            session.connect(30000);
            isConnected = true;
            Utils.writeToDebugQueue(debugOutput, "Tunnel are connected on: " + host.getDescription() + ". Starting keepalive message thread..");

            new Thread(new Runnable() {
                public void run() {
                    String message = "";
                    while (session.isConnected()) {
                        try {
                            session.sendKeepAliveMsg();
                            Thread.sleep(1500);
                        } catch (InterruptedException e) {
                            message = e.getMessage();
                            e.printStackTrace();
                        } catch (Exception e) {
                            message = e.getMessage();
                            e.printStackTrace();
                        }
                    }
                    Utils.writeToDebugQueue(debugOutput, "Tunnel session disconnected! error message: " + message);
                    isConnected = false;
                    for (Portmap portmap : portmaps) {
                        try {
                            session.delPortForwardingL(portmap.getLocalPort());
                        } catch (JSchException e) {
                            e.printStackTrace();
                        }
                    }
                    session.disconnect();
                }
            }, "tunel connection monitor").start();


        } catch (Exception e) {
            Utils.writeToDebugQueue(debugOutput, "Error on open tunnel to host: " + host.getDescription());
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public boolean isConnectionAlive() {
        if(isConnected && session.isConnected()) {
            return true;
        } else {
            return false;
        }
    }
}
