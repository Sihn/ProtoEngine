package engineV2;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;
import java.util.Hashtable;

import javax.imageio.ImageIO;

public class Cache {
	private Cache() {}

	private static Hashtable<String, Image> images = new Hashtable<>();
	private static Hashtable<String, Cursor> cursors = new Hashtable<>();

	public static void setCursor(final String filename, final int x, final int y, final String name, final Component component) {
		new Thread() {
			public void run() {
				component.setCursor(getCursor(filename, x, y, name));
			}
		}.start();
	}

	public static Image getEntity(final String filename) {
		return getResources("resources/entities/" + filename, true);
	}

	public static Image getParticle(final String filename) {
		return getResources("resources/particles/" + filename, true);
	}

	public static Image getInterface(String filename) {
		return getResources("resources/interfaces/" + filename, true);
	}

	public static Image getTiles(final String filename) {
		return getResources("file:levels/" + filename, false);
	}

	private static Cursor getCursor(String filename, int x, int y, String name) {
		String path = "/resources/cursors/" + filename;
		if (cursors.containsKey(path))
			return cursors.get(path);
		else {
			Cursor cursor = null;
			try {
				Image img = ImageIO.read(Cache.class.getResource(path));
				cursor = Toolkit.getDefaultToolkit().createCustomCursor(img, new Point(x, y), name);
				cursors.put(path, cursor);
				return cursor;
			} catch (IOException e) {
				System.err.println("Fichier introuvable : " + path);
			}
			return cursor;
		}
	}

	private static Image getResources(String path, boolean internal) {
		if (images.containsKey(path))
			return images.get(path);
		else {
			Image img = null;
			try {
				if (internal)
					img = ImageIO.read(Cache.class.getResource(path));
				else
					img = ImageIO.read(new URL(path));
				images.put(path, img);
			} catch (IOException e) {
				System.err.println("Fichier introuvable : " + path);
			}
			return img;
		}
	}
}
