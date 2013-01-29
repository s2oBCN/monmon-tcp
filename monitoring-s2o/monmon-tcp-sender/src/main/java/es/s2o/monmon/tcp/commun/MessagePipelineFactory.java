package es.s2o.monmon.tcp.commun;

import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.logging.InternalLogger;
import org.jboss.netty.logging.Log4JLoggerFactory;

/**
 * This class is used to get the pipeline for the channel
 * 
 * @author s2o
 * 
 */
public class MessagePipelineFactory implements ChannelPipelineFactory {

	private static final InternalLogger LOGGER = Log4JLoggerFactory.getInstance(MessagePipelineFactory.class);

	private transient final ChannelHandler handler;

	/**
	 * @param handler
	 */
	public MessagePipelineFactory(final ChannelHandler handler) {
		this.handler = handler;
	}

	/**
	 * Set encoder, decoder and handler
	 * 
	 * @see org.jboss.netty.channel.ChannelPipelineFactory#getPipeline()
	 */
	public ChannelPipeline getPipeline() throws Exception {
		LOGGER.debug("getting pipeline from factory");

		final ChannelPipeline pipeline = Channels.pipeline();
		pipeline.addLast("encoder", MessageEncoder.getInstance());
		pipeline.addLast("decoder", new MessageDecoder());
		pipeline.addLast("handler", handler);

		LOGGER.debug("pipeline returned ok");
		return pipeline;
	}
}
