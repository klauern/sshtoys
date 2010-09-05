package klauer.ssh.common;

import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Nick Klauer <klauer@gmail.com>
 */
public class AuthSettingsTests {

	@Test
	public void authSettingsDontChangeAfterFirstCalled() throws IOException, Exception {
		AuthSettingsFromSource settings = AuthSettingsFromSource.getInstance();
		settings.host = "False Host Name";
		assertEquals(AuthSettingsFromSource.getInstance().host, "klauern.ath.cx");
		assertEquals(settings.host, "klauern.ath.cx");
	}
	
}
