package klauer.ssh.common;

import com.jcraft.jsch.UserInfo;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * My User Information.
 * @author Nick Klauer <klauer@gmail.com>
 *
 */
public class NickUserInfo implements UserInfo {

	public static String private_key_file;
	public static String known_hosts_file;
	public static String username;
	public static String host;
	public static String script_to_load;
	public static Properties auth_props;
	public static URL url;

	public NickUserInfo() throws IOException {
		url = this.getClass().getResource("/auth_props.properties");
		auth_props = new Properties();
		auth_props.load(url.openStream());
		private_key_file = auth_props.getProperty("ssh.auth.private.key");
		known_hosts_file = auth_props.getProperty("ssh.auth.known.hosts");
		username = auth_props.getProperty("ssh.auth.username");
		host = auth_props.getProperty("ssh.auth.host");
		script_to_load = auth_props.getProperty("ssh.auth.script.to.load");
	}

	public String getPassphrase() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean promptPassword(String message) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean promptPassphrase(String message) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean promptYesNo(String message) {
		// TODO Auto-generated method stub
		return false;
	}

	public void showMessage(String message) {
		System.out.println(message);
	}
}
