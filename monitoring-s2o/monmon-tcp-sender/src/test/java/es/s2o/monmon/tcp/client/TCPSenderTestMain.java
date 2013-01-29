package es.s2o.monmon.tcp.client;

import org.jboss.netty.logging.InternalLogger;
import org.jboss.netty.logging.Log4JLoggerFactory;

import es.s2o.monmon.tcp.identifiers.Infraestructure;
import es.s2o.monmon.tcp.identifiers.MsgType;
import es.s2o.monmon.tcp.identifiers.MsgVersion;
import es.s2o.monmon.tcp.to.MeasureMessage;

/**
 * @author s2o
 * 
 */
public class TCPSenderTestMain {

	private static final InternalLogger LOGGER = Log4JLoggerFactory.getInstance(TCPSenderTestMain.class);

	public static void main(final String[] args) throws InterruptedException {

		final TCPSender client = TCPSender.INSTANCE;
		client.initialice(TestTCPUtils.getPropertiesPathFile());
		Thread.sleep(1000);
		LOGGER.info("Client started...");

		System.out.println("inicio");
		final long startTime = System.currentTimeMillis();
		flood(client);

		// Thread.sleep(20000);
		LOGGER.info("volvemos a enviar datos2");
		flood(client);

		LOGGER.info("volvemos a enviar datos3");
		flood(client);

		final long endTime = System.currentTimeMillis();

		final float timeInSeconds = (endTime - startTime) / 1000f;

		LOGGER.info("Send in " + timeInSeconds + "s");

		Thread.sleep(90000);
		client.releaseResources();

	}

	// public static void startServer() {
	// final TCPServerStandAlone server = new TCPServerStandAlone(HelperProperties.getTCPProperties().getHost(),
	// HelperProperties.getTCPProperties().getPort());
	// try {
	// server.start();
	// }
	// catch (final Exception exception) {
	// System.err.print(exception);
	// }
	//
	// }

	private static void flood(final TCPSender client) {
		final MeasureMessage message = initMessage();
		for (long i = 0; i < 100000; i++) {
			// message.setValue(i);
			client.sendMessage(message);
		}
	}

	private static MeasureMessage initMessage() {
		final MeasureMessage message = new MeasureMessage();
		message.setMsgVersion(MsgVersion.VERSION1);
		message.setMsgType(MsgType.TIME);
		message.setInfrastructure(Infraestructure.ABSIS);
		message.setInputChannel("004");
		message.setManagedId("CAX02_0402");
		final long time = System.currentTimeMillis() / 1000L;
		message.setTimestamp((int) time);
		message.setValue((short) 1);
		message.setRequestProtocol("http");
		message.setRetval("ok");
		return message;
	}

	// public static void charToByte() {
	// try {
	// final String str = "2";
	// final byte[] pp = { 67, 65, 88 };
	// final byte[] retus = str.getBytes(Charset.forName("utf-8"));
	//
	// final String text1 = new String(pp, "UTF-8");
	//
	// final byte[] ver = { 0x01 };// VERSION1((byte) 0x01)
	// final String text2 = new String(ver, "UTF-8");
	// final char c = str.charAt(1);
	//
	// final byte bValue = (byte) c;
	// System.out.println("Byte is:=" + bValue + text1);
	// }
	// catch (final Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// }

}
