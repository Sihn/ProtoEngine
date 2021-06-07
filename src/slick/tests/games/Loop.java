package slick.tests.games;

import java.awt.Dimension;
import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.Tile;

import slick.tests.BodyMaker;
import slick.tests.DrawableItem;
import slick.tests.Game;
import slick.tests.Level;
import slick.tests.Sprite;
import slick.tests.WorldMaker;

public class Loop implements Game {
	private Body player;

	@Override
	public void init(GameContainer gc, World world, ArrayList<DrawableItem> items) throws SlickException {
		world.setGravity(new Vec2(0, 100));

		int width = 5;
		int height = 4;
		Tile[] tiles = new Tile[width * height];
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
				tiles[x + y * width] = new Tile(x, y, "Under", (int) (Math.random() * 2), "tileset.png?");
		items.add(new Level(world, tiles, width * WorldMaker.TileWidth, height * WorldMaker.TileHeight));

		Dimension plDim = new Dimension(32, 32);
		player = BodyMaker.createBody(world, 200, 10, plDim.width, plDim.height, BodyType.DYNAMIC, 0, 1);
		items.add(new Sprite(player, plDim.width, plDim.height, "src/resources/entities/big_cat.png", 4, 2));
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		Input input = gc.getInput();
		Vec2 point = player.getWorldCenter();
		if (input.isKeyDown(Input.KEY_Z))
			player.applyLinearImpulse(new Vec2(0, -500000), point);
		if (input.isKeyDown(Input.KEY_Q))
			player.applyForce(new Vec2(-500000, 0), point);
		if (input.isKeyDown(Input.KEY_D))
			player.applyForce(new Vec2(500000, 0), point);
		if (input.isKeyPressed(Input.KEY_R))
			gc.reinit();
		if (input.isKeyPressed(Input.KEY_ESCAPE))
			gc.exit();
	}
}
