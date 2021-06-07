package sprites;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.FilteredImageSource;

import core.Cache;
import core.Screen;
import core.tools.ColorFilter;
import entities.Entity;

public class SpriteEntity extends Sprite {
	private Entity datas;
	private String lastSpriteName;
	private float lastHue;
	private boolean visible = true;
	private int blinkStep = 0;
	private final int blinkFrequency = 5;
	private boolean decal = false;
	private final int Decalage = 3;

	public SpriteEntity(Entity datas) {
		super(datas.getZ());
		this.datas = datas;
		refresh(datas.getSpriteName(), datas.getHue());
	}

	private void refresh(String spriteName, float hue) {
		this.lastSpriteName = spriteName;
		this.lastHue = hue;
		image = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(Cache.getEntity(spriteName, 0x800080).getSource(), new ColorFilter(hue)));
		decal = spriteName.startsWith("!");
	}

	@Override
	public boolean isDead() {
		return datas.isDisposed();
	}

	@Override
	public void render(Graphics2D g, Screen screen) {
		if (datas.getSpriteName() != lastSpriteName || datas.getHue() != lastHue)
			refresh(datas.getSpriteName(), datas.getHue());
		if (datas.isVisible()) {
			if (datas.isBlinking()) {
				blinkStep++;
				if (blinkStep % blinkFrequency == 0)
					visible = !visible;
			} else {
				blinkStep = 0;
				visible = true;
			}
		} else {
			visible = false;
		}
		if (visible) {
			int dx1 = datas.getX() - datas.getSpriteWidth() / 2;
			int dy1 = datas.getY() - datas.getSpriteHeight();
			if (decal)
				dy1 += Decalage;
			int dx2 = dx1 + datas.getSpriteWidth();
			int dy2 = dy1 + datas.getSpriteHeight();
			int realWidth = image.getWidth(null) / datas.getMaxColumns();
			int realHeight = image.getHeight(null) / datas.getMaxLines();
			int sx1 = datas.getColumn() * realWidth;
			int sy1 = datas.getLine() * realHeight;
			int sx2 = sx1 + realWidth;
			int sy2 = sy1 + realHeight;
			if (datas.lookAt() == Entity.lookAtLeft) {
				int mem = sx1;
				sx1 = sx2;
				sx2 = mem;
			}
			double theta = Math.toRadians(datas.getAngle()) * datas.convertDirection();
			double x = datas.getX();
			double y = datas.getY() - datas.getSpriteHeight() / 2;
			g.rotate(theta, x, y);
			g.drawImage(image, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
		}
	}

	@Override
	public Rectangle getRect() {
		return new Rectangle(datas.getX() - datas.getSpriteWidth() / 2, datas.getY() - datas.getSpriteHeight(), datas.getSpriteWidth() - 1, datas.getSpriteHeight() - 1);
	}
}
