package es.s2o.monmon.tcp.identifiers;

/**
 * Versions of messages
 * 
 * @author s2o
 * 
 */
public enum MsgVersion {
	UNKNOWN((byte) 48), VERSION1((byte) 49), VERSION2((byte) 50);

	private final byte value;

	private MsgVersion(final byte version) {
		value = version;
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
	public static MsgVersion fromByte(final byte b) {
		for (final MsgVersion code : values()) {
			if (code.value == b) {
				return code;
			}
		}

		return UNKNOWN;
	}

	@Override
	public String toString() {
		String answer = "";
		switch (value) {
		case 48:
			answer = "0";
			break;
		case 49:
			answer = "1";
			break;
		case 50:
			answer = "2";
			break;
		default:
			break;
		}
		return answer;
	}
}
