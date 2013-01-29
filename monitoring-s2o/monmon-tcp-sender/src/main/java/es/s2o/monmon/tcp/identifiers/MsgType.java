package es.s2o.monmon.tcp.identifiers;

/**
 * Measure type enum.
 * 
 * @author s2o
 * 
 */
public enum MsgType {

	UNKNOWN((byte) 48), TIME((byte) 49), SESSIONS((byte) 50);

	private final byte value;

	private MsgType(final byte type) {
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
	public static MsgType fromByte(final byte b) {
		for (final MsgType code : values()) {
			if (code.value == b) {
				return code;
			}
		}

		return UNKNOWN;
	}
}
