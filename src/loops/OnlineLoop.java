package loops;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import level.Sky;
import online.Config;
import online.client.Client;
import online.server.Server;
import sprites.SpriteSky;
import sprites.particles.ParticleStar.StarColor;
import core.BaseLoop;
import core.Input;
import core.Log;
import core.Main;
import core.ParticlesEngine;
import core.Screen;
import core.tools.RandomColor;

public class OnlineLoop extends BaseLoop {
	private Server server;
	private ArrayList<Client> clients = new ArrayList<>();

	private Sky sky;
	private final int changeDuration = Main.fps * 2;
	private int change = 0;

	private final int durationStarsMax = Main.fps / 2;
	private final int durationStarsAnim = durationStarsMax / 2;
	private int durationStars = 0;

	@Override
	public void start(Main main, Screen screen) {
		try {
			server = new Server(Config.port);
			server.run();
		} catch (IOException e) {
			server = null;
			Log.error(e.getMessage());
		}
		sky = new Sky(new Color[] { RandomColor.getSaturatedColor(), Color.BLACK });
		screen.addDrawable(new SpriteSky(sky));
	}

	@Override
	public void update(Main main, Screen screen, Input input) {
		if (server == null) {
			main.stop();
			return;
		}
		if (input.isPressed(Input.Cancel))
			main.stop();
		if (input.isPressed(Input.Reset))
			main.setLoop(new OnlineLoop());
		sky.update();
		if (change <= 0) {
			change = changeDuration;
			sky.setColor(0, changeDuration, RandomColor.getSaturatedColor());
		}
		change--;
		if (durationStars % durationStarsMax == 0) {
			for (int i = 0; i < clients.size(); i++)
				ParticlesEngine.addStars((int) (Math.random() * screen.gameWidth), (int) (Math.random() * screen.gameHeight), durationStarsAnim, 40, 4, StarColor.RANDOM, 1, true);
		}
		durationStars++;
		ParticlesEngine.updateParticles();

		if (input.isPressed(Input.N1))
			clients.add(Client.createClient());
		if (clients.size() > 0) {
			Client client = clients.get(clients.size() - 1);
			if (input.isPressed(Input.N2)) {
				client.sendTcp(client.name.getBytes());
			}
			if (input.isPressed(Input.N3)) {
				client.close();
				clients.remove(client);
			}
		}
	}

	@Override
	public void stop(Main main, Screen screen) {
		super.stop(main, screen);
		for (Client client : clients) {
			Log.info("Client \"" + client.name + "\" stopped.");
			client.close();
		}
		try {
			if (server != null)
				server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
