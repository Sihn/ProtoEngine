package loops;

import java.awt.Color;

import ticTacToe.CPU;
import ticTacToe.Grid;
import ticTacToe.Human;
import ticTacToe.Player;
import ticTacToe.SpriteGrid;
import ticTacToe.SpriteResult;
import core.BaseLoop;
import core.Input;
import core.Main;
import core.Screen;
import core.tools.RandomColor;

public class TicTacToeLoop extends BaseLoop {
	public final Player player1 = new Human("Rouge", 1, 0, Color.RED);
	public final Player player2 = new Human("Cyan", 2, 0, Color.CYAN);
	public final Player player3 = new Human("Magenta", 3, 0, Color.MAGENTA);
	public final Player player4 = new Human("Bleu", 1, 1, Color.BLUE);
	public final Player player5 = new Human("Jaune", 2, 1, Color.YELLOW);
	public final Player player6 = new Human("Vert", 3, 1, Color.GREEN);
	public final Player player7 = new Human("Blanc", 1, 2, Color.WHITE);
	public final Player player8 = new Human("Or", 2, 2, Color.ORANGE);
	public final Player player9 = new Human("Noir", 3, 2, Color.BLACK);
	public final Player player0 = new CPU("Aléatoire", 0, 2, RandomColor.getColor());
	private Grid grid;
	private Player[] players = new Player[2];
	private int currentPlayer;
	private final int resultDuration = 240;
	private int countdown = 0;

	@Override
	public void start(Main main, Screen screen) {
		main.setTitle("Tic-Tac-Toe-en-Toc");
		grid = new Grid(3);
		screen.addDrawable(new SpriteGrid(grid, 32));
		players[0] = player2;
		players[1] = player3;
		currentPlayer = (int) (Math.random() * players.length);
		getPlayer().reset(grid);
	}

	@Override
	public void update(Main main, Screen screen, Input input) {
		if (input.isPressed(Input.Cancel))
			main.stop();
		if (input.isPressed(Input.Reset))
			main.setLoop(new TicTacToeLoop());
		if (countdown > 0) {
			countdown--;
			if (countdown == 0)
				main.setLoop(new TicTacToeLoop());
		} else {
			grid.update();
			Player player = getPlayer();
			player.update(grid, input);
			if (player.havePlayed()) {
				if (haveWinner()) {
					setResult(screen, player);
				} else if (haveDraw()) {
					setResult(screen, null);
					grid.setBackgroundColor(Color.BLACK);
				} else {
					setNextPlayer();
				}
			}
		}
	}

	public Player getPlayer() {
		return players[currentPlayer];
	}

	public void setNextPlayer() {
		currentPlayer++;
		while (currentPlayer >= players.length)
			currentPlayer -= players.length;
		getPlayer().reset(grid);
	}

	public boolean haveWinner() {
		int size = grid.getSize();
		Player[] line;

		line = new Player[size];
		for (int i = 0; i < size; i++)
			line[i] = grid.getTile(i, i);
		if (checkLine(line))
			return true;

		line = new Player[size];
		for (int i = 0; i < size; i++)
			line[i] = grid.getTile(i, size - 1 - i);
		if (checkLine(line))
			return true;

		for (int x = 0; x < size; x++) {
			line = new Player[size];
			for (int y = 0; y < size; y++) {
				line[y] = grid.getTile(x, y);
				if (checkLine(line))
					return true;
			}
		}

		for (int y = 0; y < size; y++) {
			line = new Player[size];
			for (int x = 0; x < size; x++) {
				line[x] = grid.getTile(x, y);
				if (checkLine(line))
					return true;
			}
		}
		return false;
	}

	private boolean checkLine(Player[] line) {
		Player p1 = line[0];
		if (p1 == null)
			return false;
		for (int i = 1; i < line.length; i++) {
			Player p2 = line[i];
			if (p2 != null && p1 == p2)
				p1 = p2;
			else
				return false;
		}
		return true;
	}

	public boolean haveDraw() {
		for (Player player : grid.getTiles())
			if (player == null)
				return false;
		return true;
	}

	public void setResult(Screen screen, Player winner) {
		countdown = resultDuration;
		String message = (winner == null) ? "Égalité." : "Vainqueur : " + winner.name;
		screen.addDrawable(new SpriteResult(message));
	}
}
