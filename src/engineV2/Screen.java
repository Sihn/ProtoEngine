package engineV2;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class Screen extends Canvas {
	private static final long serialVersionUID = 1L;
	private ArrayList<Drawable> drawables = new ArrayList<>();
	public int ingameWidth;
	public int ingameHeight;

	public Screen(int ingameWidth, int ingameHeight, int trueWidth, int trueHeight) {
		super();
		this.ingameWidth = ingameWidth;
		this.ingameHeight = ingameHeight;
		setIgnoreRepaint(true);
		setSize(trueWidth, trueHeight);
		setBackground(Color.BLACK);
	}

	public void addSprite(Drawable newDrawable) {
		int id = 0;
		for (Drawable drawable : drawables)
			if (newDrawable.getZ() >= drawable.getZ())
				id++;
			else
				break;
		drawables.add(id, newDrawable);
	}

	public void addSprites(Drawable... drawableList) {
		for (Drawable drawable : drawableList)
			drawables.add(drawable);
	}

	public void removeSprite(Drawable drawable) {
		drawables.remove(drawable);
	}

	public void removeDrawables() {
		drawables.clear();
	}

	public void render(Graphics2D g) {
		g.clearRect(0, 0, getWidth(), getHeight());
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.scale((double) getWidth() / ingameWidth, (double) getHeight() / ingameHeight);
		for (Drawable drawable : drawables) {
			AffineTransform saveAT = g.getTransform();
			drawable.render(g, this);
			g.setTransform(saveAT);
		}
		g.dispose();
	}
}
