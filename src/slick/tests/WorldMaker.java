package slick.tests;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.tiled.Tile;

public class WorldMaker {
	public static final int TileWidth = 32;
	public static final int TileHeight = TileWidth;

	private WorldMaker() {}

	public static Shape createBoxShape(Tile tile) {
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(TileWidth, TileHeight, new Vec2(tile.x * TileWidth, tile.y * TileHeight), 0);
		return shape;
	}

	public static Body createBody(World world, Tile... tiles) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.STATIC;
		bodyDef.position.set(0, 0);
		Body body = world.createBody(bodyDef);
		for (Tile tile : tiles) {
			if (tile.gid == 0)
				continue;
			FixtureDef fixtureDef = new FixtureDef();
			fixtureDef.shape = createBoxShape(tile);
			fixtureDef.density = 0;
			fixtureDef.friction = 1;
			fixtureDef.restitution = 0;
			body.createFixture(fixtureDef);
		}
		return body;
	}
}
