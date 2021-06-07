package slick.tests;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

public class BodyMaker {
	private BodyMaker() {}

	private final static BodyType DefaultBodyType = BodyType.STATIC;
	private final static float DefaultDensity = 0;
	private final static float DefaultFriction = 1;

	public static Shape createBoxShape(float width, float height) {
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width / 2, height / 2);
		return shape;
	}

	public static Body createBody(World world, float x, float y, float width, float height, float angle) {
		return createBody(world, x, y, createBoxShape(width, height), DefaultBodyType, angle, DefaultDensity, DefaultFriction);
	}

	public static Body createBody(World world, float x, float y, float width, float height, BodyType bodyType, float angle, float density) {
		return createBody(world, x, y, createBoxShape(width, height), bodyType, angle, density, DefaultFriction);
	}

	public static Body createBody(World world, float x, float y, float width, float height, BodyType bodyType, float angle, float density, float friction) {
		return createBody(world, x, y, createBoxShape(width, height), bodyType, angle, density, friction);
	}

	public static Body createBody(World world, float x, float y, Shape shape, BodyType bodyType, float angle, float density, float friction) {
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = density;
		fixtureDef.friction = friction;
		fixtureDef.restitution = 0;

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = bodyType;
		bodyDef.position.set(x, y);
		bodyDef.angle = (float) Math.toRadians(angle);

		Body body = world.createBody(bodyDef);
		body.createFixture(fixtureDef);
		return body;
	}
}
