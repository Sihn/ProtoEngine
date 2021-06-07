package exceptions;

public class PlayerNotSetException extends Exception {
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage() {
		return "La position de départ du joueur est introuvable.";
	}
}
