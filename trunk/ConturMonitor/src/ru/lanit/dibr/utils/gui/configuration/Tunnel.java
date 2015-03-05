package ru.lanit.dibr.utils.gui.configuration;

import com.jcraft.jsch.Session;

import java.util.List;

/**
 * User: Vova
 * Date: 22.10.13
 * Time: 0:39
 */
public class Tunnel {
    private Host host;
    private List<Portmap> portmaps;
    private boolean isConnected = false;

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

    public synchronized void connect() {
        if(isConnected)
            return;
        try {
            final Session session = host.createSession();
            for (Portmap portmap : portmaps) {
                session.setPortForwardingL(portmap.getLocalPort(), portmap.getDestHost(), portmap.getDestPort());
            }
            System.out.println("Try to open tunnel on: " + host.getDescription());
            session.connect(30000);
            isConnected = true;
            System.out.println("Tunnel are connected on: " + host.getDescription());

            new Thread(new Runnable() {
                public void run() {
                    while (session.isConnected()) {
                        try {
                            session.sendKeepAliveMsg();
                            Thread.sleep(1500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("Session disconnected!");
                    isConnected = false;
                    session.disconnect();
                }
            }, "tunel connection monitor").start();


        } catch (Exception e) {
            System.out.println("Error on open tunnel to host: " + host.getDescription());
            e.printStackTrace();
        }

    }
}
