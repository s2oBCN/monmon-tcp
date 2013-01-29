package es.s2o.monmon.tcp.commun;

import static org.junit.Assert.fail;
import junit.framework.Assert;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelUpstreamHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MessagePipelineFactoryTest {

	@Mock
	private ChannelUpstreamHandler handler;

	@Test
	public void testGetPipeline() {
		try {
			final MessagePipelineFactory messagePipelineFactory = new MessagePipelineFactory(handler);
			final ChannelPipeline channelPipeline = messagePipelineFactory.getPipeline();
			Assert.assertTrue("verify handler", channelPipeline.removeLast() instanceof ChannelUpstreamHandler);
			Assert.assertTrue("verify decoder", channelPipeline.removeLast() instanceof MessageDecoder);
			Assert.assertTrue("verify encoder", channelPipeline.removeLast() instanceof MessageEncoder);
		}
		catch (final Exception e) {
			fail("Catch Exception: " + e.getMessage());
		}
	}
}
