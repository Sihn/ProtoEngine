package engineV2;

import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import engineV2.exemple.TestLoop;

public final class Main extends JFrame {
	private static final long serialVersionUID = 1L;
	public static final int resolution = 720;
	public static final double ratio = 16.0 / 9;

	private String title;
	private Screen screen;
	private BufferStrategy strategy;
	private Input input;

	private int refreshCount = 0;
	private Loop loop;

	public Main(String title, int ingameWidth, int ingameHeight, int trueWidth, int trueHeight) {
		super(title);
		this.title = title;
		screen = new Screen(ingameWidth, ingameHeight, trueWidth, trueHeight);
		add(screen);
		pack();
		setLocationRelativeTo(null);
		input = new Input(screen);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				stop();
			}
		});
	}

	public void start(Loop startLoop) {
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
			dispose();
		}
	}

	public void updateTitle() {
		setTitle(title + " - " + refreshCount + " FPS");
		refreshCount = 0;
	}

	public Loop getLoop() {
		return loop;
	}

	public void setLoop(Loop loop) {
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

	public static void main(String[] args) {
		int width = (int) (resolution * ratio);
		int height = resolution;
		Main main = new Main("Magus's Cat", width, height, width, height);
		main.start(new TestLoop());
	}
}
