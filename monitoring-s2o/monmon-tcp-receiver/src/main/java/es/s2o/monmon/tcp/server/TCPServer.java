package es.s2o.monmon.tcp.server;

import java.net.InetSocketAddress;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.ServerChannelFactory;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

/**
 * This class is used for TCP IP communications with client. It uses Netty tcp server bootstrap for this. Start up a
 * Netty server at a specified port.
 * 
 * @author s2o
 * 
 */
public class TCPServer {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * Sever port listening on
	 */
	@Value("${tcp.port}")
	public int portNumber;

	/**
	 * Pipeline message factory
	 */
	@Autowired
	@Qualifier("measurePipelineFactory")
	public ChannelPipelineFactory pipelineFactory;

	@Autowired
	@Qualifier("defaultChannelGroup")
	private DefaultChannelGroup channelGroup;

	@Autowired
	@Qualifier("nioServerSocketChannelFactory")
	private ServerChannelFactory serverFactory;

	/**
	 * Initialize and startup server.
	 * 
	 */
	public void startServer() {
		logger.info("Starting server");
		logger.debug("Starting server");

		final ServerBootstrap bootstrap = new ServerBootstrap(serverFactory);
		bootstrap.setOption("reuseAddress", true);
		bootstrap.setOption("child.tcpNoDelay", true);
		bootstrap.setOption("child.keepAlive", true);
		bootstrap.setPipelineFactory(pipelineFactory);

		final Channel channel = bootstrap.bind(new InetSocketAddress(portNumber));
		if (!channel.isBound()) {
			stopServer();
			logger.error("Can't start the server");
			// TODO
			// throw exception
		}

		channelGroup.add(channel);

		logger.info("Localhost started OK on port:" + portNumber);
	}

	/**
	 * Stop server and release resources
	 * 
	 */
	public void stopServer() {
		logger.debug("Stopping server");

		if (channelGroup != null) {
			channelGroup.close();
		}
		if (serverFactory != null) {
			serverFactory.releaseExternalResources();
		}

		logger.debug("Server stopped");
	}

	/**
	 * Show listening on port
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TCPServer running on [portNumber=" + portNumber + "]";
	}

}
