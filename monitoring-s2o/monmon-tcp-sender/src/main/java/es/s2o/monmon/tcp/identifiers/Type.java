package es.s2o.monmon.tcp.identifiers;

/**
 * Measure type enum.
 * 
 * @author s2o
 * 
 */
public enum Type {

	UNKNOWN((byte) 48), TIME((byte) 49), SESSIONS((byte) 50);

	private final byte value;

	private Type(final byte type) {
		value = type;
	}

	/**
	 * @return
	 */
	public byte getValue() {
		return value;
	}

	/**
	 * @param value
	 * @return
	 */
	public static Type fromByte(final byte b) {
		for (final Type code : values()) {
			if (code.value == b) {
				return code;
			}
		}

		return UNKNOWN;
	}
}
