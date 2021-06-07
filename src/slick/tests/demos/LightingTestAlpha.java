package slick.tests.demos;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * davedes' Tutorials Alpha Map Lighting http://slick.cokeandcode.com/wiki/doku.php?id=alpha_maps
 * 
 * @author davedes
 */
public class LightingTestAlpha extends BasicGame {
	public static final int TILE_COUNT = 5;
	public static final int TILE_SIZE = 40;
	public static final int ALPHA_MAP_SIZE = 256;
	public static final int TILE_SPACING = 2;
	private Image spriteSheet;
	private Image[] tileSprites;
	private Image[][] tileMap;
	private int mapWidth, mapHeight;
	private Image alphaMap;
	private Random random = new Random();
	private List<Light> lights = new ArrayList<Light>();
	private long elapsed;
	private Color sharedColor = new Color(1f, 1f, 1f, 1f);

	public static class Light {
		float x, y;
		Color tint;
		float alpha;
		private float scale;
		private float scaleOrig;

		public Light(float x, float y, float scale, Color tint) {
			this.x = x;
			this.y = y;
			this.scale = scaleOrig = scale;
			this.alpha = 1f;
			this.tint = tint;
		}

		public Light(float x, float y, float scale) {
			this(x, y, scale, Color.white);
		}

		public void update(float time) {
			scale = scaleOrig + 1f + .5f * (float) Math.sin(time);
		}
	}

	public LightingTestAlpha() {
		super("Alpha Map Lighting");
	}

	public void init(GameContainer container) throws SlickException {
		spriteSheet = new Image("src/slick/resources/lighting_sprites.png", false, Image.FILTER_NEAREST);
		tileSprites = new Image[TILE_COUNT];
		for (int i = 0; i < tileSprites.length; i++)
			tileSprites[i] = spriteSheet.getSubImage(i * (TILE_SIZE + TILE_SPACING), 0, TILE_SIZE, TILE_SIZE);
		alphaMap = spriteSheet.getSubImage(0, TILE_SIZE + TILE_SPACING, ALPHA_MAP_SIZE, ALPHA_MAP_SIZE);
		randomizeMap(container);
		resetLights(container);
	}

	Image randomTile() {
		int r = random.nextInt(100);
		if (r < 5)
			return tileSprites[1 + random.nextInt(4)];
		else
			return tileSprites[0];
	}

	void randomizeMap(GameContainer container) {
		mapWidth = container.getWidth() / TILE_SIZE + 1;
		mapHeight = container.getHeight() / TILE_SIZE + 1;
		tileMap = new Image[mapWidth][mapHeight];
		for (int x = 0; x < mapWidth; x++) {
			for (int y = 0; y < mapHeight; y++) {
				tileMap[x][y] = randomTile();
			}
		}
	}

	public void resetLights(GameContainer container) {
		lights.clear();
		lights.add(new Light(container.getInput().getMouseX(), container.getInput().getMouseY(), 1f));
	}

	public void render(GameContainer container, Graphics g) throws SlickException {
		for (Light light : lights) {
			g.setDrawMode(Graphics.MODE_ALPHA_MAP);
			g.clearAlphaMap();
			int alphaW = (int) (alphaMap.getWidth() * light.scale);
			int alphaH = (int) (alphaMap.getHeight() * light.scale);
			int alphaX = (int) (light.x - alphaW / 2f);
			int alphaY = (int) (light.y - alphaH / 2f);
			sharedColor.a = light.alpha;
			alphaMap.draw(alphaX, alphaY, alphaW, alphaH, sharedColor);
			g.setDrawMode(Graphics.MODE_ALPHA_BLEND);
			g.setClip(alphaX, alphaY, alphaW, alphaH);
			spriteSheet.startUse();
			light.tint.bind();
			for (int x = 0; x < mapWidth; x++) {
				for (int y = 0; y < mapHeight; y++) {
					tileMap[x][y].drawEmbedded(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
				}
			}
			spriteSheet.endUse();
			g.clearClip();
		}
		g.setDrawMode(Graphics.MODE_NORMAL);
		g.setColor(Color.white);
		g.drawString("Mouse click to add a light (total count: " + lights.size() + ")", 10, 25);
		g.drawString("Press R to randomize the map tiles", 10, 40);
		g.drawString("Press SPACE to reset the lights", 10, 55);
	}

	public void update(GameContainer container, int delta) throws SlickException {
		if (container.getInput().isKeyPressed(Input.KEY_R))
			randomizeMap(container);
		if (container.getInput().isKeyPressed(Input.KEY_SPACE))
			resetLights(container);
		elapsed += delta;
		for (Light light : lights)
			light.update(elapsed / 1000f);
		if (lights.size() > 0) {
			Light l = lights.get(lights.size() - 1);
			l.x = container.getInput().getMouseX();
			l.y = container.getInput().getMouseY();
		}
	}

	public void mousePressed(int button, int x, int y) {
		float randSize = random.nextInt(15) * .1f + .5f;
		Color randColor = new Color(random.nextFloat(), random.nextFloat(), random.nextFloat());
		lights.add(new Light(x, y, randSize, randColor));
	}

	public static void main(String[] args) {
		try {
			new AppGameContainer(new LightingTestAlpha(), 800, 600, false).start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
