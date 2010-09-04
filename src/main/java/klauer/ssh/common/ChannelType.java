package klauer.ssh.common;

import java.util.HashMap;
import java.util.Map;

public enum ChannelType {
	SESSION("session"),
	SHELL("shell"),
	EXEC("exec"),
	X11("x11"),
	AUTH_AGENT_OPEN_SSH("auth-agent@openssh.com"),
	DIRECT_TCPIP("direct-tcpip"),
	FORWARDED_TCPIP("forwarded-tcpip"),
	SFTP("sftp"),
	SUBSYSTEM("subsystem");
	
	private final String channel_type;

	ChannelType(String type) {
		this.channel_type = type;
	}

	private static final Map<String, ChannelType> stringToEnum = new HashMap<String, ChannelType>();
	static {
		for (ChannelType type : values() ) {
			stringToEnum.put(type.toString(), type);
		}
	}

	/**
	 * Returns the ChannelType from the string.
	 * @param type given Enum type for Channel.
	 * @return ChannelType appropriately matching the string passed in, null otherwise.
	 */
	public static ChannelType fromString(String type) {
		return stringToEnum.get(type);
	}

	public String toString() { return this.channel_type; }
	
}
