package ru.lanit.dibr.utils;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
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

/**
 * Created by IntelliJ IDEA.
 * User: VTaran
 * Date: 16.08.2010
 * Time: 22:07:28
 */
public class Configuration {

	private Map<Host, Map<String, LogFile>> servers;

	public Map<Host, Map<String, LogFile>> getServers() {
		return servers;
	}

	public void setServers(Map<Host, Map<String, LogFile>> servers) {
		this.servers = servers;
	}

	public Configuration() {
		try {
			
			servers = new HashMap<Host, Map<String, LogFile>>();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        	DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new File("settings.xml"));

			NodeList list = doc.getElementsByTagName("server");
			for (int i = 0; i < list.getLength(); i++) {
				String descr = list.item(i).getAttributes().getNamedItem("name").getNodeValue();
				String host = list.item(i).getAttributes().getNamedItem("host").getNodeValue();
                String port = "22";
                if(list.item(i).getAttributes().getNamedItem("port")!=null) {
				    port = list.item(i).getAttributes().getNamedItem("port").getNodeValue();
                }
				String user = list.item(i).getAttributes().getNamedItem("user").getNodeValue();
                String password = null;
                if(list.item(i).getAttributes().getNamedItem("password")!=null) {
                    password = list.item(i).getAttributes().getNamedItem("password").getNodeValue();
                }
                String pem = null;
                if(list.item(i).getAttributes().getNamedItem("pem")!=null) {
                    pem = list.item(i).getAttributes().getNamedItem("pem").getNodeValue();
                }
                String encoding = null;
                if(list.item(i).getAttributes().getNamedItem("encoding")!=null) {
                    encoding = list.item(i).getAttributes().getNamedItem("encoding").getNodeValue();
                }
                if(encoding==null || encoding.trim().length()==0) {
                    encoding = System.getProperty("file.encoding");
                }

                String proxyHost = null;
                String proxyType = null;
                String proxyPort = null;
                if(list.item(i).getAttributes().getNamedItem("proxyHost")!=null) {
                    proxyHost = list.item(i).getAttributes().getNamedItem("proxyHost").getNodeValue();
                    if(list.item(i).getAttributes().getNamedItem("proxyType")!=null) {
                        proxyType = list.item(i).getAttributes().getNamedItem("proxyType").getNodeValue();
                    }
                    if(list.item(i).getAttributes().getNamedItem("proxyPort")!=null) {
                        proxyPort = list.item(i).getAttributes().getNamedItem("proxyPort").getNodeValue();
                    }
                }


                Host nextHost;
                if(proxyHost!=null) {
                    if(proxyPort==null || proxyPort.trim().length()==0) {
                        proxyPort="0";
                    }
                    nextHost = new Host(descr, host, Integer.parseInt(port), user, password, pem, encoding, proxyHost, Integer.parseInt(proxyPort), proxyType);
                } else {
                    nextHost = new Host(descr, host, Integer.parseInt(port), user, password, pem, encoding);
                }


				System.out.println(nextHost);
				NodeList logList = list.item(i).getChildNodes();
				servers.put(nextHost, new HashMap<String, LogFile>());
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
}
