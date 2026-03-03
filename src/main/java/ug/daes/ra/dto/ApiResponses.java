package ug.daes.ra.dto;

public class ApiResponses {

	private boolean success;

	private String message;

	private Object result;

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
		return "ApiResponses{" + "success=" + success + ", message='" + message + '\'' + ", result=" + result + '}';
	}
}
