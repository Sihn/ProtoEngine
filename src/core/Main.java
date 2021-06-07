package core;

import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

public final class Main extends Frame {
	private final static long serialVersionUID = 1L;

	public static boolean Debug = false;
	public final static int fps = 80;

	private String title;
	private final Screen screen;
	private final Input input;

	private int refreshCount = 0;
	private BufferStrategy strategy;
	private BaseLoop loop;

	public Main(String title, int screenWidth, int screenHeight, int gameWidth, int gameHeight) {
		super(title);
		this.title = title;
		screen = new Screen(screenWidth, screenHeight, gameWidth, gameHeight);
		add(screen);
		pack();
		setLocationRelativeTo(null);
		ParticlesEngine.screen = screen;
		input = new Input(screen);
		Synthe.initialize();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				stop();
			}
		});
	}

	private void run(BaseLoop startLoop) {
		try {
			setVisible(true);
			screen.createBufferStrategy(3);
			strategy = screen.getBufferStrategy();
			loop = startLoop;
			while (loop != null)
				loop.run(this, screen, input);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Synthe.close();
			dispose();
		}
	}

	public void updateTitle() {
		super.setTitle(title + " - " + refreshCount + " FPS");
		refreshCount = 0;
	}

	public BaseLoop getLoop() {
		return loop;
	}

	public void setLoop(BaseLoop loop) {
		this.loop = loop;
	}

	public void stop() {
		this.loop = null;
	}

	public void render() {
		screen.render((Graphics2D) strategy.getDrawGraphics());
		strategy.show();
		refreshCount++;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	public static void main(String[] args) {
		String title = "Vagabond";
		Log.debug("===============================================");
		Log.debug("== " + title + " ==");
		Log.debug("===============================================");
		int resolution = 720;
		for (String arg : args) {
			switch (arg.toUpperCase()) {
			case "DEBUG":
				Main.Debug = true;
				break;
			}
		}
		Main main = new Main(title, (int) (resolution * Screen.ratio), resolution, (int) (resolution * Screen.ratio), resolution);
		main.run(new StartMenuLoop());
		Log.debug("===============================================");
		Log.debug("");
	}
}
