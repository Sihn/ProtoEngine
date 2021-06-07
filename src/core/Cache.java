package core;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.FilteredImageSource;
import java.io.IOException;
import java.net.URL;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import core.tools.BackgroundFilter;

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
		return getEntity(filename, null);
	}

	public static Image getEntity(final String filename, Integer background) {
		return getResources("/resources/entities/" + filename, true, background);
	}

	public static Image getParticle(final String filename) {
		return getResources("/resources/particles/" + filename, true, null);
	}

	public static Image getInterface(String filename) {
		return getInterface(filename, null);
	}

	public static Image getInterface(String filename, Integer background) {
		return getResources("/resources/interfaces/" + filename, true, background);
	}

	public static Image getTiles(final String filename) {
		return getResources("file:levels/" + filename, false, null);
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
				Log.error("Fichier introuvable : " + path);
			}
			return cursor;
		}
	}

	private static Image getResources(String path, boolean internal, Integer background) {
		if (images.containsKey(path))
			return images.get(path);
		else {
			Image img = null;
			try {
				if (internal)
					img = ImageIO.read(Cache.class.getResource(path));
				else
					img = ImageIO.read(new URL(path));
				if (background != null)
					img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(img.getSource(), new BackgroundFilter(background)));
				images.put(path, img);
			} catch (IOException e) {
				Log.error("Fichier introuvable : " + path);
			}
			return img;
		}
	}
}
