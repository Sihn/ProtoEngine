package sprites;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import level.Level;
import sprites.particles.ParticleStar.StarColor;
import core.Cache;
import core.ParticlesEngine;
import core.Screen;
import core.tools.ZIndex;
import entities.Player;

public class SpriteHud extends Sprite {
	private final int ox = 6;
	private final int oy = 6;
	private final Rectangle backS = new Rectangle(0, 0, 128, 32);
	private final Point text = new Point(24, 24);
	private final Point manaD = new Point(20, 14);
	private final Rectangle manaS = new Rectangle(0, 32, 100, 4);
	private final Point lifeD = new Point(17, 5);
	private final Rectangle lifeBorderS = new Rectangle(0, 36, 16, 8);
	private final Rectangle lifeLightS = new Rectangle(16, 36, 16, 8);
	private final int lifeSpace = 1;

	private Level level;
	private Player player;
	private int hue = 0;
	private int manaWidth = 0;
	private int lastManaWidth = 0;

	public SpriteHud(Level level, Player player) {
		super(ZIndex.HUD, true);
		this.level = level;
		this.player = player;
		image = Cache.getInterface("life.png", 0x800080);
	}

	public void update() {
		manaWidth = manaS.width - player.getAttackDuration() * manaS.width / player.getMaxAttackDuration();
		if (lastManaWidth > manaWidth) {
			ParticlesEngine.addStars(manaD.x + ox + manaWidth, manaD.y + oy + manaS.height / 2, 10, 6, 4, StarColor.BLUE, 1, true);
		}
		lastManaWidth = manaWidth;
	}

	@Override
	public void render(Graphics2D g, Screen screen) {
		g.translate(ox, oy);
		int dx, dy, sx, sy;
		sx = backS.x;
		sy = backS.y;
		g.drawImage(image, 0, 0, backS.width, backS.height, sx, sy, sx + backS.width, sy + backS.height, null);

		dx = manaD.x;
		dy = manaD.y;
		sx = manaS.x;
		sy = manaS.y;
		g.drawImage(image, dx, dy, dx + manaWidth, dy + manaS.height, sx, sy, sx + manaWidth, sy + manaS.height, null);
		for (int i = 0; i < player.getMaxLife(); i++) {
			dx = lifeD.x + i * (lifeBorderS.width + lifeSpace);
			dy = lifeD.y;
			sx = lifeBorderS.x;
			sy = lifeBorderS.y;
			g.drawImage(image, dx, dy, dx + lifeBorderS.width, dy + lifeBorderS.height, sx, sy, sx + lifeBorderS.width, sy + lifeBorderS.height, null);
			if (i < player.getLife()) {
				sx = lifeLightS.x;
				sy = lifeLightS.y;
				g.drawImage(image, dx, dy, dx + lifeBorderS.width, dy + lifeBorderS.height, sx, sy, sx + lifeBorderS.width, sy + lifeBorderS.height, null);
			}
		}

		while (hue > 360)
			hue -= 360;
		if (level.getItemsCount() != 0)
			drawText(g, text.x, text.y, "Cristaux : " + player.getItemsFound() + "/" + level.getItemsCount(), Color.getHSBColor(hue / 360F, .3F, 1), true);
		hue++;
	}

	@Override
	public Rectangle getRect() {
		return null;
	}
}
