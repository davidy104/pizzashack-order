package co.nz.pizzashack;

@SuppressWarnings("serial")
public class DuplicatedException extends Exception {

	public DuplicatedException() {
		super();
	}

	public DuplicatedException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DuplicatedException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicatedException(String message) {
		super(message);
	}

	public DuplicatedException(Throwable cause) {
		super(cause);
	}

}
