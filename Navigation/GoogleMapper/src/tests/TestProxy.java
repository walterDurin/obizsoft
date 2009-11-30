package tests;

import java.net.Socket;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.InetSocketAddress;
import java.io.IOException;
import java.io.InputStream;

import junit.framework.TestCase;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.methods.GetMethod;

/**
 * User: vtaran
 * 25.02.2009 17:12:12*
 *
 * @author vtaran@lanit.ru
 */
public class TestProxy extends TestCase {
    public void testSocks() throws IOException {
        Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 1080));
        Socket s = new Socket(proxy);
        s.connect(new InetSocketAddress("mail.ru", 80));
        InputStream is = s.getInputStream();
        int i;
        while((i=is.read())!=-1) {
            System.out.print((char) i );
        }
    }

    public void testHttpClientProxy() throws IOException {
        HttpClient client = new HttpClient();
        HostConfiguration conf = new HostConfiguration();
        conf.setProxy("proxyma.lanit", 3128);
        GetMethod get = new GetMethod("http://mail.ru");
        client.executeMethod(conf, get);
        System.out.println(get.getResponseBodyAsString());
    }
}
