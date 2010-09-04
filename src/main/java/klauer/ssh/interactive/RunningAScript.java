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
import klauer.ssh.common.ScriptRunner;

public class RunningAScript {

	public static void main(String[] args) throws IOException {

		AuthSettingsFromSource set = AuthSettingsFromSource.getInstance();

		ScriptRunner.runScript(new File(set.script_to_load));
		
	}
}
