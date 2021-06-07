package ticTacToe;

import java.awt.Color;
import java.awt.Point;

import core.tools.ChangingColor;

public class Grid {
	private Player[] tiles;
	private int size;
	private int currentTile = 0;
	private ChangingColor background;

	public Grid(int size) {
		tiles = new Player[size * size];
		this.size = size;
		background = new ChangingColor(Color.BLACK);
	}

	public void update() {
		background.update();
	}

	public Player[] getTiles() {
		return tiles;
	}

	public int getSize() {
		return size;
	}

	public int currentTile() {
		return currentTile;
	}

	public Color getBackgroundColor() {
		return background.getColor();
	}

	public void setBackgroundColor(Color color) {
		background.setColor(color, 80);
	}

	public Point getPosition() {
		return new Point(currentTile % size, currentTile / size);
	}

	public void setPosition(int x, int y) {
		while (x < 0)
			x += size;
		while (y < 0)
			y += size;
		while (x >= size)
			x -= size;
		while (y >= size)
			y -= size;
		currentTile = x + y * size;
	}

	public Player getTile(int x, int y) {
		return tiles[x + y * size];
	}

	public void moveUp() {
		Point p = getPosition();
		setPosition(p.x, p.y - 1);
	}

	public void moveDown() {
		Point p = getPosition();
		setPosition(p.x, p.y + 1);
	}

	public void moveLeft() {
		Point p = getPosition();
		setPosition(p.x - 1, p.y);
	}

	public void moveRight() {
		Point p = getPosition();
		setPosition(p.x + 1, p.y);
	}

	public boolean conquest(Player player) {
		if (tiles[currentTile] != null)
			return false;
		tiles[currentTile] = player;
		return true;
	}
}
