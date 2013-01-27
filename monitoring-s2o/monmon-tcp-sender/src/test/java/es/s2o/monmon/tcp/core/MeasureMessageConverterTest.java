package es.s2o.monmon.tcp.core;

import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.junit.Test;

import es.s2o.monmon.tcp.identifiers.Version;
import es.s2o.monmon.tcp.to.MeasureMessage;

/**
 * @author s2o
 * 
 */
public class MeasureMessageConverterTest {

	@Test
	public void testToDTO() {
		final short timestamp = (short) (System.currentTimeMillis() * 1000);
		final BigDecimal value = new BigDecimal(123);
		// final String measureMessage = Version.VERSION1.getVersion() + NettyConstants.FIELD_DELIMITER + "ManagedId"
		// + NettyConstants.FIELD_DELIMITER + Type.USERS.getType() + NettyConstants.FIELD_DELIMITER
		// + "MeasurementSource" + NettyConstants.FIELD_DELIMITER + "MeasurementSourceVersion"
		// + NettyConstants.FIELD_DELIMITER + "MeasureTarget" + NettyConstants.FIELD_DELIMITER
		// + "MeasureTargetVersion" + NettyConstants.FIELD_DELIMITER + timestamp + NettyConstants.FIELD_DELIMITER
		// + value;
		//
		// final MeasureMessage actual = MeasureMessageConverter.toDTO(measureMessage);
		// Assert.assertEquals(init(timestamp).toString(), actual.toString());
	}

	@Test
	public void testToStringMeasureMessage() {
		fail("Not yet implemented");
	}

	private MeasureMessage init(final short timestamp) {
		final MeasureMessage answer = new MeasureMessage();
		answer.setVersion(Version.VERSION1);
		answer.setManagedId("ManagedId");

		answer.setTimestamp(timestamp);
		// answer.setValue(123);

		return answer;
	}
}
