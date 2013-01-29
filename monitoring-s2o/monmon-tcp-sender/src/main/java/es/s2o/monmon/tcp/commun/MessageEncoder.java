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

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;
import org.jboss.netty.util.CharsetUtil;

import es.s2o.monmon.tcp.identifiers.Infraestructure;
import es.s2o.monmon.tcp.identifiers.MsgType;
import es.s2o.monmon.tcp.identifiers.MsgVersion;
import es.s2o.monmon.tcp.to.MeasureMessage;

/**
 * Encodes the requested {@link MeasureMessage} into a {@link ChannelBuffer}. <br/>
 * It's a combination of fixed length and fieldDelimiter
 */
@ChannelHandler.Sharable
public class MessageEncoder extends OneToOneEncoder {

	private static final String fieldDelimiter = "#";

	/**
	 * @return
	 */
	public static MessageEncoder getInstance() {
		return InstanceHolder.INSTANCE;
	}

	/**
	 * 
	 * 
	 * @see org.jboss.netty.handler.codec.oneone.OneToOneEncoder#encode(org.jboss.netty.channel.ChannelHandlerContext,
	 * org.jboss.netty.channel.Channel, java.lang.Object)
	 */
	@Override
	protected Object encode(final ChannelHandlerContext ctx, final Channel channel, final Object msg) throws Exception {
		Object answer = msg;
		if (msg instanceof MeasureMessage) {
			answer = encodeMessage((MeasureMessage) msg);
		}

		return answer;
	}

	/**
	 * Verify and encode message. Not nullabled values: version, MsgType, Infrastructure, Channel
	 * 
	 * @param message
	 * @return
	 * @throws IllegalArgumentException
	 */
	private static ChannelBuffer encodeMessage(final MeasureMessage message) throws IllegalArgumentException {
		if (message.getMsgVersion() == null || message.getMsgVersion() == MsgVersion.UNKNOWN) {
			throw new IllegalArgumentException("Message version cannot be null or UNKNOWN");
		}

		if (message.getMsgType() == null || message.getMsgType() == MsgType.UNKNOWN) {
			throw new IllegalArgumentException("Message type cannot be null or UNKNOWN");
		}

		if (message.getInfrastructure() == null || message.getInfrastructure() == Infraestructure.UNKNOWN) {
			throw new IllegalArgumentException("Message Infraestructure cannot be null or empty");
		}

		if (message.getInputChannel() == null || message.getInputChannel().length() < 3) {
			throw new IllegalArgumentException("Message channel cannot be null or empty");
		}

		final String payload = message.getInputChannel() + fieldDelimiter + message.getSubchannel() + fieldDelimiter
				+ message.getSubsubchannel() + fieldDelimiter + message.getLayer() + fieldDelimiter
				+ message.getManagedId() + fieldDelimiter + message.getTimestamp() + fieldDelimiter
				+ message.getValue() + fieldDelimiter + message.getInvoker() + fieldDelimiter
				+ message.getInvokerVersion() + fieldDelimiter + message.getTarget() + fieldDelimiter
				+ message.getTargetVersion() + fieldDelimiter + message.getRequestProtocol() + fieldDelimiter
				+ message.getRetval();
		final int payloadLength = payload.length();

		// version(1b) + type(1b) + Infrastructure(1b) + + payload length(4b) + payload(nb)
		final int size = 7 + payloadLength;

		final ChannelBuffer buffer = ChannelBuffers.buffer(size);
		buffer.writeByte(message.getMsgVersion().getValue());
		buffer.writeByte(message.getMsgType().getValue());
		buffer.writeByte(message.getInfrastructure().getValue());
		buffer.writeInt(payloadLength);
		buffer.writeBytes(payload.getBytes(CharsetUtil.UTF_8));
		return buffer;
	}

	private static final class InstanceHolder {
		private static final MessageEncoder INSTANCE = new MessageEncoder();
	}
}
