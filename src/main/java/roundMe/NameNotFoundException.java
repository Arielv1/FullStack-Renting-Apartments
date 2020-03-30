package roundMe;

public class NameNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6705441597410384657L;

	public NameNotFoundException() {
	}

	public NameNotFoundException(String message) {
		super(message);
	}

	public NameNotFoundException(Throwable cause) {
		super(cause);
	}

	public NameNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public NameNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
