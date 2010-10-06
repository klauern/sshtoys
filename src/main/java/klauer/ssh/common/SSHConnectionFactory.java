package klauer.ssh.common;

import com.jcraft.jsch.JSch;
import java.io.File;

/**
 *
 * @author Nick Klauer <klauer@gmail.com>
 */
public class SSHConnectionFactory {

	AuthEngine authentication_details;
	JSch jsch_session;

	public SSHConnectionFactory() {
	}

}
