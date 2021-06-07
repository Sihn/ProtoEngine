package ticTacToe;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import sprites.Sprite;
import core.Screen;

public class SpriteResult extends Sprite {
	private final String message;
	private final int ox = 0;
	private final int oy = -32;

	public SpriteResult(String message) {
		super(10, true);
		this.message = message;
	}

	@Override
	public Rectangle getRect() {
		return new Rectangle();
	}

	@Override
	public void render(Graphics2D g, Screen screen) {
		Font font = new Font("Tahoma", Font.PLAIN, 28);
		FontMetrics metrics = g.getFontMetrics(font);
		int x = screen.getGameWidth() / 2 - metrics.stringWidth(message) / 2;
		int y = screen.getGameHeight() / 2 - metrics.getHeight() / 2;
		g.setFont(font);
		drawText(g, x + ox, y + oy, "- " + message + " -", Screen.FONT_COLOR, true);
	}
}
