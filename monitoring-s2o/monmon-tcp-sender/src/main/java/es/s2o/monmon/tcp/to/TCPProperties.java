package es.s2o.monmon.tcp.to;

public class TCPProperties {

	private String host;

	private int port;

	private String fieldDelimiter;

	/**
	 * By default monitoringEnabled = false
	 */
	private boolean monitoringEnabled = false;

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

	public String getFieldDelimiter() {
		return fieldDelimiter;
	}

	public void setFieldDelimiter(final String fieldDelimiter) {
		this.fieldDelimiter = fieldDelimiter;
	}

	public boolean isMonitoringEnabled() {
		return monitoringEnabled;
	}

	public void setMonitoringEnabled(final boolean monitoringEnabled) {
		this.monitoringEnabled = monitoringEnabled;
	}

	@Override
	public String toString() {
		return "host:" + host + ", port:" + port + ", fieldDelimiter:" + fieldDelimiter + ", monitoringEnabled:"
				+ monitoringEnabled;
	}
}
