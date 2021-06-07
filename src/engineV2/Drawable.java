package engineV2;

import java.awt.Graphics2D;

public interface Drawable {
	public int getZ();

	public void render(Graphics2D g, Screen screen);
}
