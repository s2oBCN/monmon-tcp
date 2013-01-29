package es.s2o.monmon.tcp.client;

import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author s2o
 * 
 */
public class ClientConnectorTest {

	/**
	 * 
	 */
	@Test
	public void testClientConnector() {
		final ClientConnector nio = new ClientConnector(null);
		final ChannelFactory nioFac = nio.getFactory();
		Assert.assertTrue(nioFac instanceof NioClientSocketChannelFactory);

	}

}
