package es.s2o.monmon.tcp.client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.jboss.netty.logging.InternalLogger;
import org.jboss.netty.logging.Log4JLoggerFactory;

import es.s2o.monmon.tcp.to.TCPProperties;

/**
 * Helper properties for load file properties
 * @author s2o
 * 
 */
public class HelperProperties {

	private static final InternalLogger LOGGER = Log4JLoggerFactory.getInstance(HelperProperties.class);

	private static final TCPProperties tcpProperties = new TCPProperties();

	/**
	 * Initialice Properties with the propertie file path (/home/s2o/properties/TCPSender.properties)
	 * 
	 * @param resourceFilePath
	 */
	public static void initialice(final String resourceFileNamePath) {
		final Properties properties = getPropertiesFromResource(resourceFileNamePath);
		tcpProperties.setHost(properties.getProperty("tcp.host"));
		tcpProperties.setPort(Integer.parseInt(properties.getProperty("tcp.port")));
		tcpProperties.setFrameSize(Integer.parseInt(properties.getProperty("tcp.frameSize")));
		tcpProperties.setFieldDelimiter(properties.getProperty("tcp.fieldDelimiter"));
		tcpProperties.setMessageDelimiter(properties.getProperty("tcp.messageDelimiter"));
		final int level = Integer.parseInt(properties.getProperty("monitoring.level"));
		if (level > 0) {
			LOGGER.info("Monitoring enabled!:" + tcpProperties);
			tcpProperties.setMonitoringEnabled(true);
		}
		else {
			LOGGER.info("Monitoring NOT enabled!:" + tcpProperties);
			tcpProperties.setMonitoringEnabled(false);
		}
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

		final Properties properties = new Properties();

		final Properties prop = new Properties();
		InputStream inputStream = null;
		try {
			try {
				inputStream = new FileInputStream(resource);
			}
			catch (final FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				prop.load(inputStream);
			}
			catch (final IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				}
				catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}

		LOGGER.info("Properties loaded Ok");
		return prop;
	}

	/**
	 * Tranform properties from file to a specific dto
	 * 
	 * @param resource
	 * @return
	 */
	public static TCPProperties getTCPProperties() {
		return tcpProperties;
	}
}
