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

    public void connect() {
        if(isConnected)
            return;
        try {
            Session session = host.createSession();
            for (Portmap portmap : portmaps) {
                session.setPortForwardingL(portmap.getLocalPort(), portmap.getDestHost(), portmap.getDestPort());
            }
            session.connect();

            isConnected = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
