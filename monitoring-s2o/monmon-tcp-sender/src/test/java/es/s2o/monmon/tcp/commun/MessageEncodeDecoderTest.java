package es.s2o.monmon.tcp.commun;

import static org.junit.Assert.fail;
import junit.framework.Assert;

import org.jboss.netty.buffer.ChannelBuffer;
import org.junit.Test;

import es.s2o.monmon.tcp.identifiers.Infraestructure;
import es.s2o.monmon.tcp.identifiers.MsgType;
import es.s2o.monmon.tcp.identifiers.MsgVersion;
import es.s2o.monmon.tcp.to.MeasureMessage;

public class MessageEncodeDecoderTest {

	/**
	 * This test is important to be sure keep coherence between coding and decoding
	 */
	@Test
	public void testMessageDecoderDecode() {
		final MessageEncoder encoder = MessageEncoder.getInstance();

		final MessageDecoder decoder = new MessageDecoder();
		try {
			// Initial message
			final MeasureMessage message = initMessage();

			// Encede message
			final ChannelBuffer buffer = (ChannelBuffer) encoder.encode(null, null, message);

			// Decode message
			final MeasureMessage actual = (MeasureMessage) decoder.decode(null, null, buffer,
					MessageDecoder.DecodingState.VERSION);

			// Assert it's the initial message
			Assert.assertEquals(message.toString(), actual.toString());
		}
		catch (final Exception e) {
			fail("Catch Exception: " + e.getMessage());
		}
	}

	private static MeasureMessage initMessage() {
		final MeasureMessage message = new MeasureMessage();
		message.setMsgVersion(MsgVersion.VERSION1);
		message.setMsgType(MsgType.TIME);
		message.setInfrastructure(Infraestructure.ABSIS);
		message.setInputChannel("004");
		message.setSubchannel("010");
		message.setSubsubchannel("000");
		message.setManagedId("CAX02_0402");
		final long time = System.currentTimeMillis() / 1000L;
		message.setTimestamp((int) time);
		message.setValue((short) 1);
		message.setRequestProtocol("http");
		message.setRetval("ok");
		return message;
	}
}
