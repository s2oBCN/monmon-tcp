package es.s2o.monmon.tcp.identifiers;

/**
 * Measure type enum.
 * 
 * @author s2o
 * 
 */
public enum InputChannel {
	UNKNOWN("UNKNO"), CAX("CAX"), LO("LO"), COF("COF");

	private final String value;

	private InputChannel(final String newValue) {
		value = newValue;
	}

	/**
	 * @return
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 * @return
	 */
	public static InputChannel fromString(final String fromValue) {
		for (final InputChannel code : values()) {
			if (code.value.equals(fromValue)) {
				return code;
			}
		}

		return UNKNOWN;
	}
}
