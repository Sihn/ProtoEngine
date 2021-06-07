package core;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public final class Screen extends Canvas {
	private static final long serialVersionUID = 1L;

	public static final Font BASE_FONT = new Font("Arial", Font.PLAIN, 12);
	public static final Color BACKGROUND_COLOR = Color.BLACK;
	public static final Color FONT_COLOR = Color.WHITE;
	public static final Color DEFAULT_COLOR = Color.WHITE;

	private ArrayList<Drawable> drawables = new ArrayList<>();
	private ArrayList<Drawable> removedDrawables = new ArrayList<>();
	public int ox = 0;
	public int oy = 0;
	public double ratioX = 1;
	public double ratioY = 1;
	public int gameWidth;
	public int gameHeight;
	public static final float ratio = 16F / 9F;
	public static final int gameHeightMin = 90;
	public static final int gameWidthMin = (int) (gameHeightMin * ratio);
	public static final int gameHeightMax = 1080;
	public static final int gameWidthMax = (int) (gameHeightMax * ratio);

	public Screen(int screenWidth, int screenHeight, int gameWidth, int gameHeight) {
		super();
		setGameSize(gameWidth, gameHeight);
		setIgnoreRepaint(true);
		setSize(screenWidth, screenHeight);
		setBackground(Screen.BACKGROUND_COLOR);
		setFont(Screen.BASE_FONT);
		Cache.setCursor("brush.png", 0, 0, "Brush", this);
	}

	public void setGameSize(int size) {
		setGameSize((int) (size * ratio), size);
	}

	public void setGameSize(int width, int height) {
		gameWidth = Math.min(gameWidthMax, Math.max(gameWidthMin, width));
		gameHeight = Math.min(gameHeightMax, Math.max(gameHeightMin, height));
	}

	public int getGameWidth() {
		return gameWidth;
	}

	public int getGameHeight() {
		return gameHeight;
	}

	public double getScreenWidth() {
		return getWidth();
	}

	public double getScreenHeight() {
		return getHeight();
	}

	public void addDrawable(Drawable newDrawable) {
		int id = 0;
		for (Drawable drawable : drawables) {
			if (newDrawable.getZ() >= drawable.getZ())
				id++;
			else
				break;
		}
		drawables.add(id, newDrawable);
	}

	public void removeDrawable(Drawable drawable) {
		drawables.remove(drawable);
	}

	public void removeDrawables() {
		drawables.clear();
	}

	public int drawablesCount() {
		return drawables.size();
	}

	public void render(Graphics2D g) {
		g.clearRect(0, 0, gameWidth, gameHeight);
		Rectangle screen = new Rectangle(-ox, -oy, gameWidth, gameHeight);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.scale(ratioX = getScreenWidth() / gameWidth, ratioY = getScreenHeight() / gameHeight);
		for (Drawable drawable : drawables) {
			if (drawable.isDead()) {
				removedDrawables.add(drawable);
				continue;
			}
			if (drawable.isFixed() || screen.intersects(drawable.getRect())) {
				if (!drawable.isFixed())
					g.translate(ox, oy);
				Composite comp = g.getComposite();
				AffineTransform Tx = g.getTransform();
				drawable.render(g, this);
				g.setTransform(Tx);
				g.setComposite(comp);
				if (!drawable.isFixed())
					g.translate(-ox, -oy);
			}
		}
		g.dispose();
		for (Drawable drawable : removedDrawables)
			drawables.remove(drawable);
		removedDrawables.clear();
	}
}
