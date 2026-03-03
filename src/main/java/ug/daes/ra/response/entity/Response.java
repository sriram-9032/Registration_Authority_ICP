package ug.daes.ra.response.entity;

import java.io.Serializable;

public class Response implements Serializable {



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean success;

	private String message;

	private Object result;

	public Response(boolean success, String message, Object result) {
		this.success = success;
		this.message = message;
		this.result = result;
	}

	public Response() {

	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "ApiResponse{" + "success=" + success + ", message='" + message + '\'' + ", result=" + result + '}';
	}
}
