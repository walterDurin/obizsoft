package ru.lanit.sftp;

import com.jcraft.jsch.*;

/**
 * Created by IntelliJ IDEA.
 * User: VTaran
 * Date: 12.11.2009
 * Time: 11:49:28
 */
public class Main {

    public static void main(String[] args) {
        try {
            JSch jsch=new JSch();
            Session session=jsch.getSession("bea", "portal1-test-c1", 22);

            UserInfo ui = new MyUserInfo();
            session.setUserInfo(ui);
            session.connect();

            Channel channel=session.openChannel("sftp");
            channel.connect();
            ChannelSftp c = (ChannelSftp)channel;

            java.util.Vector vv=c.ls(".");
            if(vv!=null) {
	            for(int ii=0; ii<vv.size(); ii++) {
                    Object obj=vv.elementAt(ii);
                    if(obj instanceof com.jcraft.jsch.ChannelSftp.LsEntry){
                      System.out.println(((com.jcraft.jsch.ChannelSftp.LsEntry)obj).getLongname());
                    }
                }
            }
            

            
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        }

    }

    private static class MyUserInfo implements UserInfo {
        public String getPassphrase() {
            return null;
        }

        public String getPassword() {
            return "bea123";
        }

        public boolean promptPassword(String s) {
            System.out.println("promptPassword");
            System.out.println(s);
            return true;
        }

        public boolean promptPassphrase(String s) {
            System.out.println("promptPassphrase");
            System.out.println(s);
            return false;
        }

        public boolean promptYesNo(String s) {
            System.out.println("promptYesNo");
            System.out.println(s);
            return true;
        }

        public void showMessage(String s) {
            System.out.println("showMessage");
            System.out.println(s);
        }
    }
}
