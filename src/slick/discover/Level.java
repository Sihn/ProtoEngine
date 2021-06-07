package slick.discover;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Level {
	final LevelData levelData;
	//final int width;
	//final int height;
	public Image spriteSheet;

	public Level(LevelData levelData) {
		this.levelData = levelData;
		try {
			spriteSheet = new Image("src/slick/discover/resources/tileset.png", false, Image.FILTER_NEAREST);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		//width = ???.getWidth();
	}
}
