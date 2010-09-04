package klauer.ssh.interactive;

import com.google.common.io.Files;
import java.io.IOException;
import java.io.InputStream;

import klauer.ssh.common.ChannelType;
import klauer.ssh.common.AuthSettingsFromSource;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import java.io.File;
import java.io.FileInputStream;

public class RunningAScript {

	public static void main(String[] args) throws IOException {

		try {
			JSch jsch = new JSch();
			AuthSettingsFromSource nui = new AuthSettingsFromSource();
			jsch.addIdentity(nui.private_key_file);
			jsch.setKnownHosts(nui.known_hosts_file);

			Session session = jsch.getSession(nui.username,
							nui.host);
			session.connect();
			ChannelExec channel = (ChannelExec) session.openChannel(ChannelType.EXEC.toString());

			InputStream in = channel.getInputStream();


			channel.setCommand(Files.toByteArray(new File(nui.script_to_load)));
			channel.setErrStream(System.out);

			channel.connect();

			cycleThroughInput(in, channel);

			channel.setCommand("say \"Hello World\"");

			cycleThroughInput(in, channel);

			channel.disconnect();
			session.disconnect();

		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
}
