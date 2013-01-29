package es.s2o.monmon.tcp.client;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.logging.InternalLogger;
import org.jboss.netty.logging.Log4JLoggerFactory;

/**
 * This class is used for handle the communication with TCP Server
 * 
 * @author s2o
 * 
 */
public class ClientHandler extends SimpleChannelUpstreamHandler {

	private static final InternalLogger LOGGER = Log4JLoggerFactory.getInstance(ClientHandler.class);

	private final ChannelGroup channelGroup;

	/**
	 * @param channelGroup
	 */
	public ClientHandler(final ChannelGroup channelGroup) {
		super();
		this.channelGroup = channelGroup;
	}

	/**
	 * Add channel to group so later we will close the group
	 * 
	 * @see org.jboss.netty.channel.SimpleChannelUpstreamHandler#channelConnected(org.jboss.netty.channel.ChannelHandlerContext
	 * , org.jboss.netty.channel.ChannelStateEvent)
	 */
	@Override
	public void channelConnected(final ChannelHandlerContext ctx, final ChannelStateEvent channelEvent)
			throws Exception {
		LOGGER.debug("Channel connected OK");

		// Add channel to group so later we will close the group
		channelGroup.add(channelEvent.getChannel());
	}

	/**
	 * loggin errors
	 * 
	 * @see org.jboss.netty.channel.SimpleChannelUpstreamHandler#exceptionCaught(org.jboss.netty.channel.ChannelHandlerContext
	 * , org.jboss.netty.channel.ExceptionEvent)
	 */
	@Override
	public void exceptionCaught(final ChannelHandlerContext ctx, final ExceptionEvent chanelEvent) throws Exception {
		LOGGER.error("Communication error. No connection found");
	}
}
