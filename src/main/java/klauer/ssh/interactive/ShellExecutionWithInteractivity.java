package klauer.ssh.interactive;

import java.io.File;
import java.io.OutputStream;
import java.io.InputStream;

import com.google.common.io.Files;
import com.google.common.io.InputSupplier;
import com.google.common.io.OutputSupplier;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

/**
 * Class for interacting with a shell that you would like to use. This is an
 * example to create a workable shell that you can use for development with
 * applications on remote hosts that would need TTY interactivity.
 * 
 * @author Nick Klauer <klauer@gmail.com>
 * 
 */
public class ShellExecutionWithInteractivity {
	

	public static void main(String[] args) {
		try {
			JSch ch = new JSch();

			String host = "klauern.ath.cx";
			String user = "klauer";
			ch.addIdentity("/Users/klauer/.ssh/id_dsa");
			ch.setKnownHosts("/Users/klauer/.ssh/known_hosts");

			Session session = ch.getSession(user, host, 22);

			session.setUserInfo(new NickUserInfo());
			
			session.connect();
			
			Channel channel = session.openChannel("shell");
			
			
			OutputSupplier osw = Files.newOutputStreamSupplier(new File("/Users/klauer/Programming/Java/sshtoys/file.txt"));
			InputSupplier ins = Files.newInputStreamSupplier(new File("/Users/klauer/Programming/Java/sshtoys/file.txt"));
			
//			channel.setInputStream((InputStream) ins.getInput());
//			channel.setOutputStream((OutputStream) osw.getOutput());

			channel.setInputStream(System.in);
			channel.setOutputStream(System.out);
			
			channel.connect();	// Was missing this the entire time...
			
			
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public static class NickUserInfo implements UserInfo {

		public String getPassphrase() {
			// return System.console().readLine();
			return null;
		}

		public String getPassword() {
			// TODO Auto-generated method stub
//			return System.console().readLine();
			return null;
		}

		public boolean promptPassword(String message) {
			// TODO Auto-generated method stub
//			System.out.println(message);
			return false;
		}

		public boolean promptPassphrase(String message) {
			// TODO Auto-generated method stub
//			System.out.println(message);
			return false;
		}

		public boolean promptYesNo(String message) {
			// TODO Auto-generated method stub
			System.out.println(message);
			return true;
		}

		public void showMessage(String message) {
//			System.out.println(message);
		}

	}
}
