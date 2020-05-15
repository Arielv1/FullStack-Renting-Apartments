package acs.logic.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ForbiddenAccessException extends RuntimeException{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 5849266470632923557L;

	public ForbiddenAccessException() {
		super();
	}

	public ForbiddenAccessException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public ForbiddenAccessException(String message) {
		super(message);
		
	}

	public ForbiddenAccessException(Throwable cause) {
		super(cause);
	}
}
