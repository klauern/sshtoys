package klauer.ssh.common;

import com.google.common.io.Files;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class for creating a session and running a script.
 *
 * @author Nick Klauer <klauer@gmail.com>
 */
public class ScriptRunner {

	public static JSch jsch = new JSch();
	public static AuthSettingsFromSource settings;

	public static boolean runScript(File scriptToRun) {

		try {
			settings = AuthSettingsFromSource.getInstance();
			runScript(settings.host, settings.username, new File(settings.private_key_file),
							new File(settings.known_hosts_file), new File(settings.script_to_load));

		} catch (IOException ex) {
			return false;
		} catch (JSchException jschex) {
			return false;
		}
		return true;
	}

	public static void cycleThroughInput(InputStream in, Channel channel) throws IOException {

		byte[] tmp = new byte[1024];
		System.out.println("cycleThroughinput");
		while (true) {
			while (in.available() > 0) {
				int i = in.read(tmp, 0, 1024);
				if (i < 0) {
					break;
				}
				System.out.print(new String(tmp, 0, i));
			}
			if (channel.isClosed()) {
				System.out.println("exit-status: " + channel.getExitStatus());
				break;
			}
			try {
			} catch (Exception ee) {
			}
		}

	}

	public static void runScript(String host, String user, File private_key, File known_hosts, File script_to_run) throws JSchException, IOException {
		JSch jsch = new JSch();
		jsch.addIdentity(private_key.getCanonicalPath());
		jsch.setKnownHosts(known_hosts.getCanonicalPath());

		Session session = jsch.getSession(user, host);
		session.connect();

		ChannelExec ce = (ChannelExec) session.openChannel(ChannelType.EXEC.toString());

		InputStream in = ce.getInputStream();
		byte[] script = Files.toByteArray(script_to_run);
		
		ce.setCommand(script);
		ce.setErrStream(System.err);

		ce.connect();

		cycleThroughInput(in, ce);

		ce.disconnect();
		session.disconnect();
	}
}
