package es.s2o.monmon.tcp.client;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import junit.framework.Assert;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.junit.Before;
import org.junit.Test;

public class ClientHandlerTest {

	private ClientHandler clientHandler;

	private ChannelGroup channelGroup;

	@Before
	public void init() {
		channelGroup = new DefaultChannelGroup();
		clientHandler = new ClientHandler(channelGroup);
	}

	@Test
	public void testChannelConnected() {

		final ChannelStateEvent chanelEvent = mock(ChannelStateEvent.class);
		final Channel mockChannel = mock(Channel.class);
		final ChannelFuture mockChannelFuture = mock(ChannelFuture.class);

		when(mockChannel.getCloseFuture()).thenReturn(mockChannelFuture);
		when(chanelEvent.getChannel()).thenReturn(mockChannel);

		final int groupsize = channelGroup.size();

		try {
			clientHandler.channelConnected(null, chanelEvent);
			Assert.assertEquals(groupsize + 1, channelGroup.size());
		}
		catch (final Exception e) {
			fail("Execution error:" + e.getMessage());
		}

	}

}
