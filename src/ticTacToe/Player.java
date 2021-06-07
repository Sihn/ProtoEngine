package ticTacToe;

import java.awt.Color;

import core.Input;

public abstract class Player {
	public final String name;
	public int column;
	public int line;
	public Color color;
	private boolean havePlayed = false;

	public Player(String name, int column, int line, Color color) {
		this.name = name;
		this.column = column;
		this.line = line;
		this.color = color;
	}

	public final void reset(Grid grid) {
		havePlayed = false;
		start(grid);
	}

	public final void endTurn() {
		havePlayed = true;
	}

	public final boolean havePlayed() {
		return havePlayed;
	}

	public abstract void start(Grid grid);

	public abstract void update(Grid grid, Input input);
}
