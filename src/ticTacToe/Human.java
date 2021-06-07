package ticTacToe;

import java.awt.Color;

import core.Input;

public class Human extends Player {
	public Human(String name, int column, int line, Color color) {
		super(name, column, line, color);
	}

	@Override
	public void start(Grid grid) {
		grid.setBackgroundColor(color);
	}

	@Override
	public void update(Grid grid, Input input) {
		if (input.isPressed(Input.Up))
			grid.moveUp();
		else if (input.isPressed(Input.Down))
			grid.moveDown();
		else if (input.isPressed(Input.Left))
			grid.moveLeft();
		else if (input.isPressed(Input.Right))
			grid.moveRight();
		if (input.isPressed(Input.Validate))
			if (grid.conquest(this))
				endTurn();
	}
}
