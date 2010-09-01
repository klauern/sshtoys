package klauer.ssh.common;

import com.jcraft.jsch.UserInfo;


/**
 * My User Information.
 * @author Nick Klauer <klauer@gmail.com>
 *
 */
public class NickUserInfo implements UserInfo {

	public static String private_key_file = "/Users/klauer/.ssh/id_dsa";
	public static String known_hosts_file = "/Users/klauer/.ssh/known_hosts";
	public static String username = "klauer";
	public static String host = "klauern.ath.cx";
	
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
