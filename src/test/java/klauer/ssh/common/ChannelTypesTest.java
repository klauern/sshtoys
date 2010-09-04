package klauer.ssh.common;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Nick Klauer <klauer@gmail.com>
 */
public class ChannelTypesTest {

	@Test
	public void testValueToEnumConversion() {
		assertEquals(ChannelType.SHELL, ChannelType.fromString("shell"));
		assertEquals(ChannelType.EXEC, ChannelType.fromString("exec"));
		assertEquals(ChannelType.AUTH_AGENT_OPEN_SSH, ChannelType.fromString("auth-agent@openssh.com"));
	}

	@Test
	public void testValueOf() {
		assertEquals("shell", ChannelType.SHELL.toString());
	}
}
