package co.nz.pizzashack.client;

@SuppressWarnings("serial")
public class NotFoundException extends Exception {

	public NotFoundException(String message) {
		super(message);
	}

	public NotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
