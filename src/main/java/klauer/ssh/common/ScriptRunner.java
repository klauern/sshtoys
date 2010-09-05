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

	/**
	 * Method for cycling through a channel's input stream upon calling a script.
	 * @param in {@link InputStream} of the remote's {@code stdin} that will be processed
	 * @param channel {@link Channel} to determine if channel is closed and what ocurred during read.
	 * @throws IOException
	 */
	public static void cycleThroughInput(InputStream in, Channel channel) throws IOException {

		byte[] tmp = new byte[1024];
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

	/**
	 * Run an arbitrary script for a given remote host.
	 * @param host hostname, IP address, or server to connect to.
	 * @param user username to log in as to execute script.
	 * @param private_key private key file necessary to log in without password.  This key
	 * is assumed to be passwordless as well, meaning it can be read in and used for
	 * remote connections without being prompted for the passphrase.
	 * @param known_hosts File of known_hosts to connect to, for verifying the remote
	 * server is who it says it is.
	 * @param script_to_run File of the script to run.  This can be any sort of
	 * text file or whatnot that simply has a number of commands in it, and doesn't
	 * necessarily need to be of a {@code .sh} filetype or be prepended with
	 * {@code #!/bin/sh}.
	 * @throws JSchException
	 * @throws IOException
	 */
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
