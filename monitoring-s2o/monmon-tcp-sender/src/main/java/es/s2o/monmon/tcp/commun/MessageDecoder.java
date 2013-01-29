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
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.replay.ReplayingDecoder;
import org.jboss.netty.util.CharsetUtil;

import es.s2o.monmon.tcp.identifiers.Infraestructure;
import es.s2o.monmon.tcp.identifiers.MsgType;
import es.s2o.monmon.tcp.identifiers.MsgVersion;
import es.s2o.monmon.tcp.to.MeasureMessage;

/**
 * Decodes a received {@link ChannelBuffer} into a {@link MeasureMessage}. <br/>
 * It's a combination of fixed length and fieldDelimiter
 * 
 */
public class MessageDecoder extends ReplayingDecoder<MessageDecoder.DecodingState> {

	private MeasureMessage message;

	private static final String fieldDelimiter = "#";

	/**
	 * Initialize new message
	 */
	public MessageDecoder() {
		reset();
	}

	/**
	 * Loop until the end of the message
	 * 
	 * @see org.jboss.netty.handler.codec.replay.ReplayingDecoder#decode(org.jboss.netty.channel.ChannelHandlerContext,
	 * org.jboss.netty.channel.Channel, org.jboss.netty.buffer.ChannelBuffer, java.lang.Enum)
	 */
	@Override
	protected Object decode(final ChannelHandlerContext ctx, final Channel channel, final ChannelBuffer buffer,
			final DecodingState state) throws Exception {
		switch (state) {
		case VERSION:
			message.setMsgVersion(MsgVersion.fromByte(buffer.readByte()));
			checkpoint(DecodingState.TYPE);
		case TYPE:
			message.setMsgType(MsgType.fromByte(buffer.readByte()));
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
			final String payload = new String(content, CharsetUtil.UTF_8);
			decodeFromTCPStringDelimited(payload);
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
	 * Decode from string to MeasureMessage Object
	 * 
	 * @param message
	 * @return
	 */
	private void decodeFromTCPStringDelimited(final String payload) {
		final String[] decoded = payload.split(fieldDelimiter);

		message.setInputChannel(decoded[0]);
		message.setSubchannel(decoded[1]);
		message.setSubsubchannel(decoded[2]);
		message.setLayer(decoded[3]);
		message.setManagedId(decoded[4]);
		final int timestamp = Integer.valueOf(decoded[5]);
		message.setTimestamp(timestamp);
		final short value = Short.valueOf(decoded[6]);
		message.setValue(value);
		message.setInvoker(decoded[7]);
		message.setInvokerVersion(decoded[8]);
		message.setTarget(decoded[9]);
		message.setTargetVersion(decoded[10]);
		message.setRequestProtocol(decoded[11]);
		message.setRetval(decoded[12]);

	}

	/**
	 * New message
	 */
	private void reset() {
		checkpoint(DecodingState.VERSION);
		message = new MeasureMessage();
	}

	/**
	 * @author s2o
	 * 
	 */
	public enum DecodingState {
		VERSION, TYPE, INFRAESTRUCTURE, PAYLOAD_LENGTH,
	}
}
