package es.s2o.monmon.tcp.server;

import org.jboss.netty.channel.ChannelHandler.Sharable;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.channel.group.ChannelGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import es.s2o.monmon.tcp.to.MeasureMessage;

/**
 * Handle the server communication with the clients. Receive messages
 * 
 * @author s2o
 * 
 */
@Sharable
public class MeasureServerHandler extends SimpleChannelUpstreamHandler {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final ChannelGroup channelGroup;

	/**
	 * Measure Message agregator service
	 */
	@Autowired
	public MeasureAggregatorService agregator;

	/**
	 * @param channelGroup
	 */
	public MeasureServerHandler(final ChannelGroup channelGroup) {
		this.channelGroup = channelGroup;
	}

	/**
	 * Add channel to channelGroup, thinking in release resources at shutdown
	 * 
	 * @see org.jboss.netty.channel.SimpleChannelUpstreamHandler#channelConnected(org.jboss.netty.channel.ChannelHandlerContext
	 * , org.jboss.netty.channel.ChannelStateEvent)
	 */
	@Override
	public void channelConnected(final ChannelHandlerContext ctx, final ChannelStateEvent e) throws Exception {
		channelGroup.add(e.getChannel());
	}

	/**
	 * Meesage received -> agregate message
	 * 
	 * @see org.jboss.netty.channel.SimpleChannelUpstreamHandler#messageReceived(org.jboss.netty.channel.ChannelHandlerContext
	 * , org.jboss.netty.channel.MessageEvent)
	 */
	@Override
	public void messageReceived(final ChannelHandlerContext ctx, final MessageEvent e) throws Exception {
		// logger.debug("messageReceived start");
		final MeasureMessage message = (MeasureMessage) e.getMessage();
		agregator.aggregate(message);
	}

	/**
	 * Log exception and super()
	 * 
	 * @see org.jboss.netty.channel.SimpleChannelUpstreamHandler#exceptionCaught(org.jboss.netty.channel.ChannelHandlerContext
	 * , org.jboss.netty.channel.ExceptionEvent)
	 */
	@Override
	public void exceptionCaught(final ChannelHandlerContext ctx, final ExceptionEvent e) throws Exception {
		logger.error("Error in server", e.getCause());
		super.exceptionCaught(ctx, e);
	}

}
