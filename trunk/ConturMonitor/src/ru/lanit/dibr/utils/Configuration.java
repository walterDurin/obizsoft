package ru.lanit.dibr.utils;

import com.sun.org.apache.xerces.internal.dom.DeferredElementImpl;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import java.util.*;
import java.io.IOException;
import java.io.File;

import ru.lanit.dibr.utils.gui.configuration.Host;
import ru.lanit.dibr.utils.gui.configuration.LogFile;
import ru.lanit.dibr.utils.gui.configuration.Portmap;
import ru.lanit.dibr.utils.gui.configuration.Tunnel;

/**
 * Created by IntelliJ IDEA.
 * User: VTaran
 * Date: 16.08.2010
 * Time: 22:07:28
 */
public class Configuration {

	private Map<Host, Map<String, LogFile>> servers;
    private Map<String, Tunnel> tunnels = new HashMap<String, Tunnel>();

	public Map<Host, Map<String, LogFile>> getServers() {
		return servers;
	}

	public void setServers(Map<Host, Map<String, LogFile>> servers) {
		this.servers = servers;
	}

	public Configuration() {
		try {
			
			servers = new LinkedHashMap<Host, Map<String, LogFile>>();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        	DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new File("settings.xml"));

            NodeList tunnelsList = doc.getElementsByTagName("tunnel");
            for (int i = 0; i < tunnelsList.getLength(); i++) {
                Node item = tunnelsList.item(i);
                Host nextHost = readHost(item);
                String name = item.getAttributes().getNamedItem("name").getNodeValue();

                List<Portmap> portmapList = new ArrayList<Portmap>();

                NodeList portmapNodeList = item.getChildNodes();
                for (int j =0; j < portmapNodeList.getLength(); j++) {
                    Node portmapNode = portmapNodeList.item(j);
                    if(!portmapNode.getNodeName().equals("L")) {
                        continue;
                    }
                    int localPort = Integer.parseInt(portmapNode.getAttributes().getNamedItem("localPort").getNodeValue());
                    String destHost = portmapNode.getAttributes().getNamedItem("destHost").getNodeValue();
                    int destPort = Integer.parseInt(portmapNode.getAttributes().getNamedItem("destPort").getNodeValue());
                    portmapList.add(new Portmap(localPort, destHost, destPort));
                }

                tunnels.put(name, new Tunnel(nextHost, portmapList));
            }


			NodeList serversList = doc.getElementsByTagName("server");
			for (int i = 0; i < serversList.getLength(); i++) {
                Node server = serversList.item(i);
                Host nextHost = readHost(server);

				System.out.println(nextHost);
				NodeList logList = server.getChildNodes();
				servers.put(nextHost, new LinkedHashMap<String, LogFile>());
				for(int j = 0; j < logList.getLength() ; j++  ) {
					if(logList.item(j).getNodeName().equals("log")) {
                        NamedNodeMap logElement = logList.item(j).getAttributes();
                        String name = logElement.getNamedItem("name").getNodeValue();
                        String file = logElement.getNamedItem("file").getNodeValue();
                        String blockPattern = null;
                        if(logElement.getNamedItem("blockPattern")!=null) {
                            blockPattern = logElement.getNamedItem("blockPattern").getNodeValue().trim();
                            if(blockPattern.length()==0) {
                                blockPattern = null;
                            }
                        }
                        servers.get(nextHost).put(name, new LogFile(name, file, blockPattern));
                    }
				}
			}
		} catch (IOException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		} catch (SAXException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		} catch (ParserConfigurationException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}

    private Host readHost(Node server) {
        String descr = server.getAttributes().getNamedItem("name").getNodeValue();
        String host = server.getAttributes().getNamedItem("host").getNodeValue();
        String port = "22";
        if(server.getAttributes().getNamedItem("port")!=null) {
            port = server.getAttributes().getNamedItem("port").getNodeValue();
        }
        String user = server.getAttributes().getNamedItem("user").getNodeValue();
        String password = null;
        if(server.getAttributes().getNamedItem("password")!=null) {
            password = server.getAttributes().getNamedItem("password").getNodeValue();
        }
        String pem = null;
        if(server.getAttributes().getNamedItem("pem")!=null) {
            pem = server.getAttributes().getNamedItem("pem").getNodeValue();
        }
        String encoding = null;
        if(server.getAttributes().getNamedItem("encoding")!=null) {
            encoding = server.getAttributes().getNamedItem("encoding").getNodeValue();
        }
        if(encoding==null || encoding.trim().length()==0) {
            encoding = System.getProperty("file.encoding");
        }

        String proxyHost = null;
        String proxyType = null;
        String proxyPort = null;
        if(server.getAttributes().getNamedItem("proxyHost")!=null) {
            proxyHost = server.getAttributes().getNamedItem("proxyHost").getNodeValue();
            if(server.getAttributes().getNamedItem("proxyType")!=null) {
                proxyType = server.getAttributes().getNamedItem("proxyType").getNodeValue();
            }
            if(server.getAttributes().getNamedItem("proxyPort")!=null) {
                proxyPort = server.getAttributes().getNamedItem("proxyPort").getNodeValue();
            }
        }

        Tunnel tunnel = null;
        if(server.getAttributes().getNamedItem("tunnel")!=null) {
            tunnel = tunnels.get(server.getAttributes().getNamedItem("tunnel").getNodeValue());
        }

        Host nextHost;
        if(proxyHost!=null) {
            if(proxyPort==null || proxyPort.trim().length()==0) {
                proxyPort="0";
            }
            nextHost = new Host(descr, host, Integer.parseInt(port), user, password, pem, encoding, proxyHost, Integer.parseInt(proxyPort), proxyType, tunnel);
        } else {
            nextHost = new Host(descr, host, Integer.parseInt(port), user, password, pem, encoding, tunnel);
        }
        return nextHost;
    }
}
