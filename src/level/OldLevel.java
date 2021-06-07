package level;

/*import java.awt.Shape;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import core.Main;
import core.Screen;
import core.tools.Direction;
import entities.Entity;
import entities.Player;
import entities.Target;
import exceptions.BadPassabilityException;
import exceptions.LevelFileNotFoundException;
import exceptions.PlayerNotSetException;
import exceptions.PropertieNotSetException;*/

public class OldLevel {
	/*private final String name;
	private final int width;
	private final int height;

	private final String tilesetName;
	private final int tileSize;
	private final int tileSizeIngame;
	private final HashMap<Integer, Tile> tileset;
	private final int[] levelDatas;
	private int animationStep = 0;

	private int[] skyColors;

	private final Player player;
	private ArrayList<Entity> entities;
	private Target camera;
	private int itemsCount = 0;

	public OldLevel(File levelFile) throws LevelFileNotFoundException, PropertieNotSetException, PlayerNotSetException, BadPassabilityException {
		LevelLoader loader = new LevelLoader(levelFile, this);
		tileset = loader.getTileset();
		width = loader.getWidth();
		height = loader.getHeight();
		levelDatas = loader.getLevelDatas();
		player = loader.getPlayer();
		itemsCount = loader.getItemsCount();
		entities = loader.getEntities();
		name = loader.getProperties("name");
		tilesetName = loader.getProperties("tileset_name");
		tileSize = Integer.parseInt(loader.getProperties("tile_size"));
		tileSizeIngame = Integer.parseInt(loader.getProperties("tile_size_ingame"));
		String[] colors = loader.getProperties("sky_colors").split(",");
		skyColors = new int[colors.length];
		for (int i = 0; i < colors.length; i++)
			skyColors[i] = Integer.parseInt(colors[i], 16);
		camera = player;
	}

	public int[] getSkyColors() {
		return skyColors;
	}

	public Tile getTile(int x, int y) {
		return tileset.get(levelDatas[getTileId(x, y)]);
	}

	public int getTileId(int x, int y) {
		return x + y * width;
	}

	public String getTilesetName() {
		return tilesetName;
	}

	public int getTileSize() {
		return tileSize;
	}

	public int getTileSizeIngame() {
		return tileSizeIngame;
	}

	public String getName() {
		return name;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getAnimationStep() {
		return animationStep;
	}

	public Player getPlayer() {
		return player;
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}

	public Target getCamera() {
		return camera;
	}

	public void setCamera(Target camera) {
		this.camera = camera;
	}

	public int getItemsCount() {
		return itemsCount;
	}

	public boolean isPassable(float x, float y, Direction dir) {
		if (!isOnLevel(x, y))
			return false;
		Tile tile = getTile((int) (x / tileSizeIngame), (int) (y / tileSizeIngame));
		if (tile != null) {
			return tile.canPass((int) (x % tileSizeIngame), (int) (y % tileSizeIngame), dir, getTileSizeIngame());
		}
		return true;
	}

	public boolean isOnLevel(float x, float y) {
		return (x >= 0 && y >= 0 && x < width * tileSizeIngame && y < height * tileSizeIngame);
	}

	public void update(Main main, Screen screen) {
		animationStep++;
		ArrayList<Entity> deletedEntities = new ArrayList<>();
		updateCollision();
		for (Entity entity : entities) {
			entity.update(main, screen);
			if (entity.isDisposed())
				deletedEntities.add(entity);
		}
		for (Entity entity : deletedEntities)
			entities.remove(entity);
		deletedEntities.clear();
		updateCam(screen);
	}

	private void updateCollision() {
		for (int entityId1 = 0; entityId1 < entities.size(); entityId1++) {
			Entity entity1 = entities.get(entityId1);
			Shape hitbox1 = entity1.getHitbox();
			for (int entityId2 = entityId1 + 1; entityId2 < entities.size(); entityId2++) {
				Entity entity2 = entities.get(entityId2);
				Shape hitbox2 = entity2.getHitbox();
				if (hitbox1.getBounds().intersects(hitbox2.getBounds())) {
					entity1.collisionWithEntity(entity2);
					entity2.collisionWithEntity(entity1);
				}
			}
		}
	}

	private void updateCam(Screen screen) {
		screen.ox = screen.getGameWidth() / 2 - camera.getX();
		screen.oy = screen.getGameHeight() / 2 - camera.getY();
		if (screen.ox > 0)
			screen.ox = 0;
		if (screen.oy > 0)
			screen.oy = 0;
		if (screen.getGameWidth() < width * tileSizeIngame) {
			int rightBorder = -width * tileSizeIngame + screen.getGameWidth();
			if (screen.ox < rightBorder)
				screen.ox = rightBorder;
		}
		if (screen.getGameHeight() < height * tileSizeIngame) {
			int bottomBorder = -height * tileSizeIngame + screen.getGameHeight();
			if (screen.oy < bottomBorder)
				screen.oy = bottomBorder;
		}

	}*/
}
