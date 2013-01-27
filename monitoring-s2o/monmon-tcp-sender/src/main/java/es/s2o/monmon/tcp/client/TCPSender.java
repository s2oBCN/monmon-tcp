package es.s2o.monmon.tcp.client;

import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.logging.InternalLogger;
import org.jboss.netty.logging.Log4JLoggerFactory;

import es.s2o.monmon.tcp.to.MeasureMessage;

/**
 * This class is used for TCP IP communications with the server. It uses Netty tcp client clientConnector for this.
 * Start up a Netty client communication with a tcp server.
 * 
 * @author s2o
 * 
 */
public class TCPSender {

	private static final InternalLogger LOGGER = Log4JLoggerFactory.getInstance(TCPSender.class);

	private static final TCPSender SENDER = new TCPSender();

	private ChannelGroup channelGroup;

	private ChannelFuture channelFuture;

	private ClientConnector clientConnector;

	/**
	 * Restricts the instantiation of TCPSender to one object (Singleton pattern).
	 * 
	 * @return
	 */
	public static TCPSender getInstance() {
		return SENDER;
	}

	public boolean isMonitoringEnabled() {
		return HelperProperties.getTCPProperties().isMonitoringEnabled();
	}

	/**
	 * Send message to server
	 * 
	 * @param message
	 */
	public void sendMessage(final MeasureMessage message) {
		if (isMonitoringEnabled()) {
			if (connected()) {
				channelFuture.getChannel().write(message);
			}
			else {
				LOGGER.error("No active connection found, can't send message:" + message);
			}
		}
	}

	/**
	 * Disconnetc client and release resources isMonitoringEnabled
	 */
	public void releaseResources() {
		LOGGER.info("Stopping TCPSender");

		if (channelGroup != null) {
			channelGroup.close();
		}
		if (clientConnector != null) {
			clientConnector.releaseExternalResources();
		}

		LOGGER.info("TCPSender stopped OK");
	}

	/**
	 * Be sure to release resources
	 * 
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable {
		releaseResources();
		super.finalize();
	}

	/**
	 * Initialize client with tcp server params
	 */
	private void init() {
		LOGGER.info("Initialize TCPSender");

		if (isMonitoringEnabled()) {
			channelGroup = new DefaultChannelGroup(this + "-channelGroup");

			clientConnector = new ClientConnector();
			clientConnector.initPipelineFactory(channelGroup);
		}
		else {
			LOGGER.info("Monitoring is not enabled");
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
		if (channelFuture == null || !channelFuture.isSuccess() || !channelFuture.getChannel().isConnected()) {
			if (channelGroup == null) {
				// Connection not initialized
				init();
			}

			// No connection found, trying to connect to server
			channelFuture = clientConnector.connectToServer();
		}
		return channelFuture.isSuccess();
	}

}
