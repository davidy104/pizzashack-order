package co.nz.pizzashack;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class AuthenticationException extends Exception {

	private String code;

	public AuthenticationException(String message, String code) {
		super(message);
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public AuthenticationException() {
		super();
	}

	public AuthenticationException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthenticationException(String message) {
		super(message);
	}

	public AuthenticationException(Throwable cause) {
		super(cause);
	}

}
