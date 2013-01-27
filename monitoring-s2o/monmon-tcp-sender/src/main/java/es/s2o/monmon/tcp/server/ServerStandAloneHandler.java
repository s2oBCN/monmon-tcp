package es.s2o.monmon.tcp.server;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.logging.InternalLogger;
import org.jboss.netty.logging.Log4JLoggerFactory;

import es.s2o.monmon.tcp.to.MeasureMessage;

public class ServerStandAloneHandler extends SimpleChannelUpstreamHandler {

	private static final InternalLogger LOGGER = Log4JLoggerFactory.getInstance(ServerStandAloneHandler.class);

	private final ChannelGroup channelGroup;

	// private static long startTime;
	//
	// private static final String FINAL = String.valueOf(100000 - 1);

	/**
	 * @param channelGroup
	 */
	public ServerStandAloneHandler(final ChannelGroup channelGroup) {
		super();
		this.channelGroup = channelGroup;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jboss.netty.channel.SimpleChannelUpstreamHandler#channelConnected(org.jboss.netty.channel.ChannelHandlerContext
	 * , org.jboss.netty.channel.ChannelStateEvent)
	 */
	@Override
	public void channelConnected(final ChannelHandlerContext ctx, final ChannelStateEvent e) throws Exception {
		LOGGER.debug("channelConnected:" + ctx.getName());

		channelGroup.add(e.getChannel());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jboss.netty.channel.SimpleChannelUpstreamHandler#messageReceived(org.jboss.netty.channel.ChannelHandlerContext
	 * , org.jboss.netty.channel.MessageEvent)
	 */
	@Override
	public void messageReceived(final ChannelHandlerContext ctx, final MessageEvent e) throws Exception {
		final MeasureMessage message = (MeasureMessage) e.getMessage();
		// logger.debug("message:" + message);
		// final String[] decoded = message.split(":");
		// final String id = decoded[decoded.length - 1];
		// if ("0".equals(id)) {
		// startTime = System.currentTimeMillis();
		// }
		// if (FINAL.equals(id)) {
		// final float timeInSeconds = (System.currentTimeMillis() - startTime) / 1000f;
		// logger.info("Total time in " + timeInSeconds + "s");
		// }
		// final MeasureMessage measure = MeasureMessageConverter.toDTO(message);
		// SeverStandAloneAggregator.getInstance().aggregate(measure);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jboss.netty.channel.SimpleChannelUpstreamHandler#exceptionCaught(org.jboss.netty.channel.ChannelHandlerContext
	 * , org.jboss.netty.channel.ExceptionEvent)
	 */
	@Override
	public void exceptionCaught(final ChannelHandlerContext ctx, final ExceptionEvent e) throws Exception {
		LOGGER.error("Errorrr", e.getCause());
		super.exceptionCaught(ctx, e);
	}
}
