/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package es.s2o.monmon.tcp.commun;

import java.nio.charset.Charset;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.Delimiters;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import org.jboss.netty.handler.codec.replay.ReplayingDecoder;

import es.s2o.monmon.tcp.identifiers.Infraestructure;
import es.s2o.monmon.tcp.identifiers.InputChannel;
import es.s2o.monmon.tcp.identifiers.Type;
import es.s2o.monmon.tcp.identifiers.Version;
import es.s2o.monmon.tcp.to.MeasureMessage;

/**
 * Decodes a received {@link ChannelBuffer} into a {@link String}. Please note that this decoder must be used with a
 * proper {@link FrameDecoder} such as {@link DelimiterBasedFrameDecoder} if you are using a stream-based transport such
 * as TCP/IP. A typical setup for a text-based line protocol in a TCP/IP socket would be:
 * 
 * <pre>
 * {@link ChannelPipeline} pipeline = ...;
 * 
 * // Decoders
 * pipeline.addLast("frameDecoder", new {@link DelimiterBasedFrameDecoder}(80, {@link Delimiters#lineDelimiter()}));
 * pipeline.addLast("stringDecoder", new {@link MessageDecoder}(CharsetUtil.UTF_8));
 * 
 * // Encoder
 * pipeline.addLast("stringEncoder", new {@link MessageEncoder}(CharsetUtil.UTF_8));
 * </pre>
 * 
 * and then you can use a {@link String} instead of a {@link ChannelBuffer} as a message:
 * 
 * <pre>
 * void messageReceived({@link ChannelHandlerContext} ctx, {@link MessageEvent} e) {
 *     String msg = (String) e.getMessage();
 *     ch.write("Did you say '" + msg + "'?\n");
 * }
 * </pre>
 * 
 * @apiviz.landmark
 */
@ChannelHandler.Sharable
public class MessageDecoder extends ReplayingDecoder<MessageDecoder.DecodingState> {

	private final Charset charset = Charset.forName("UTF-8");

	private MeasureMessage message;

	private static final String fieldDelimiter = "#";

	// constructors ---------------------------------------------------------------------------------------------------

	public MessageDecoder() {
		reset();
	}

	@Override
	protected Object decode(final ChannelHandlerContext ctx, final Channel channel, final ChannelBuffer buffer,
			final DecodingState state) throws Exception {
		// notice the switch fall-through
		switch (state) {
		case VERSION:
			message.setVersion(Version.fromByte(buffer.readByte()));
			checkpoint(DecodingState.TYPE);
		case TYPE:
			message.setMsgType(Type.fromByte(buffer.readByte()));
			checkpoint(DecodingState.INFRAESTRUCTURE);
		case INFRAESTRUCTURE:
			message.setInfrastructure(Infraestructure.fromByte(buffer.readByte()));
			checkpoint(DecodingState.PAYLOAD_LENGTH);
		case PAYLOAD_LENGTH:
			final int size = buffer.readInt();
			if (size <= 0) {
				throw new Exception("Invalid content size");
			}
			// pre-allocate content buffer
			final byte[] content = new byte[size];
			buffer.readBytes(content);
			final String payload = new String(content, charset);
			convertFromTCP(payload);
			try {
				// return the instance var and reset this decoder state after doing so.
				return message;
			}
			finally {
				reset();
			}
		default:
			throw new Exception("Unknown decoding state: " + state);
		}
	}

	/**
	 * Convert from string (from TCP) to MeasureMessage Object
	 * 
	 * @param message
	 * @return
	 */
	private void convertFromTCP(final String payload) {
		final String[] decoded = payload.split(fieldDelimiter);

		message.setChannel(InputChannel.fromString(decoded[0]));
		message.setSubchannel(decoded[1]);
		message.setLayer(decoded[2]);
		message.setManagedId(decoded[3]);
		final int timestamp = Integer.valueOf(decoded[4]);
		message.setTimestamp(timestamp);
		final short value = Short.valueOf(decoded[5]);
		message.setValue(value);
		message.setInvoker(decoded[6]);
		message.setInvokerVersion(decoded[7]);
		message.setTarget(decoded[8]);
		message.setTargetVersion(decoded[9]);
		message.setRequestProtocol(decoded[10]);
		message.setRetval(decoded[11]);
	}

	private void reset() {
		checkpoint(DecodingState.VERSION);
		message = new MeasureMessage();
	}

	// private classes ------------------------------------------------------------------------------------------------

	public enum DecodingState {

		// constants --------------------------------------------------------------------------------------------------
		VERSION, TYPE, INFRAESTRUCTURE, PAYLOAD_LENGTH,
	}
}
