package es.s2o.monmon.tcp.client;

import java.net.URL;

public class TestTCPUtils {

	/**
	 * @return
	 */
	public static String getPropertiesPathFile() {
		final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		final URL resource = classLoader.getResource("");
		return resource.getPath() + "TCPSender.properties";
	}
}
