package klauer.ssh.common;

import com.jcraft.jsch.UserInfo;
import java.io.File;

/**
 * Class to contain authorization details
 * @author Nick Klauer <klauer@gmail.com>
 */
public interface AuthEngine {

	/**
	 * Creates a UserInfo implementation based on the settings passed in.  For
	 * instance, if an AuthEngine uses a passphrase for the private_key, the
	 * UserInfo should contain functionality to be prompted and return the
	 * passphrase for a key, as required by JSch.
	 * @return context-specific UserInfo implementation
	 */
	public UserInfo getUserInfo();

	public String getUsername();
	public String getHost();
	public File getKnownHosts();
	public File getPrivateKey();

}
