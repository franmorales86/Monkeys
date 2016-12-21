package controller;

import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ErrorMessage {

	/** contains the same HTTP Status code returned by the server */
	@XmlElement(name = "status")
	int status;

	/** message describing the error */
	@XmlElement(name = "message")
	String message;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ErrorMessage(AppException ex) {
		this.status = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
		this.message = ex.getMessage();
	}

	public ErrorMessage() {
	}
}