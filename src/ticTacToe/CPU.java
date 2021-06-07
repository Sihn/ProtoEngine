package ticTacToe;

import java.awt.Color;

import core.Input;

public class CPU extends Player {
	private final int fakeThinkingDuration = 100;
	private int fakeThinking = 0;

	public CPU(String name, int column, int line, Color color) {
		super(name, column, line, color);
	}

	@Override
	public void start(Grid grid) {
		grid.setBackgroundColor(color);
		fakeThinking = fakeThinkingDuration;
	}

	@Override
	public void update(Grid grid, Input input) {
		if (fakeThinking > 0) {
			fakeThinking--;
		} else {
			grid.setPosition((int) (Math.random() * grid.getSize()), (int) (Math.random() * grid.getSize()));
			if (grid.conquest(this))
				endTurn();
		}
	}
}
