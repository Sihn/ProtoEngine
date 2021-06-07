package core;

import java.awt.Color;
import java.util.ArrayList;

import sprites.particles.ParticleBubble;
import sprites.particles.ParticleCircle;
import sprites.particles.ParticleDeath;
import sprites.particles.ParticleImage;
import sprites.particles.ParticleShard;
import sprites.particles.ParticleSquare;
import sprites.particles.ParticleStar;
import sprites.particles.ParticleStar.StarColor;
import sprites.particles.SpriteParticle;
import core.tools.Direction;
import entities.Entity;

public final class ParticlesEngine {
	private ParticlesEngine() {}

	static ArrayList<SpriteParticle> particles = new ArrayList<>();

	public static Screen screen;

	public static void addStars(float x, float y, int duration, int size, float spread, StarColor colorType, int count, boolean fixed) {
		for (int i = 0; i < count; i++) {
			SpriteParticle particle = new ParticleStar(x, y, duration, size, spread, colorType);
			particle.setFixed(fixed);
			particles.add(particle);
			screen.addDrawable(particle);
		}
	}

	public static void squaresBlast(float x, float y, int duration, int size, boolean fixed) {
		for (Direction direction : Direction.values()) {
			if (direction == Direction.NONE)
				continue;
			SpriteParticle particle = new ParticleSquare(x, y, duration, size, direction);
			particle.setFixed(fixed);
			particles.add(particle);
			screen.addDrawable(particle);
		}
	}

	public static void shardsBlast(float x, float y, int duration, int size, float spread, float hue, int count, boolean fixed) {
		for (int i = 0; i < count; i++) {
			SpriteParticle particle = new ParticleShard(x, y, duration, size, spread, hue);
			particle.setFixed(fixed);
			particles.add(particle);
			screen.addDrawable(particle);
		}
	}

	public static void addCircles(float x, float y, int duration, int size, float spread, int count, boolean fixed) {
		for (int i = 0; i < count; i++) {
			SpriteParticle particle = new ParticleCircle(x, y, duration, size, spread);
			particle.setFixed(fixed);
			particles.add(particle);
			screen.addDrawable(particle);
		}
	}

	public static void addBubbles(float x, float y, int duration, int size, float spread, int count, boolean fixed) {
		for (int i = 0; i < count; i++) {
			SpriteParticle particle = new ParticleBubble(x, y, duration, size, spread);
			particle.setFixed(fixed);
			particles.add(particle);
			screen.addDrawable(particle);
		}
	}

	public static void collapse(Entity entity, int duration, int size, float spread) {
		Color c1 = new Color(0x000000);
		Color c2 = new Color(0x00FF80);
		Color c3 = new Color(0x000080);
		for (int i = 0; i < 20; i++) {
			SpriteParticle particle = null;
			if (i < 6) {
				particle = new ParticleDeath(entity.getX(), entity.getY(), duration, size, spread, c1);
			} else if (i < 7) {
				particle = new ParticleDeath(entity.getX(), entity.getY(), duration, size, spread, c2);
			} else if (i < 10) {
				particle = new ParticleDeath(entity.getX(), entity.getY(), duration, size, spread, c3);
			} else {
				particle = new ParticleStar(entity.getX(), entity.getY(), duration, size, spread, StarColor.RANDOM);
			}
			particle.setFixed(false);
			particles.add(particle);
			screen.addDrawable(particle);
		}
	}

	/**
	 * @Deprecated Use addLightTrail(float x, float y, Color color, boolean fixed) instead
	 */
	@Deprecated
	public static void addLightTrail(float x, float y, float hue, boolean fixed) {
		SpriteParticle particle = new ParticleImage("mana.png", x, y, 50, 30, 0, 0, 0, hue);
		particle.setFixed(fixed);
		particles.add(particle);
		screen.addDrawable(particle);
	}

	public static void addLightTrail(float x, float y, Color color, boolean fixed) {
		SpriteParticle particle = new ParticleImage("mana2.png", x, y, 50, 30, 0, 0, 0, color);
		particle.setFixed(fixed);
		particles.add(particle);
		screen.addDrawable(particle);
	}

	public static void addFireTrail(float x, float y, boolean fixed) {
		SpriteParticle particle = new ParticleImage("flare.png", x, y, 100, 20, 2, 0, -2, 0);
		particle.setFixed(fixed);
		particles.add(particle);
		screen.addDrawable(particle);
	}

	public static void addParticles(String name, float x, float y, int duration, float spread, float speedX, float speedY, int count, boolean fixed) {
		for (int i = 0; i < count; i++) {
			SpriteParticle particle = new ParticleImage(name, x, y, duration, spread, speedX, speedY);
			particle.setFixed(fixed);
			particles.add(particle);
			screen.addDrawable(particle);
		}
	}

	public static void updateParticles() {
		ArrayList<SpriteParticle> deleteParticles = new ArrayList<>();
		for (SpriteParticle particle : particles) {
			particle.update();
			if (particle.isDead())
				deleteParticles.add(particle);
		}
		for (SpriteParticle particle : deleteParticles)
			particles.remove(particle);
		deleteParticles.clear();
	}

	public static void clearAllParticles(Screen screen) {
		for (SpriteParticle particle : particles)
			screen.removeDrawable(particle);
		particles.clear();
	}
}
