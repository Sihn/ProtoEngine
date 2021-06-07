package loops;

import java.io.File;

import level.Level;
import level.LevelLoader;
import level.Sky;
import level.TileLayer.Position;
import sprites.SpriteEntity;
import sprites.SpriteHud;
import sprites.SpriteLevel;
import sprites.SpriteLevelName;
import sprites.SpriteSky;
import core.BaseLoop;
import core.Input;
import core.Log;
import core.Main;
import core.ParticlesEngine;
import core.Screen;
import core.tools.ExtensionFilter;
import core.tools.RandomColor;
import entities.Entity;
import entities.Player;
import entities.State;
import exceptions.BadPassabilityException;
import exceptions.LevelFileNotFoundException;
import exceptions.PlayerNotSetException;
import exceptions.PropertieNotSetException;

public class GameLoop extends BaseLoop {
	private static File[] Levels = new File(LevelLoader.levelsFolder).listFiles(new ExtensionFilter(LevelLoader.levelsExtension));
	private static int currentLevel = 0;
	private Level level;
	private Player player;
	private Sky sky;
	private SpriteHud hud;

	public GameLoop() {
		this(currentLevel);
	}

	public GameLoop(int level) {
		currentLevel = level;
	}

	@Override
	public void start(Main main, Screen screen) {
		if (currentLevel >= Levels.length) {
			main.setLoop(null);
			return;
		}
		try {
			level = new Level(Levels[currentLevel]);
			player = level.getPlayer();
			player.spawn();
			sky = new Sky(level.getSkyColors());
			screen.addDrawable(new SpriteSky(sky));
			for (Position position : Position.values())
				screen.addDrawable(new SpriteLevel(level, position));
			for (Entity entity : level.getEntities())
				screen.addDrawable(new SpriteEntity(entity));
			hud = new SpriteHud(level, player);
			screen.addDrawable(hud);
			screen.addDrawable(new SpriteLevelName(level.getName()));
		} catch (LevelFileNotFoundException | PropertieNotSetException | PlayerNotSetException | BadPassabilityException e) {
			Log.error("Impossible de charger le niveau \"" + Levels[currentLevel] + "\".");
			main.setLoop(new GameLoop(currentLevel + 1));
		}
	}

	@Override
	public void update(Main main, Screen screen, Input input) {
		if (input.isPressed(Input.Cancel))
			main.stop();
		if (input.isPressed(Input.Reset))
			main.setLoop(new GameLoop(0));
		screen.setGameSize(screen.getGameHeight() + input.getWheel() * 10);
		if (input.isPressed(Input.Inventory)) {
			ParticlesEngine.addCircles(input.getX(), input.getY(), 20, 20, 4, 20, true);
			for (int i = 0; i < sky.getColors().size(); i++)
				sky.setColor(i, Main.fps, RandomColor.getColor());
		}
		if (input.isStillPressed(Input.Attack2)) {
			if (!player.isProtected())
				player.addDamage(1);
			String particleName = "";
			if (input.isStillPressed(Input.Delta))
				particleName = "mana.png";
			else if (input.isStillPressed(Input.Epsilon))
				particleName = "flare.png";
			else
				particleName = "flare2.png";
			ParticlesEngine.addParticles(particleName, input.getX(), input.getY(), 10, 5, 0, -2, 1, true);
		}
		sky.update();
		hud.update();
		if (player.state == State.Normal)
			player.input(input.get8Dir(), input.isStillPressed(Input.Jump), input.isStillPressed(Input.Debug), input.isStillPressed(Input.Attack1));
		level.update(main, screen);
		changeCamera(input);
		ParticlesEngine.updateParticles();
		if (input.isPressed(Input.Omega) || player.getItemsFound() == level.getItemsCount())
			main.setLoop(new GameLoop(currentLevel + 1));
	}

	private void changeCamera(Input input) {
		int index = level.getEntities().indexOf(level.getCamera());
		if (input.isPressed(Input.Alpha))
			index--;
		if (input.isPressed(Input.Beta))
			index = level.getEntities().indexOf(player);
		if (input.isPressed(Input.Gamma))
			index++;
		if (index != level.getEntities().indexOf(level.getCamera())) {
			if (index < 0)
				index = level.getEntities().size() - 1;
			if (index >= level.getEntities().size())
				index = 0;
			level.setCamera(level.getEntities().get(index));
		}
	}
}
