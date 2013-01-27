package es.s2o.monmon.tcp.to;

public class TCPProperties {

	private String host;

	private int port;

	private int frameSize;

	private String fieldDelimiter;

	private String messageDelimiter;

	private boolean monitoringEnabled;

	public String getHost() {
		return host;
	}

	public void setHost(final String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(final int port) {
		this.port = port;
	}

	public int getFrameSize() {
		return frameSize;
	}

	public void setFrameSize(final int frameSize) {
		this.frameSize = frameSize;
	}

	public String getFieldDelimiter() {
		return fieldDelimiter;
	}

	public void setFieldDelimiter(final String fieldDelimiter) {
		this.fieldDelimiter = fieldDelimiter;
	}

	public String getMessageDelimiter() {
		return messageDelimiter;
	}

	public void setMessageDelimiter(final String messageDelimiter) {
		this.messageDelimiter = messageDelimiter;
	}

	public boolean isMonitoringEnabled() {
		return monitoringEnabled;
	}

	public void setMonitoringEnabled(final boolean monitoringEnabled) {
		this.monitoringEnabled = monitoringEnabled;
	}

	@Override
	public String toString() {
		return "host:" + host + ", port:" + port + ", frameSize:" + frameSize + ", fieldDelimiter:" + fieldDelimiter
				+ ", messageDelimiter:" + messageDelimiter + ", monitoringEnabled:" + monitoringEnabled;
	}
}
