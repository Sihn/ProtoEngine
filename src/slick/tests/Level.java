package slick.tests;

import org.jbox2d.dynamics.World;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.Tile;

import slick.tools.RandomColor;

public class Level extends DrawableItem {
	Color color;
	Tile[] tiles;

	public Level(World world, Tile[] tiles, int width, int height) {
		super(WorldMaker.createBody(world, tiles), width, height);
		this.tiles = tiles;
		color = RandomColor.getSaturatedColor();
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.rotate(getX(), getY(), (float) Math.toDegrees(getAngle()));
		for (Tile tile : tiles) {
			if (tile.gid == 0)
				continue;
			int x = (tile.x - WorldMaker.TileWidth / 2) * WorldMaker.TileWidth;
			int y = (tile.y - WorldMaker.TileHeight / 2) * WorldMaker.TileHeight;
			g.setColor(color.brighter());
			g.fillRect(x, y, WorldMaker.TileWidth, WorldMaker.TileHeight);
			g.setColor(color.darker());
			g.drawRect(x, y, WorldMaker.TileWidth, WorldMaker.TileHeight);
		}
		/*
		 * for (Fixture fixture = body.getFixtureList(); fixture != null; fixture = fixture.getNext()) { g.setColor(color.brighter()); g.fillRect(getX() - getWidth() / 2, getY() - getHeight() / 2, getWidth(), getHeight()); g.setColor(color.darker()); g.drawRect(getX() - getWidth() / 2, getY() -
		 * getHeight() / 2, getWidth(), getHeight()); }
		 */
	}
}
