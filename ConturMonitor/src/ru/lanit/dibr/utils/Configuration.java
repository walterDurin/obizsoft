package ru.lanit.dibr.utils;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import java.util.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;
import java.beans.XMLEncoder;
import java.beans.XMLDecoder;

import ru.lanit.dibr.utils.gui.Host;

/**
 * Created by IntelliJ IDEA.
 * User: VTaran
 * Date: 16.08.2010
 * Time: 22:07:28
 */
public class Configuration {

	private Map<Host, Map<String, String>> servers;

	public Map<Host, Map<String, String>> getServers() {
		return servers;
	}

	public void setServers(Map<Host, Map<String, String>> servers) {
		this.servers = servers;
	}

	public Configuration() {
		try {
			
			servers = new HashMap<Host, Map<String, String>>();
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
				String password = list.item(i).getAttributes().getNamedItem("password").getNodeValue();
                String encoding = null;
                if(list.item(i).getAttributes().getNamedItem("encoding")!=null) {
                    encoding = list.item(i).getAttributes().getNamedItem("encoding").getNodeValue();
                }
                if(encoding==null || encoding.trim().length()==0) {
                    encoding = System.getProperty("file.encoding");
                }

				Host nextHost = new Host(descr, host, Integer.parseInt(port), user, password, encoding);
				System.out.println(nextHost);
				NodeList logList = list.item(i).getChildNodes();
				servers.put(nextHost, new HashMap<String, String>());
				for(int j = 0; j < logList.getLength() ; j++  ) {
					if(logList.item(j).getNodeName().equals("log")) {
                        NamedNodeMap logElement = logList.item(j).getAttributes();
                        servers.get(nextHost).put(logElement.getNamedItem("name").getNodeValue(), logElement.getNamedItem("file").getNodeValue());
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
