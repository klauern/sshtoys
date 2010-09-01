package klauer.ssh.interactive;

import java.io.IOException;
import java.io.InputStream;

import klauer.ssh.common.ChannelTypes;
import klauer.ssh.common.NickUserInfo;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class RunningAScript {

	public static void main(String[] args) throws IOException {

		try {
			JSch jsch = new JSch();
			jsch.addIdentity(NickUserInfo.private_key_file);
			jsch.setKnownHosts(NickUserInfo.known_hosts_file);

			Session session = jsch.getSession(NickUserInfo.username,
					NickUserInfo.host);
			session.connect();
			ChannelExec channel = (ChannelExec) session
					.openChannel(ChannelTypes.EXEC.toString());
			InputStream in = channel.getInputStream();

			channel.connect();
			
			channel.setCommand("cal");
			channel.setErrStream(System.out);

			cycleThroughInput(in, channel);
			
			channel.setCommand("say \"Hello World!\"");
			
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
		while (true) {
			while (in.available() > 0) {
				int i = in.read(tmp, 0, 1024);
				if (i < 0)
					break;
				System.out.print(new String(tmp, 0, i));
			}
			if (channel.isClosed()) {
				System.out.println("exit-status: " + channel.getExitStatus());
				break;
			}
			try {
				Thread.sleep(1000);
			} catch (Exception ee) {
			}
		}

	}

}
