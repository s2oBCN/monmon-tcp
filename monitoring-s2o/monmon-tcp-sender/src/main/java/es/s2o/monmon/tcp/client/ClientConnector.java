package es.s2o.monmon.tcp.client;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.logging.InternalLogger;
import org.jboss.netty.logging.Log4JLoggerFactory;

import es.s2o.monmon.tcp.commun.MessagePipelineFactory;
import es.s2o.monmon.tcp.to.TCPProperties;

/**
 * A helper class which creates a new client-side {@link Channel} and makes a connection attempt and manages the
 * properties connection
 * 
 * @author s2o
 * 
 */
public class ClientConnector extends ClientBootstrap {

	private static final InternalLogger LOGGER = Log4JLoggerFactory.getInstance(ClientConnector.class);

	/**
	 * Properties loaded from filesystem
	 */
	private final TCPProperties tcpProperties = HelperProperties.getTCPProperties();

	/**
	 * Constructor that creates a new {@link NioClientSocketChannelFactory} which uses
	 * {@link Executors#newCachedThreadPool()} for the worker and boss executors.
	 */
	public ClientConnector() {
		super(new NioClientSocketChannelFactory());
	}

	/**
	 * Initialize parameters of connection
	 * 
	 * @param channelGroup
	 */
	public void initPipelineFactory(final ChannelGroup channelGroup) {
		LOGGER.debug("Initialize clientconnection");

		final ClientHandler handler;
		handler = new ClientHandler(channelGroup);
		final ChannelPipelineFactory pipelineFactory = new MessagePipelineFactory(handler, tcpProperties.getFrameSize());
		setPipelineFactory(pipelineFactory);

		setOption("reuseAddress", true);
		setOption("tcpNoDelay", true);
		setOption("keepAlive", true);

		LOGGER.debug("Initialized clientconnection OK");
	}

	/**
	 * Attempts a new connection, so the return is a ChannelFuture. All I/O operations in Netty are asynchronous. It
	 * means any I/O calls will return immediately with no guarantee that the requested I/O operation has been completed
	 * at the end of the call. Instead, you will be returned with a {@link ChannelFuture} instance which gives you the
	 * information about the result or status of the I/O operation.
	 * 
	 * @return
	 */
	public ChannelFuture connectToServer() {
		LOGGER.debug("No connection found, trying to connect to server");

		final ChannelFuture channelFuture = connect(new InetSocketAddress(tcpProperties.getHost(),
				tcpProperties.getPort()));
		channelFuture.getChannel().setReadable(false);
		channelFuture.awaitUninterruptibly();

		LOGGER.debug("Connection initialized");
		return channelFuture;
	}
}
