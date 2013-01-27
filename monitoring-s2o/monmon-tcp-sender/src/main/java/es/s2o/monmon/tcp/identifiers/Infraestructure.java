package es.s2o.monmon.tcp.identifiers;

/**
 * Versions of messages
 * 
 * @author s2o
 * 
 */
public enum Infraestructure {

	UNKNOWN((byte) 48), ABSIS((byte) 49);

	private final byte value;

	private Infraestructure(final byte Infraestructure) {
		value = Infraestructure;
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
	public static Infraestructure fromByte(final byte value) {
		for (final Infraestructure code : values()) {
			if (code.value == value) {
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
