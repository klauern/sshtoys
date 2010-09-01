package klauer.ssh.common;

public enum ChannelTypes {
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
	
	ChannelTypes(String type) {
		this.channel_type = type;
	}
	
	public String toString() { return this.channel_type; }
	
}
