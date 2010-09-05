package klauer.ssh.common;

import com.jcraft.jsch.UserInfo;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * Authorization settings that are pulled from a source file ({@code .properties},
 * likely).  This class expects a key with no passphrase associated with it.
 * <p>
 * The defaults for this class are pulled from a file in your classpath,
 * {@code auth_props.properties} which will have within it the following
 * information:
 * <ol>
 * <li>{@code ssh.auth.private.key} - Location of the private keyfile to be
 * used for developement</li>
 * <li>{@code ssh.auth.known.hosts} - Location fo the known_hosts file often
 * found in your {@code ~/.ssh/} directory.</li>
 * <li>{@code ssh.auth.username} - Username to use for the server.</li>
 * <li>{@code ssh.auth.host} - Host to connect to (on port 22).</li>
 * </ol>
 * @author Nick Klauer <klauer@gmail.com>
 */
public class AuthSettingsFromSource implements AuthEngine {

	public static String private_key_file;
	public static String known_hosts_file;
	public static String username;
	public static String host;
	public static Properties auth_props;
	public static String script_to_load;
	public static URL url;

	protected static AuthSettingsFromSource settings;

	private AuthSettingsFromSource() throws IOException {
		url = this.getClass().getResource("/auth_props.properties");
		auth_props = new Properties();
		auth_props.load(url.openStream());
		private_key_file = auth_props.getProperty("ssh.auth.private.key");
		known_hosts_file = auth_props.getProperty("ssh.auth.known.hosts");
		username = auth_props.getProperty("ssh.auth.username");
		host = auth_props.getProperty("ssh.auth.host");
		script_to_load = auth_props.getProperty("ssh.auth.script.to.load");
	}

	public static AuthSettingsFromSource getInstance() throws IOException {
		if (settings == null) { return new AuthSettingsFromSource(); }
		return settings;
	}


	/**
	 * 
	 * @return the simplest UserInfo necessary for this context.
	 */
	@Override
	public UserInfo getUserInfo() {
		return new EmptyUserInfo();
	}
}
