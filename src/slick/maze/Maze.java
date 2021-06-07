package slick.maze;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.Tile;

import slick.tools.RandomColor;

public class Maze implements DrawableItem {
	private final int TileSize = 32;
	private final float TileSizeIngame = TileSize * GameMain.Zoom;
	private Image image;
	private final int MazeColumns = 34;
	private final int MazeLines = 26;
	private CustomTile[] maze = new CustomTile[MazeColumns * MazeLines];
	private final static int CanPass = 0;
	private final static int CannotPass = 1;
	private Color color;
	private float hue = 0;

	private static class CustomTile extends Tile {
		public static class Corner {
			public int column = 0;
			public int line = 0;

			public void set(int column, int line) {
				this.column = column;
				this.line = line;
			}
		}

		public Corner upLeft = new Corner();
		public Corner upRight = new Corner();
		public Corner downLeft = new Corner();
		public Corner downRight = new Corner();
		public int decoration;

		public CustomTile(int x, int y, String layerName, int gid, String tileset, int decoration) {
			super(x, y, layerName, gid, tileset);
			this.decoration = decoration;
		}
	}

	public Maze() throws SlickException {
		for (int y = 0; y < MazeLines; y++) {
			for (int x = 0; x < MazeColumns; x++) {
				CustomTile tile = new CustomTile(x, y, "Under", (int) (Math.random() * 2), "walls.png", (int) (Math.random() * 4));
				maze[key(x, y)] = tile;
			}
		}
		image = new Image("src/slick/resources/walls.png", new Color(0x800080));
		initializeTiles();
		refreshColor(0);
	}

	private int key(int x, int y) {
		return x + y * MazeColumns;
	}

	private void initializeTiles() {
		for (CustomTile tile : maze) {
			if (tile.gid == CanPass)
				continue;
			initializeTile(tile);
		}
	}

	private void initializeTile(CustomTile tile) {
		boolean border = true;
		boolean up = border;
		boolean upRight = border;
		boolean right = border;
		boolean downRight = border;
		boolean down = border;
		boolean downLeft = border;
		boolean left = border;
		boolean upLeft = border;

		boolean afterLeftBorder = tile.x > 0;
		boolean afterTopBorder = tile.y > 0;
		boolean beforeRightBorder = tile.x < MazeColumns - 1;
		boolean beforeBottomBorder = tile.y < MazeLines - 1;

		if (afterTopBorder)
			up = maze[key(tile.x, tile.y - 1)].gid == CannotPass;
		if (beforeRightBorder && afterTopBorder)
			upRight = maze[key(tile.x + 1, tile.y - 1)].gid == CannotPass;
		if (beforeRightBorder)
			right = maze[key(tile.x + 1, tile.y)].gid == CannotPass;
		if (beforeRightBorder && beforeBottomBorder)
			downRight = maze[key(tile.x + 1, tile.y + 1)].gid == CannotPass;
		if (beforeBottomBorder)
			down = maze[key(tile.x, tile.y + 1)].gid == CannotPass;
		if (afterLeftBorder && beforeBottomBorder)
			downLeft = maze[key(tile.x - 1, tile.y + 1)].gid == CannotPass;
		if (afterLeftBorder)
			left = maze[key(tile.x - 1, tile.y)].gid == CannotPass;
		if (afterLeftBorder && afterTopBorder)
			upLeft = maze[key(tile.x - 1, tile.y - 1)].gid == CannotPass;
		if (up) {
			if (left)
				if (upLeft)
					tile.upLeft.set(9, 0);
				else
					tile.upLeft.set(2, 0);
			else
				tile.upLeft.set(4, 0);
			if (right)
				if (upRight)
					tile.upRight.set(8, 0);
				else
					tile.upRight.set(3, 0);
			else
				tile.upRight.set(5, 0);
		} else {
			if (left)
				tile.upLeft.set(6, 0);
			else
				tile.upLeft.set(0, 0);
			if (right)
				tile.upRight.set(7, 0);
			else
				tile.upRight.set(1, 0);
		}
		if (down) {
			if (left)
				if (downLeft)
					tile.downLeft.set(9, 1);
				else
					tile.downLeft.set(2, 1);
			else
				tile.downLeft.set(4, 1);
			if (right)
				if (downRight)
					tile.downRight.set(8, 1);
				else
					tile.downRight.set(3, 1);
			else
				tile.downRight.set(5, 1);
		} else {
			if (left)
				tile.downLeft.set(6, 1);
			else
				tile.downLeft.set(0, 1);
			if (right)
				tile.downRight.set(7, 1);
			else
				tile.downRight.set(1, 1);
		}
	}

	private void refreshColor(float hue) {
		color = RandomColor.getHSBColor(hue / 360F, .2F, 1);
	}

	private Image getCorner(CustomTile.Corner corner) {
		return image.getSubImage(corner.column * TileSize / 2, corner.line * TileSize / 2, TileSize / 2, TileSize / 2);
	}

	private void drawDecoration(CustomTile tile) {
		Image tileImage = image.getSubImage(tile.decoration + 1 * TileSize, 1 * TileSize, TileSize, TileSize);
		tileImage.draw(tile.x * TileSizeIngame, tile.y * TileSizeIngame, GameMain.Zoom, color);
	}

	private void drawGround(CustomTile tile) {
		Image tileImage = image.getSubImage(0 * TileSize, 1 * TileSize, TileSize, TileSize);
		tileImage.draw(tile.x * TileSizeIngame, tile.y * TileSizeIngame, GameMain.Zoom);
	}

	private void drawWall(CustomTile tile) {
		float demiTileSize = TileSizeIngame / 2;
		getCorner(tile.upLeft).draw(tile.x * TileSizeIngame, tile.y * TileSizeIngame, GameMain.Zoom);
		getCorner(tile.upRight).draw(tile.x * TileSizeIngame + demiTileSize, tile.y * TileSizeIngame, GameMain.Zoom);
		getCorner(tile.downRight).draw(tile.x * TileSizeIngame + demiTileSize, tile.y * TileSizeIngame + demiTileSize, GameMain.Zoom);
		getCorner(tile.downLeft).draw(tile.x * TileSizeIngame, tile.y * TileSizeIngame + demiTileSize, GameMain.Zoom);
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		refreshColor(hue);
		hue += .3F;
		while (hue >= 360)
			hue -= 360;
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		for (int y = 0; y < MazeLines; y++) {
			for (int x = 0; x < MazeColumns; x++) {
				CustomTile tile = maze[key(x, y)];
				if (tile.gid == 0) {
					drawGround(tile);
					drawDecoration(tile);
				} else
					drawWall(tile);
			}
		}
	}
}
