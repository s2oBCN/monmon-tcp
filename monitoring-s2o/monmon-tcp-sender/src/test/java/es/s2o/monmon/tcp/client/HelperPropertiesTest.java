package es.s2o.monmon.tcp.client;

import junit.framework.Assert;

import org.junit.Test;

import es.s2o.monmon.tcp.to.TCPProperties;

public class HelperPropertiesTest {

	/**
	 * Expect file not found
	 */
	// @Test(expected = TCPException.class)
	public void testInitialiceError() {
		HelperProperties.getTCPProperties("Not found");
	}

	/**
	 * Verify properties
	 */
	@Test
	public void testGetTCPProperties() {
		final TCPProperties properties = HelperProperties.getTCPProperties(TestTCPUtils.getPropertiesPathFile());
		Assert.assertEquals("localhost", properties.getHost());
		Assert.assertEquals(9999, properties.getPort());
		Assert.assertEquals("#", properties.getFieldDelimiter());
		Assert.assertEquals(true, properties.isMonitoringEnabled());
	}

	/**
	 * Verify properties
	 */
	@Test
	public void testValidateroperties() {
		final TCPProperties tcpProperties = new TCPProperties();
		Assert.assertFalse("Validate false", HelperProperties.validateProperties(tcpProperties));
	}
}
