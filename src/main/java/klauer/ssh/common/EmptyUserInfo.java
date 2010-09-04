package klauer.ssh.common;

import com.jcraft.jsch.UserInfo;

/**
 * The most restrictive, useless {@link UserInfo} you can possibly have and still
 * work (maybe).
 * 
 * @author Nick Klauer <klauer@gmail.com>
 */
public class EmptyUserInfo implements UserInfo {

	@Override
	public String getPassphrase() {
		return null;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public boolean promptPassword(String message) {
		return false;
	}

	@Override
	public boolean promptPassphrase(String message) {
		return false;
	}

	@Override
	public boolean promptYesNo(String message) {
		return false;
	}

	@Override
	public void showMessage(String message) {
		System.out.println(message);
	}

}
