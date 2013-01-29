package es.s2o.monmon.tcp.client;

import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.logging.InternalLogger;
import org.jboss.netty.logging.Log4JLoggerFactory;

import es.s2o.monmon.tcp.to.MeasureMessage;
import es.s2o.monmon.tcp.to.TCPProperties;

/**
 * Main class for send messages to a TCP server. It's doesn't care about the server response<br/>
 * Start up a Netty client communication with a tcp server and send {@link MeasureMessage} .<br/>
 * 
 * @author s2o
 * 
 */
public enum TCPSender {
	/**
	 * Enum type to restricts the instantiation of TCPSender to one object (Singleton pattern).
	 */
	INSTANCE;

	private static final InternalLogger LOGGER = Log4JLoggerFactory.getInstance(TCPSender.class);

	private ChannelGroup channelGroup;

	private ChannelFuture channelFuture;

	private ClientConnector clientConnector;

	private TCPProperties tcpProperties;

	private boolean monitoringEnabled = false;

	/**
	 * Send message to server
	 * 
	 * @param message
	 */
	public void sendMessage(final MeasureMessage message) {
		if (monitoringEnabled) {
			if (connected()) {
				channelFuture.getChannel().write(message);
			}
			else {
				LOGGER.error("No active connection found, can't send message:" + message);
			}
		}
	}

	/**
	 * Initialice Properties with the propertie file path (/home/s2o/properties/TCPSender.properties)
	 * 
	 * @param resourceFilePath
	 */
	public void initialice(final String resourceFileNamePath) {
		initialice(HelperProperties.getTCPProperties(resourceFileNamePath));
	}

	/**
	 * Initialice Properties with the properties
	 * 
	 * @param TCPProperties tcpProperties
	 */
	public void initialice(final TCPProperties tcpProperties) {
		this.tcpProperties = tcpProperties;
		monitoringEnabled = HelperProperties.validateProperties(tcpProperties);

		// Initialize connection
		initializeConnetion();

		// start connection to server
		channelFuture = clientConnector.connectToServer();
	}

	/**
	 * Disconnetc client and release resources isMonitoringEnabled
	 */
	public void releaseResources() {
		LOGGER.info("start releaseResources");

		if (channelGroup != null) {
			channelGroup.close();
		}
		if (clientConnector != null) {
			clientConnector.releaseExternalResources();
		}

		LOGGER.info("end releaseResources");
	}

	/**
	 * Test if monitor propertie monitoring.level=1 (enabled)
	 * 
	 * @return
	 */
	public boolean isMonitoringEnabled() {
		return monitoringEnabled;
	}

	/**
	 * Initialize client connetion with tcp server params
	 */
	private void initializeConnetion() {
		LOGGER.info("Initialize TCPSender");

		synchronized (this) {
			if (isMonitoringEnabled()) {
				// Be sure to get clean resources
				releaseResources();
				channelGroup = new DefaultChannelGroup(this + "-channelGroup");

				clientConnector = new ClientConnector(tcpProperties);
				clientConnector.initPipelineFactory(channelGroup);
			}
			else {
				LOGGER.info("Monitoring is not enabled");
			}
		}

		LOGGER.info("TCPSender Initialized OK");
	}

	/**
	 * Return if the channel is connected or not, and if not connected attempts a new connection with the specified
	 * parameters loaded from a properties file.
	 * 
	 * @return
	 */
	private boolean connected() {
		if (channelFuture == null) {
			if (channelGroup == null) {
				// Connection not initialized
				initializeConnetion();
			}

			// No connection found, trying to connect to server
			channelFuture = clientConnector.connectToServer();
		}
		return channelFuture.isSuccess();
	}

}
