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
import java.io.File;
import java.io.FileInputStream;

public class RunningAScript {

	public static void main(String[] args) throws IOException {

		try {
			JSch jsch = new JSch();
			NickUserInfo nui = new NickUserInfo();
			jsch.addIdentity(nui.private_key_file);
			jsch.setKnownHosts(nui.known_hosts_file);

			Session session = jsch.getSession(nui.username,
							nui.host);
			session.connect();
			ChannelExec channel = (ChannelExec) session.openChannel(ChannelTypes.EXEC.toString());


			InputStream in = channel.getInputStream();

			File scriptfile = new File(nui.script_to_load);
			FileInputStream fis = new FileInputStream(scriptfile);

			byte[] commandbytes = new byte[(int) scriptfile.length()];
			// Read in the bytes
			int offset = 0;
			int numRead = 0;
			while (offset < commandbytes.length && (numRead = fis.read(commandbytes, offset, commandbytes.length - offset)) >= 0) {
				offset += numRead;
			}

			channel.setCommand(commandbytes);
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
