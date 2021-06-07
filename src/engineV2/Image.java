package engineV2;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image implements Drawable {
	/* Position et dimensions */
	/** Position X sur l'écran */
	public int x;
	/** Position Y sur l'écran */
	public int y;
	/** Position Z sur l'écran, le plus élevé étant affiché par dessus les autres */
	private int z;
	/** Largeur du sprite affiché */
	private int width;
	/** Hauteur du sprite affiché */
	private int height;
	/** Indépendance vis à vis du scrolling (ex : true pour les HUD, false pour les entitées) */
	private boolean fixed;

	/* Transformations */
	/** Angle (0~259) */
	private double angle = 0;
	/** Zoom global (1=100%) */
	private double scale = 1;
	/** Zoom horizontal (1=100%) */
	private double sx = 1;
	/** Zoom vertical (1=100%) */
	private double sy = 1;
	/** Transparance (0~1, 1=opaque, 0=invisible, autre=transparant) */
	public float alpha = 1;
	/** Teinte (changement des couleurs, mais saturation et luminosité identique) */
	public double hue = 0;
	/** Ton (changement complet des couleurs) */
	public Color tone = null;
	/** Inversion horizontale */
	public boolean flipped = false;

	/* Sprite */
	/** Image */
	private java.awt.Image img;
	/** Nombre de colonnes de sprites */
	private int columnMax;
	/** Nombre de lignes de sprites */
	private int lineMax;
	/** Colonne de sprite actuelle */
	public int column = 0;
	/** Ligne de sprite actuelle */
	public int line = 0;
	/** ... */
	private boolean visible = true;

	public Image(String filename, int x, int y) {
		initialize(filename, x, y, 10, 1, 1, false);
	}

	public Image(String filename, int x, int y, int z) {
		initialize(filename, x, y, z, 1, 1, false);
	}

	public Image(String filename, int x, int y, int z, int columnMax, int lineMax) {
		initialize(filename, x, y, z, columnMax, lineMax, false);
	}

	public Image(String filename, int x, int y, int z, int columnMax, int lineMax, boolean fixed) {
		initialize(filename, x, y, z, columnMax, lineMax, fixed);
	}

	private void initialize(String filename, int x, int y, int z, int columnMax, int lineMax, boolean fixed) {
		try {
			img = ImageIO.read(new File("resources/" + filename));
			width = img.getWidth(null) / columnMax;
			height = img.getHeight(null) / lineMax;
		} catch (IOException e) {
			System.err.println("Image \"resources/" + filename + "\" introuvable.");
			img = null;
			width = 32;
			height = 32;
		}
		this.x = x;
		this.y = y;
		this.z = z;
		this.columnMax = columnMax;
		this.lineMax = lineMax;
		this.fixed = fixed;
	}

	@Override
	public int getZ() {
		return z;
	}

	public boolean isFixed() {
		return fixed;
	}

	public void rotate(double add) {
		this.angle = (angle + add) % 360;
	}

	public void setAngle(double angle) {
		this.angle = angle % 360;
	}

	public double getAngle() {
		return angle;
	}

	public void setScale(double scale) {
		this.scale = Math.max(0, scale);
	}

	public void setScale(double sx, double sy) {
		this.sx = Math.max(0, sx);
		this.sy = Math.max(0, sy);
	}

	public void zoom(double zoom) {
		this.scale = Math.max(0, this.scale + zoom);
	}

	public double getScale() {
		return scale;
	}

	public double getScaleX() {
		return sx;
	}

	public double getScaleY() {
		return sy;
	}

	@Override
	public void render(Graphics2D g, Screen screen) {
		if (!visible)
			return;
		double ox = x + width / 2.0;
		double oy = y + height / 2.0;
		g.translate(ox, oy);
		if (angle % 360 != 0)
			g.rotate(Math.toRadians(angle));
		if (scale != 1 || sx != 1 || sy != 1)
			g.scale(scale * sx, scale * sy);
		g.translate(-ox, -oy);
		int dx1 = x;
		int dy1 = y;
		int dx2 = dx1 + width;
		int dy2 = dy1 + height;
		int sx1 = column % columnMax * width;
		int sy1 = line % lineMax * height;
		int sx2 = sx1 + width;
		int sy2 = sy1 + height;
		if (flipped) {
			int mem = sx1;
			sx1 = sx2;
			sx2 = mem;
		}
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Math.max(0, Math.min(1, alpha))));
		g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
	}
}
