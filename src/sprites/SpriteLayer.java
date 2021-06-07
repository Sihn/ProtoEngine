package sprites;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import level.Level;
import level.TileLayer;
import core.Cache;
import core.Screen;

public class SpriteLayer extends Sprite {
	private Level level;
	private TileLayer layer;

	public SpriteLayer(Level level, TileLayer layer) {
		super(layer.position.getZ());
		this.level = level;
		this.layer = layer;
		image = Cache.getTiles(level.getTilesetName());
	}

	@Override
	public void render(Graphics2D g, Screen screen) {
		int startX = -screen.ox / level.getTileSizeIngame();
		int startY = -screen.oy / level.getTileSizeIngame();
		int endX = Math.min(startX + screen.getGameWidth() / level.getTileSizeIngame() + 2, level.getWidth());
		int endY = Math.min(startY + screen.getGameHeight() / level.getTileSizeIngame() + 2, level.getHeight());
		for (int y = startY; y < endY; y++) {
			for (int x = startX; x < endX; x++) {
				drawTile(g, x, y, layer.line, layer.column + (layer.animations > 0 ? level.getAnimationStep() / 10 % layer.animations : 0));
			}
		}
	}

	private void drawTile(Graphics2D g, int x, int y, int tileLine, int tileColumn) {
		x *= level.getTileSizeIngame();
		y *= level.getTileSizeIngame();
		int width = tileColumn * level.getTileSize();
		int height = tileLine * level.getTileSize();
		g.drawImage(image, x, y, x + level.getTileSizeIngame(), y + level.getTileSizeIngame(), width, height, width + level.getTileSize(), height + level.getTileSize(), null);
	}

	@Override
	public Rectangle getRect() {
		return new Rectangle(0, 0, level.getTileSizeIngame(), level.getTileSizeIngame());
	}
}