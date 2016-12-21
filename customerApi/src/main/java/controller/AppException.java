package controller;

import javax.ws.rs.core.Response.Status;

/**
 * Exception to manage error in API Rest
 * @author "fjmorales"
 *
 */
public class AppException extends Exception {

	private static final long serialVersionUID = 2656974803400780148L;
	
	private Status status;
	
	public AppException(String message) {
		super(message);
		this.status = Status.INTERNAL_SERVER_ERROR;
	}
	
	public AppException(String message, Status status) {
		super(message);
		this.status = status;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
