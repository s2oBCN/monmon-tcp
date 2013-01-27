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
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.handler.codec.frame.DelimiterBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.Delimiters;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

import es.s2o.monmon.tcp.identifiers.Infraestructure;
import es.s2o.monmon.tcp.identifiers.InputChannel;
import es.s2o.monmon.tcp.identifiers.Type;
import es.s2o.monmon.tcp.identifiers.Version;
import es.s2o.monmon.tcp.to.MeasureMessage;

/**
 * Encodes the requested {@link String} into a {@link ChannelBuffer}. A typical setup for a text-based line protocol in
 * a TCP/IP socket would be:
 * 
 * <pre>
 * {@link ChannelPipeline} pipeline = ...;
 * 
 * // Decoders
 * pipeline.addLast("frameDecoder", new {@link DelimiterBasedFrameDecoder}({@link Delimiters#lineDelimiter()}));
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
public class MessageEncoder extends OneToOneEncoder {

	private static final Charset charset = Charset.forName("UTF-8");

	private static final String fieldDelimiter = "#";

	public static MessageEncoder getInstance() {
		return InstanceHolder.INSTANCE;
	}

	@Override
	protected Object encode(final ChannelHandlerContext ctx, final Channel channel, final Object msg) throws Exception {
		Object answer = msg;
		if (msg instanceof MeasureMessage) {
			answer = encodeMessage((MeasureMessage) msg);
		}

		return answer;
	}

	public static ChannelBuffer encodeMessage(final MeasureMessage message) throws IllegalArgumentException {
		if (message.getVersion() == null || message.getVersion() == Version.UNKNOWN) {
			throw new IllegalArgumentException("Message version cannot be null or UNKNOWN");
		}

		if (message.getMsgType() == null || message.getMsgType() == Type.UNKNOWN) {
			throw new IllegalArgumentException("Message type cannot be null or UNKNOWN");
		}

		if (message.getInfrastructure() == null || message.getInfrastructure() == Infraestructure.UNKNOWN) {
			throw new IllegalArgumentException("Message Infraestructure cannot be null or empty");
		}

		if (message.getChannel() == null || message.getChannel() == InputChannel.UNKNOWN) {
			throw new IllegalArgumentException("Message channel cannot be null or empty");
		}

		final String payload = message.getChannel().getValue() + fieldDelimiter + message.getSubchannel()
				+ fieldDelimiter + message.getLayer() + fieldDelimiter + message.getManagedId() + fieldDelimiter
				+ message.getTimestamp() + fieldDelimiter + message.getValue() + fieldDelimiter + message.getInvoker()
				+ fieldDelimiter + message.getInvokerVersion() + fieldDelimiter + message.getTarget() + fieldDelimiter
				+ message.getTargetVersion() + fieldDelimiter + message.getRequestProtocol() + fieldDelimiter
				+ message.getRetval();
		final int payloadLength = payload.length();

		// version(1b) + type(1b) + Infrastructure(1b) + + payload length(4b) + payload(nb)
		final int size = 7 + payloadLength;

		final ChannelBuffer buffer = ChannelBuffers.buffer(size);
		buffer.writeByte(message.getVersion().getValue());
		buffer.writeByte(message.getMsgType().getValue());
		buffer.writeByte(message.getInfrastructure().getValue());
		buffer.writeInt(payloadLength);
		buffer.writeBytes(payload.getBytes(charset));
		return buffer;
	}

	private static final class InstanceHolder {
		private static final MessageEncoder INSTANCE = new MessageEncoder();
	}
}
