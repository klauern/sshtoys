package klauer.ssh.interactive;

import java.io.IOException;
import klauer.ssh.common.AuthSettingsFromSource;
import java.io.File;
import klauer.ssh.common.ScriptRunner;

public class RunningAScript {

	public static void main(String[] args) throws IOException {

		AuthSettingsFromSource set = AuthSettingsFromSource.getInstance();

		ScriptRunner.runScript(new File(set.script_to_load));
		
	}
}
