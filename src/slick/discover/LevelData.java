package slick.discover;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class LevelData {
	private BufferedImage image;

	public LevelData(String levelName) {
		try {
			image = ImageIO.read(new File("src/slick/discover/resources/levels/" + levelName + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
			image = null;
		}
	}

	public int getTile(int x, int y) {
		return image.getRGB(x, y);
	}
}
