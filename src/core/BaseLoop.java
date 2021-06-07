package core;

public abstract class BaseLoop {
	private final int Second = 1000000000;

	public final void run(Main main, Screen screen, Input input) {
		final double GameSpeed = Second / Main.fps;
		final double DrawingSpeed = Second / Main.fps;
		final int MaxUpdatesBeforeRender = 5;
		double lastUpdateTime = System.nanoTime();
		double lastRenderTime = System.nanoTime();
		int lastSecondTime = (int) (lastUpdateTime / Second);
		start(main, screen);
		while (main.getLoop() == this) {
			double now = System.nanoTime();
			int updateCount = 0;
			while (now - lastUpdateTime > GameSpeed && updateCount < MaxUpdatesBeforeRender) {
				// Mise à jour des sons MIDI
				Synthe.update();
				// Mise à jour de la logique du jeu
				update(main, screen, input);
				lastUpdateTime += GameSpeed;
				updateCount++;
			}
			if (now - lastUpdateTime > GameSpeed)
				lastUpdateTime = now - GameSpeed;
			// Mise à jour de l'affichage
			main.render();
			lastRenderTime = now;
			int thisSecond = (int) (lastUpdateTime / Second);
			if (thisSecond > lastSecondTime) {
				main.updateTitle();
				lastSecondTime = thisSecond;
			}
			while (now - lastRenderTime < DrawingSpeed && now - lastUpdateTime < GameSpeed) {
				Thread.yield();
				try {
					Thread.sleep(1);
				} catch (Exception e) {}
				now = System.nanoTime();
			}
		}
		stop(main, screen);
	}

	public abstract void start(Main main, Screen screen);

	public abstract void update(Main main, Screen screen, Input input);

	public void stop(Main main, Screen screen) {
		screen.removeDrawables();
	}
}
