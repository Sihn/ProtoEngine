package slick.tests.demos;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.command.BasicCommand;
import org.newdawn.slick.command.Command;
import org.newdawn.slick.command.ControllerButtonControl;
import org.newdawn.slick.command.ControllerDirectionControl;
import org.newdawn.slick.command.InputProvider;
import org.newdawn.slick.command.InputProviderListener;
import org.newdawn.slick.command.KeyControl;
import org.newdawn.slick.command.MouseButtonControl;

/**
 * A test for abstract input via InputProvider
 * 
 * @author kevin
 */
public class InputProviderTest extends BasicGame implements InputProviderListener {
	/** The command for attack */
	private Command attack = new BasicCommand("attack");
	/** The command for jump */
	private Command jump = new BasicCommand("jump");
	/** The command for left */
	private Command left = new BasicCommand("left");
	/** The command for right */
	private Command right = new BasicCommand("right");
	/** The input provider abstracting input */
	private InputProvider provider;
	/** The message to be displayed */
	private String message = "";

	/**
	 * Create a new image rendering test
	 */
	public InputProviderTest() {
		super("InputProvider Test");
	}

	/**
	 * @see org.newdawn.slick.BasicGame#init(org.newdawn.slick.GameContainer)
	 */
	public void init(GameContainer container) throws SlickException {
		provider = new InputProvider(container.getInput());
		provider.addListener(this);

		provider.bindCommand(new KeyControl(Input.KEY_LEFT), left);
		provider.bindCommand(new KeyControl(Input.KEY_Q), left);
		provider.bindCommand(new ControllerDirectionControl(0, ControllerDirectionControl.LEFT), left);

		provider.bindCommand(new KeyControl(Input.KEY_RIGHT), right);
		provider.bindCommand(new KeyControl(Input.KEY_D), right);
		provider.bindCommand(new ControllerDirectionControl(0, ControllerDirectionControl.RIGHT), right);

		provider.bindCommand(new KeyControl(Input.KEY_UP), jump);
		provider.bindCommand(new KeyControl(Input.KEY_Z), jump);
		provider.bindCommand(new KeyControl(Input.KEY_SPACE), jump);
		provider.bindCommand(new ControllerDirectionControl(0, ControllerDirectionControl.UP), jump);

		provider.bindCommand(new KeyControl(Input.KEY_LSHIFT), attack);
		provider.bindCommand(new KeyControl(Input.KEY_RSHIFT), attack);
		provider.bindCommand(new MouseButtonControl(0), attack);
		provider.bindCommand(new ControllerButtonControl(0, 1), attack);
	}

	/**
	 * @see org.newdawn.slick.BasicGame#update(org.newdawn.slick.GameContainer, int)
	 */
	public void update(GameContainer container, int delta) {}

	/**
	 * @see org.newdawn.slick.BasicGame#render(org.newdawn.slick.GameContainer, org.newdawn.slick.Graphics)
	 */
	public void render(GameContainer container, Graphics g) {
		g.drawString("Press Q, D, Z, Left, Right, Up, space, shift, mouse button 1, and gamepad controls.", 10, 50);
		g.drawString(message, 100, 150);
	}

	/**
	 * @see org.newdawn.slick.command.InputProviderListener#controlPressed(org.newdawn.slick.command.Command)
	 */
	public void controlPressed(Command command) {
		message = "Pressed: " + command;
	}

	/**
	 * @see org.newdawn.slick.command.InputProviderListener#controlReleased(org.newdawn.slick.command.Command)
	 */
	public void controlReleased(Command command) {
		message = "Released: " + command;
	}

	/**
	 * Entry point to our test
	 * 
	 * @param args
	 *            The arguments to pass into the test
	 */
	public static void main(String[] args) {
		try {
			new AppGameContainer(new InputProviderTest(), 640, 480, false).start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
