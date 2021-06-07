package exceptions;

public class PropertieNotSetException extends Exception {
	private static final long serialVersionUID = 1L;
	String key;

	public PropertieNotSetException(String key) {
		super();
		this.key = key;
	}

	@Override
	public String getMessage() {
		return "Le param�tre \"" + key + "\" n'a pas �t� d�finis.";
	}
}
