package sprites;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import windows.SelectionWindow;
import core.Screen;
import core.tools.ZIndex;

public class SpriteSelectionWindow extends Sprite {
	private SelectionWindow window;
	private Font font;

	public SpriteSelectionWindow(SelectionWindow window) {
		super(ZIndex.HUD, true);
		this.window = window;
		font = new Font(Screen.BASE_FONT.getFontName(), Font.PLAIN, 22);
	}

	@Override
	public Rectangle getRect() {
		return null;
	}

	@Override
	public void render(Graphics2D g, Screen screen) {
		String[] values = window.getValues();
		FontMetrics metrics = g.getFontMetrics(font);
		g.setFont(font);
		int height = metrics.getHeight();
		int totalHeight = height * values.length;
		for (int i = 0; i < values.length; i++) {
			int x = screen.gameWidth / 2 - metrics.stringWidth(values[i]) / 2;
			int y = screen.gameHeight / 2 - totalHeight / 2 + height * i;
			if (window.getCurrentValue() == i)
				drawText(g, x, y, values[i], Color.YELLOW);
			else
				drawText(g, x, y, values[i]);
		}
	}
}
