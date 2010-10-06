package klauer.ssh.common;

import com.jcraft.jsch.UserInfo;
import java.io.File;

/**
 * Simple, finalized implementation of an AuthEngine.  AuthEngine provides the
 * details for authorizing and authenticating to a remote server, by either
 * simple user/pass combination up to using {@code user@host} with a specific
 * private key and passphrase associated with it.
 * 
 * @author Nick Klauer <klauer@gmail.com>
 */
public class AuthEngineImpl implements AuthEngine {

	private final String user;
	private final String host;
	private final File private_key;
	private final File known_hosts;
	private final String password;
	private final String key_passphrase;
	private final UserInfo user_info;

	/**
	 * Full configuration for a new SSH connection.  All other constructors pass through
	 * this one, setting {@code null} values for customized connection settings.
	 * @param user
	 * @param host
	 * @param private_key
	 * @param known_hosts
	 * @param password
	 * @param key_passphrase
	 */
	public AuthEngineImpl(String user, String host, File private_key, File known_hosts, final String password, final String key_passphrase) {
		this.user = user;
		this.host = host;
		this.private_key = private_key;
		this.known_hosts = known_hosts;
		this.password = password;
		this.key_passphrase = key_passphrase;
		this.user_info = new UserInfo() {
			@Override
			public String getPassphrase() {
				return key_passphrase;
			}

			@Override
			public String getPassword() {
				return password;
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
				// TODO: use a logger or just output.
				System.out.println(message);
			}
		};
	}

	public AuthEngineImpl(String user, String host, File private_key, File known_hosts) {
		this(user, host, private_key, known_hosts, null, null);
	}

	public AuthEngineImpl(String user, String host, String password) {
		this(user, host, null, null, password, null);
	}

	public AuthEngineImpl(String user, String host, File private_key, File known_hosts, String key_passphrase) {
		this(user, host, private_key, known_hosts, null, key_passphrase);
	}

	/**
	 * For use with interactive terminals which may request information to authenticate
	 * as well as ask permission to read your known_hosts file.  This is required
	 * for JSch, and many default configurations are provided to automate any
	 * requirement to implement this yourself.
	 * @return A UserInfo for {@link com.jcraft.jsch.JSch} to make connections via
	 * SSH
	 */
	@Override
	public UserInfo getUserInfo() {
		return user_info;
	}

	@Override
	public String getUsername() {
		return user;
	}

	@Override
	public String getHost() {
		return host;
	}

	@Override
	public File getKnownHosts() {
		return known_hosts;
	}

	@Override
	public File getPrivateKey() {
		return private_key;
	}
}
