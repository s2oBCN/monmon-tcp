package es.s2o.monmon.tcp.client;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.jboss.netty.logging.InternalLogger;
import org.jboss.netty.logging.Log4JLoggerFactory;

import es.s2o.monmon.tcp.commun.TCPException;
import es.s2o.monmon.tcp.to.TCPProperties;

/**
 * Helper properties for load file properties
 * @author s2o
 * 
 */
public class HelperProperties {
	private static final InternalLogger LOGGER = Log4JLoggerFactory.getInstance(HelperProperties.class);

	/**
	 * Initialice Properties with the propertie file path (/home/s2o/properties/TCPSender.properties)
	 * 
	 * @param resourceFilePath
	 */
	public static TCPProperties getTCPProperties(final String resourceFileNamePath) {
		LOGGER.info("start getTCPProperties");

		final Properties properties = getPropertiesFromResource(resourceFileNamePath);
		final TCPProperties tcpProperties = new TCPProperties();
		tcpProperties.setHost(properties.getProperty("tcp.host"));
		tcpProperties.setPort(Integer.parseInt(properties.getProperty("tcp.port")));
		tcpProperties.setFieldDelimiter(properties.getProperty("tcp.fieldDelimiter"));
		final int level = Integer.parseInt(properties.getProperty("monitoring.level"));
		if (level > 0) {
			LOGGER.info("Monitoring enabled!:" + tcpProperties);
			tcpProperties.setMonitoringEnabled(true);
		}
		else {
			LOGGER.info("Monitoring NOT enabled!:" + tcpProperties);
			tcpProperties.setMonitoringEnabled(false);
		}

		LOGGER.info("end getTCPProperties. tcpProperties:" + tcpProperties);
		return tcpProperties;
	}

	/**
	 * @param tcpProperties
	 * @return
	 */
	public static boolean validateProperties(final TCPProperties tcpProperties) {
		boolean monitoringEnabled = tcpProperties.isMonitoringEnabled();

		final String host = tcpProperties.getHost();
		final int port = tcpProperties.getPort();
		if (host == null || port < 500 || host.length() < 5) {
			monitoringEnabled = false;
			LOGGER.error("TCP properties not correct (host == null || port < 500 || host.length() < 5:" + tcpProperties);
		}

		return monitoringEnabled;
	}

	/**
	 * Load the properties of a specified configuration file as a resource in the classpath. It can be Within a JAR
	 * 
	 * @param resource
	 * @return
	 * @see ClassLoader#getResource(String)
	 * @see Properties
	 */
	private static Properties getPropertiesFromResource(final String resource) {

		LOGGER.info("Getting properties from resource:" + resource);

		final Properties prop = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(resource);
			prop.load(inputStream);
		}
		catch (final Exception e) {
			final TCPException tcpException = new TCPException(e);
			throw tcpException;
		}

		finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				}
				catch (final IOException e) {
					final TCPException tcpException = new TCPException(e);
					throw tcpException;
				}
			}
		}

		LOGGER.info("Properties loaded Ok");
		return prop;
	}

}
