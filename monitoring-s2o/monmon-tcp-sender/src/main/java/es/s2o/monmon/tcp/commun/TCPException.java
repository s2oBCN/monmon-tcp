package es.s2o.monmon.tcp.commun;

/**
 * @author s2o
 * 
 */
public class TCPException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -378656453816649975L;

	/**
	 * @param e
	 */
	public TCPException(final Exception exception) {
		// TODO ver como tratar las excepciones
		super(exception);
	}

}
