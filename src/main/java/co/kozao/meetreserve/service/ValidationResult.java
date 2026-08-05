package co.kozao.meetreserve.service;

public class ValidationResult {
	private final String message;
	private final boolean valid;

	// Constructeur privé — on passe par les méthodes statiques ci-dessous
	private ValidationResult(String message, boolean valid) {
		this.message = message;
		this.valid = valid;
	}

	public static ValidationResult success() {
		return new ValidationResult(null, true);
	}

	public static ValidationResult failure(String message) {
		return new ValidationResult(message, false);
	}

	public boolean isValid() {
		return valid;
	}

	public String getMessage() {
		return message;
	}

}