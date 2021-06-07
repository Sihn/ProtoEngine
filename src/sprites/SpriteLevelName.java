package sprites;

import java.awt.AlphaComposite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import core.Screen;
import core.tools.ZIndex;

public class SpriteLevelName extends Sprite {
	private String name;
	private final int ox = 0;
	private final int oy = -32;
	private final int durationMax = 150;
	private final int fadeDuration = 80;
	private int duration = 0;

	public SpriteLevelName(String name) {
		super(ZIndex.fullscreen, true);
		this.name = name;
	}

	@Override
	public void render(Graphics2D g, Screen screen) {
		duration++;
		if (duration >= durationMax) {
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1 - (duration - durationMax) / (float) fadeDuration));
		}
		Font font = new Font(Screen.BASE_FONT.getFontName(), Font.ITALIC, 14);
		FontMetrics metrics = g.getFontMetrics(font);
		int x = screen.getGameWidth() / 2 - metrics.stringWidth(name) / 2;
		int y = screen.getGameHeight() / 2 - metrics.getHeight() / 2;
		g.setFont(font);
		drawText(g, x + ox, y + oy, "- " + name + " -", Screen.FONT_COLOR, true);
	}

	@Override
	public boolean isDead() {
		return duration >= durationMax + fadeDuration;
	}

	@Override
	public Rectangle getRect() {
		return null;
	}
}
