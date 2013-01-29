package es.s2o.monmon.tcp.client;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;

import junit.framework.Assert;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.group.ChannelGroup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import es.s2o.monmon.tcp.identifiers.MsgVersion;
import es.s2o.monmon.tcp.to.MeasureMessage;

/**
 * @author s2o
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class TCPSenderTest {

	// @BeforeClass
	// public static void startServer() {
	// final TCPServerStandAlone server = new TCPServerStandAlone("localhost", 9999);
	//
	// if (!server.start()) {
	//
	// System.exit(-1);
	// return; // not really needed...
	// }
	//
	// Runtime.getRuntime().addShutdownHook(new Thread() {
	// @Override
	// public void run() {
	// server.stop();
	// }
	// });
	// }

	@Mock
	private Channel channel;

	@Mock
	private ChannelGroup channelGroup;

	@Mock
	private ClientConnector clientConnector;

	@Mock
	private ChannelFuture mockChannelFuture;

	/**
	 * 
	 */
	@Test
	public void testSendMessage() {
		final TCPSender sender = TCPSender.INSTANCE;

		sender.initialice(TestTCPUtils.getPropertiesPathFile());

		when(mockChannelFuture.isSuccess()).thenReturn(true);
		when(mockChannelFuture.getChannel()).thenReturn(channel);

		try {
			Field privateField;
			privateField = TCPSender.class.getDeclaredField("channelFuture");
			privateField.setAccessible(true);
			privateField.set(sender, mockChannelFuture);
		}
		catch (final Exception e) {
			fail("Catch Exception: " + e.getMessage());
		}

		final MeasureMessage message = initMessage();
		sender.sendMessage(message);
		verify(channel).write(message);
	}

	/**
	 * 
	 */
	@Test
	public void testInitialice() {
		final TCPSender sender = TCPSender.INSTANCE;
		sender.initialice(TestTCPUtils.getPropertiesPathFile());
		Assert.assertTrue(sender.isMonitoringEnabled());
	}

	/**
	 * 
	 */
	@Test
	public void testReleaseResources() {
		final TCPSender sender = TCPSender.INSTANCE;

		try {
			Field privateField;
			privateField = TCPSender.class.getDeclaredField("channelGroup");
			privateField.setAccessible(true);
			privateField.set(sender, channelGroup);

			privateField = TCPSender.class.getDeclaredField("clientConnector");
			privateField.setAccessible(true);
			privateField.set(sender, clientConnector);
		}
		catch (final Exception e) {
			fail("Exception in reflexion");
		}

		sender.releaseResources();

		verify(channelGroup).close();
		verify(clientConnector).releaseExternalResources();
	}

	/**
	 * @return
	 */
	private MeasureMessage initMessage() {
		final MeasureMessage message = new MeasureMessage();
		message.setMsgVersion(MsgVersion.VERSION1);
		message.setManagedId("server-managed");

		return message;
	}

}
