package exceptions;

public class BadPassabilityException extends IllegalArgumentException {
	private static final long serialVersionUID = 1L;
	String passability;

	public BadPassabilityException(String passability) {
		super();
		this.passability = passability;
	}

	@Override
	public String getMessage() {
		return "La passabilité \"" + passability + "\" n'existe pas.";
	}
}
