package level;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

import level.Tile.Passability;
import level.TileLayer.Position;
import entities.Croacroa;
import entities.Entity;
import entities.Player;
import entities.Scroll;
import entities.Shard;
import exceptions.BadPassabilityException;
import exceptions.LevelFileNotFoundException;
import exceptions.PlayerNotSetException;
import exceptions.PropertieNotSetException;

public class LevelLoader {
	public final static String levelsFolder = "levels/";
	public final static String levelsExtension = ".lvl";
	public final static String tilesetExtension = ".tileset";

	private HashMap<String, String> properties = new HashMap<>();
	private HashMap<Integer, Tile> tileset = new HashMap<>();
	private int width;
	private int height;
	private int[] levelDatas;
	private Player player;
	private ArrayList<Entity> entities = new ArrayList<>();
	private int itemsCount = 0;

	public LevelLoader(File levelFile, Level level) throws LevelFileNotFoundException, PlayerNotSetException, BadPassabilityException, PropertieNotSetException {
		loadParameters(levelFile);
		loadDatas(level);
	}

	private void loadParameters(File levelFile) throws LevelFileNotFoundException {
		String parameters = setupDatas(levelsFolder + levelFile.getName());
		for (String dataLine : parameters.split(";")) {
			if (dataLine.isEmpty())
				continue;
			String[] tileDatas = dataLine.split(":");
			if (tileDatas.length == 2)
				properties.put(tileDatas[0].toLowerCase(), tileDatas[1]);
		}
	}

	private void loadDatas(Level level) throws LevelFileNotFoundException, PropertieNotSetException, PlayerNotSetException {
		String datas = setupDatas(levelsFolder + getProperties("tileset_data") + tilesetExtension);
		BufferedImage image = setupImage(levelsFolder + getProperties("image"));
		width = image.getWidth();
		height = image.getHeight();
		levelDatas = image.getRGB(0, 0, width, height, null, 0, width);
		for (int i = 0; i < levelDatas.length; i++)
			levelDatas[i] = levelDatas[i] & 0xffffff;
		for (String dataLine : datas.split(";")) {
			if (dataLine.isEmpty())
				continue;
			String[] tileDatas = dataLine.split(":");
			if (tileDatas.length == 3)
				setEntity(tileDatas, level);
			else
				setTile(tileDatas);
		}
	}

	private boolean containChar(char[] chars, char c) {
		for (char c2 : chars) {
			if (c2 == c)
				return true;
		}
		return false;
	}

	private String setupDatas(String path) throws LevelFileNotFoundException {
		try (FileReader fr = new FileReader(path); BufferedReader br = new BufferedReader(fr)) {
			String datas = "";
			String lines = "";
			String fileLine;
			// Suppression des commentaires
			while ((fileLine = br.readLine()) != null)
				lines += fileLine.split("#")[0];
			// Suppression des espaces, tabulations, et retour à la ligne ne se trouvant pas dans des guillemets
			char[] markups = { '\"', '\'' };
			char[] deletes = { ' ', '	', '\r', '\n' };
			Character currentMarkup = null;
			for (char c : lines.toCharArray()) {
				if (currentMarkup == null) {
					if (containChar(markups, c))
						currentMarkup = c;
					else if (!containChar(deletes, c))
						datas += c;
				} else {
					if (c == currentMarkup)
						currentMarkup = null;
					else
						datas += c;
				}
			}
			return datas;
		} catch (IOException e) {
			throw new LevelFileNotFoundException(path);
		}
	}

	private BufferedImage setupImage(String path) throws LevelFileNotFoundException {
		try {
			return ImageIO.read(new File(path));
		} catch (IOException e) {
			throw new LevelFileNotFoundException(path);
		}
	}

	private void setEntity(String[] tileDatas, Level level) throws PlayerNotSetException, NumberFormatException, PropertieNotSetException {
		int baseColor = Integer.parseInt(tileDatas[0], 16);
		int tileColor = Integer.parseInt(tileDatas[2], 16);
		int tileSizeIngame = Integer.parseInt(getProperties("tile_size_ingame"));
		for (int i = 0; i < levelDatas.length; i++) {
			if (levelDatas[i] == baseColor) {
				levelDatas[i] = tileColor;
				int x = i % width * tileSizeIngame + tileSizeIngame / 2;
				int y = i / width * tileSizeIngame + tileSizeIngame;
				switch (tileDatas[1].toLowerCase()) {
				case "player":
					player = new Player(x, y, level);
					entities.add(player);
					break;
				case "croacroa":
					entities.add(new Croacroa(x, y, level));
					break;
				case "scroll":
					entities.add(new Scroll(x, y, level));
					itemsCount++;
					break;
				case "shard":
					entities.add(new Shard(x, y, level));
					itemsCount++;
					break;
				}
			}
		}
		if (player == null)
			throw new PlayerNotSetException();
	}

	private void setTile(String[] tileDatas) throws BadPassabilityException, PropertieNotSetException {
		int color = Integer.parseInt(tileDatas[0], 16);
		int tileSize = Integer.parseInt(getProperties("tile_size_ingame"));
		Tile tile = new Tile(tileSize);
		tile.name = tileDatas[1];
		tile.layers = parseLayers(tileDatas[2]);
		try {
			tile.passable = Passability.valueOf(tileDatas[3]);
			switch (tile.passable) {
			case CannotPass:
			case TopBlocked:
				tile.changeBorders(0, 0);
				break;
			case DownToUp:
				tile.changeBorders(tileSize, 0);
				break;
			case UpToDown:
				tile.changeBorders(0, tileSize);
				break;
			default:
				break;
			}
		} catch (IllegalArgumentException e) {
			throw new BadPassabilityException(tileDatas[3]);
		}
		tile.liquid = Boolean.parseBoolean(tileDatas[4]);
		tile.damage = Integer.parseInt(tileDatas[5]);
		tileset.put(color, tile);
	}

	private ArrayList<TileLayer> parseLayers(String layersDatas) {
		ArrayList<TileLayer> layers = new ArrayList<>();
		for (String layerDatas : layersDatas.split("/")) {
			String[] datas = layerDatas.split(",");
			if (datas.length >= 3) {
				TileLayer layer = new TileLayer();
				layer.position = Position.valueOf(datas[0]);
				layer.line = Integer.parseInt(datas[1]);
				layer.column = Integer.parseInt(datas[2]);
				if (datas.length >= 4)
					layer.animations = Integer.parseInt(datas[3]);
				layers.add(layer);
			}
		}
		return layers;
	}

	public HashMap<Integer, Tile> getTileset() {
		return tileset;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int[] getLevelDatas() {
		return levelDatas;
	}

	public Player getPlayer() {
		return player;
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}

	public int getItemsCount() {
		return itemsCount;
	}

	public String getProperties(String key) throws PropertieNotSetException {
		key = key.toLowerCase();
		if (properties.containsKey(key))
			return properties.get(key);
		throw new PropertieNotSetException(key);
	}
}
