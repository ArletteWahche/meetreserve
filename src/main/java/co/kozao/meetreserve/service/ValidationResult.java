package co.kozao.meetreserve.service;

public class ValidationResult {
	private String message;
	private boolean valid;
	
	public void ValidationResult(String message, boolean valid) {
		this.message = message;
		this.valid = valid;
	}
	
	public boolean isValid() {
		return valid;
	}
	
	public String getMessage() {
		return message;
	}
	 
}
