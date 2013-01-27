package es.s2o.monmon.tcp.client;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import es.s2o.monmon.tcp.identifiers.Version;
import es.s2o.monmon.tcp.to.MeasureMessage;

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

	@Before
	public void init() {
		System.out.println("beforeClass");
	}

	//
	// @Test
	// public void testInit() {
	// sender.init();
	// Assert.assertTrue(sender.isConnected());
	// }
	//
	// @Test
	// public void testNotInit() {
	// Assert.assertTrue(sender.isConnected() == false);
	// }

	@Test
	public void testStop() {
		final TCPSender sender = TCPSender.getInstance();
		sender.releaseResources();
		Assert.assertNotNull(sender);
		assertEquals(123, 123);
	}

	@Test
	public void testTime() {
		final long now = System.currentTimeMillis();
		final Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(now);
		System.out.println("Seconds to Date: "
				+ convertSecondsToDate(System.currentTimeMillis() / 1000L, "dd/MM/yyyy hh:mm:ss.SSS"));
	}

	/**
	 * Return date in specified format.
	 * @param seconds Date in seconds
	 * @param dateFormat Date format
	 * @return String representing date in specified format
	 */
	public static String convertSecondsToDate(final long seconds, final String dateFormat) {
		final long dateInMillis = seconds * 1000;

		// Create a DateFormatter object for displaying date in specified format.
		final DateFormat formatter = new SimpleDateFormat(dateFormat);

		final Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(dateInMillis);

		return formatter.format(calendar.getTime());
	}

	@Test
	public void testSendMessage() {
		final TCPSender sender = TCPSender.getInstance();
		// final SeverStandAloneAggregator serverAggregator = SeverStandAloneAggregator.getInstance();
		// final int size = serverAggregator.listSize();
		sender.sendMessage(initMessage());
		// try {
		// Thread.currentThread().sleep(1000);
		// }
		// catch (final InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// Assert.assertEquals(size + 1, serverAggregator.listSize());
		// final ChannelStateEvent chanelEvent = mock(ChannelStateEvent.class);
		// final Channel mockChannel = mock(Channel.class);
		// final ChannelFuture mockChannelFuture = mock(ChannelFuture.class);
		// when(mockChannel.getCloseFuture()).thenReturn(mockChannelFuture);
		// when(chanelEvent.getChannel()).thenReturn(mockChannel);
		// try {
		// // clientHandler.channelConnected(null, chanelEvent);
		// // clientHandler.sendMessage("test");
		// verify(mockChannel).write("test" + NettyConstants.MESSAGE_DELIMITER);
		// }
		// catch (final Exception e) {
		// fail("Execution error:" + e.getMessage());
		// }
	}

	private MeasureMessage initMessage() {
		final MeasureMessage message = new MeasureMessage();
		message.setVersion(Version.VERSION1);
		message.setManagedId("server-managed");

		return message;
	}

}
