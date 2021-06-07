package sprites;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import level.Level;
import level.Tile;
import level.TileLayer;
import level.TileLayer.Position;
import core.Cache;
import core.Screen;

public class SpriteLevel extends Sprite {
	private Level datas;
	private Position position;

	public SpriteLevel(Level datas, Position position) {
		super(position.getZ());
		this.datas = datas;
		this.position = position;
		image = Cache.getTiles(datas.getTilesetName());
	}

	@Override
	public void render(Graphics2D g, Screen screen) {
		int startX = -screen.ox / datas.getTileSizeIngame();
		int startY = -screen.oy / datas.getTileSizeIngame();
		int endX = Math.min(startX + screen.getGameWidth() / datas.getTileSizeIngame() + 2, datas.getWidth());
		int endY = Math.min(startY + screen.getGameHeight() / datas.getTileSizeIngame() + 2, datas.getHeight());
		for (int y = startY; y < endY; y++) {
			for (int x = startX; x < endX; x++) {
				Tile tile = datas.getTile(x, y);
				if (tile != null) {
					for (TileLayer layer : tile.layers) {
						if (layer.position != position)
							continue;
						drawTile(g, x, y, layer.line, layer.column + (layer.animations > 0 ? datas.getAnimationStep() / 10 % layer.animations : 0));
					}
				}
			}
		}
	}

	private void drawTile(Graphics2D g, int x, int y, int tileLine, int tileColumn) {
		x *= datas.getTileSizeIngame();
		y *= datas.getTileSizeIngame();
		int width = tileColumn * datas.getTileSize();
		int height = tileLine * datas.getTileSize();
		g.drawImage(image, x, y, x + datas.getTileSizeIngame(), y + datas.getTileSizeIngame(), width, height, width + datas.getTileSize(), height + datas.getTileSize(), null);
	}

	@Override
	public Rectangle getRect() {
		return new Rectangle(0, 0, datas.getTileSizeIngame() * datas.getWidth(), datas.getTileSizeIngame() * datas.getHeight());
	}
}