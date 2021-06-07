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

import slick.tests.BodyMaker;
import slick.tests.DrawableItem;
import slick.tests.Game;
import slick.tests.Sprite;
import slick.tests.TestBox;

public class Test implements Game {
	private Body player;

	@Override
	public void init(GameContainer gc, World world, ArrayList<DrawableItem> items) throws SlickException {
		world.setGravity(new Vec2(0, 100));
		Dimension grDim = new Dimension(50, 10);
		Body ground = BodyMaker.createBody(world, 200, 100, grDim.width, grDim.height, BodyType.STATIC, 10, 0);
		items.add(new TestBox(ground, grDim.width, grDim.height));

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
