package engineV2;

public abstract class Loop {
	private final int Second = 1000000000;
	private final int fps = 80;
	private final double GameSpeed = Second / fps;
	private final double DrawingSpeed = Second / fps;

	public void run(Main main, Screen screen, Input input) {
		final int MaxUpdatesBeforeRender = 5;
		double lastUpdateTime = System.nanoTime();
		double lastRenderTime = System.nanoTime();
		int lastSecondTime = (int) (lastUpdateTime / Second);
		start(screen);
		while (main.getLoop() == this) {
			double now = System.nanoTime();
			int updateCount = 0;
			while (now - lastUpdateTime > GameSpeed && updateCount < MaxUpdatesBeforeRender) {
				// ---------------------------------------------
				// Mise à jour de la logique du jeu
				// ---------------------------------------------
				update(main, screen, input);
				lastUpdateTime += GameSpeed;
				updateCount++;
			}
			if (now - lastUpdateTime > GameSpeed)
				lastUpdateTime = now - GameSpeed;
			// ---------------------------------------------
			// Mise à jour de l'affichage
			// ---------------------------------------------
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
		screen.removeDrawables();
	}

	public abstract void start(Screen screen);

	public abstract void update(Main main, Screen screen, Input input);
}
