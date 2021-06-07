package ticTacToe;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import sprites.Sprite;
import core.Cache;
import core.Screen;

public class SpriteGrid extends Sprite {
	private Grid grid;
	private int borderSize;
	private int imgWidth = 32;
	private int imgHeight = imgWidth;

	public SpriteGrid(Grid grid, int borderSize) {
		super(0, true);
		this.image = Cache.getEntity("runes2.png", 0x000000);
		this.grid = grid;
		this.borderSize = borderSize;
	}

	@Override
	public Rectangle getRect() {
		return new Rectangle();
	}

	@Override
	public void render(Graphics2D g, Screen screen) {
		Player[] tiles = grid.getTiles();
		int size = grid.getSize();
		int tileWidth = (screen.gameWidth - borderSize * (size + 1)) / size;
		int tileHeight = (screen.gameHeight - borderSize * (size + 1)) / size;
		g.setColor(grid.getBackgroundColor());
		g.fillRect(0, 0, screen.gameWidth, screen.gameHeight);
		for (int i = 0; i < tiles.length; i++) {
			int x = (tileWidth + borderSize) * (i % size) + borderSize;
			int y = (tileHeight + borderSize) * (i / size) + borderSize;
			int column = 0;
			int line = i == grid.currentTile() ? imgHeight : 0;
			g.drawImage(image, x, y, x + tileWidth, y + tileHeight, column, line, column + imgWidth, line + imgHeight, null);
			Player player = tiles[i];
			if (player != null) {
				column = player.column * imgWidth;
				line = player.line * imgHeight;
				g.drawImage(image, x, y, x + tileWidth, y + tileHeight, column, line, column + imgWidth, line + imgHeight, null);
			}
		}
	}
}
