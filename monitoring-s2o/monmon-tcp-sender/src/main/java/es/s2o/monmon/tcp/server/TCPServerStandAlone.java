package es.s2o.monmon.tcp.server;

import java.net.InetSocketAddress;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.ServerChannelFactory;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.logging.InternalLogger;
import org.jboss.netty.logging.Log4JLoggerFactory;

import es.s2o.monmon.tcp.commun.MessagePipelineFactory;

/**
 * @author s2o
 * 
 */
public class TCPServerStandAlone {

	private static final InternalLogger LOGGER = Log4JLoggerFactory.getInstance(TCPServerStandAlone.class);

	private final String host;

	private final int port;

	public int frameSize = 128;

	private DefaultChannelGroup channelGroup;

	private ServerChannelFactory serverFactory;

	/**
	 * @param host
	 * @param port
	 */
	public TCPServerStandAlone(final String host, final int port) {
		this.host = host;
		this.port = port;
	}

	/**
	 * @return
	 */
	public boolean start() {
		LOGGER.info("Starting server");

		serverFactory = new NioServerSocketChannelFactory();
		channelGroup = new DefaultChannelGroup(this + "-channelGroup");

		final ChannelHandler handler = new ServerStandAloneHandler(channelGroup);
		final ChannelPipelineFactory pipelineFactory = new MessagePipelineFactory(handler, frameSize);

		final ServerBootstrap bootstrap = new ServerBootstrap(serverFactory);
		bootstrap.setOption("reuseAddress", true);
		bootstrap.setOption("child.tcpNoDelay", true);
		bootstrap.setOption("child.keepAlive", true);
		bootstrap.setPipelineFactory(pipelineFactory);

		final Channel channel = bootstrap.bind(new InetSocketAddress(host, port));
		if (!channel.isBound()) {
			releaseResources();
			LOGGER.info("Can't start the server");
			return false;
		}

		channelGroup.add(channel);

		LOGGER.info("TCPServerStandAlone:" + host + ", started OK on port:" + port);
		return true;
	}

	/**
	 * 
	 */
	public void releaseResources() {
		if (channelGroup != null) {
			channelGroup.close();
		}
		if (serverFactory != null) {
			serverFactory.releaseExternalResources();
		}
	}

	@Override
	protected void finalize() throws Throwable {
		// releaseResources();
		super.finalize();
	}
}
