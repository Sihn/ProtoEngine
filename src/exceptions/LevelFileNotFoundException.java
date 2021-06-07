package exceptions;

import java.io.IOException;

public class LevelFileNotFoundException extends IOException {
	private static final long serialVersionUID = 1L;
	String path;

	public LevelFileNotFoundException(String path) {
		super();
		this.path = path;
	}

	@Override
	public String getMessage() {
		return "Le fichier \"" + path + "\" est introuvable.";
	}
}
