package engineV2.exemple;

import java.awt.Color;

import engineV2.Image;
import engineV2.Input;
import engineV2.Loop;
import engineV2.Main;
import engineV2.Screen;

public class TestLoop extends Loop {
	private Image fairy1, fairy2, cat;
	private final static boolean zoomIn = true;
	private final static boolean zoomOut = false;
	private boolean zoom = zoomOut;
	private final static double zoomPower = .01;
	private int currentStep = 0;
	private int animeStep = 10;

	@Override
	public void start(Screen screen) {
		fairy1 = new Image("entities/fairy.png", 128, 64, 10, 4, 2);
		fairy2 = new Image("entities/fairy.png", 256, 64, 12, 4, 2);
		fairy2.line = 1;
		fairy2.alpha = (float) 0.5;
		cat = new Image("entities/cat.png", 64, 128, 11, 4, 2);
		cat.line = 1;
		screen.addSprite(new Background(screen, new Color(0x000008), new Color(0x0000FF), new Color(0x0080FF), new Color(0x00FFFF), new Color(0x80FFFF), new Color(0xFFFFFF)));
		screen.addSprite(fairy1);
		screen.addSprite(fairy2);
		screen.addSprite(cat);
	}

	@Override
	public void update(Main main, Screen screen, Input input) {
		if (input.isPressed(Input.Cancel)) {
			main.stop();
			return;
		}
		if (input.isPressed(Input.Reset)) {
			main.setLoop(new TestLoop());
			return;
		}

		if (input.isStillPressed(Input.Up))
			cat.y -= 2;
		if (input.isStillPressed(Input.Down))
			cat.y += 2;
		if (input.isStillPressed(Input.Left))
			cat.x -= 2;
		if (input.isStillPressed(Input.Right))
			cat.x += 2;
		cat.zoom(-input.getWheel());
		currentStep++;
		if (currentStep % animeStep == 0) {
			fairy1.column++;
			fairy2.column++;
			cat.column++;
		}
		fairy1.rotate(1);
		fairy2.zoom(zoom == zoomOut ? zoomPower : -zoomPower);
		// cat.rotate(-1);
		// cat.scale += zoom == zoomOut ? zoomPower : -zoomPower;
		if (fairy2.getScale() >= 2)
			zoom = zoomIn;
		if (fairy2.getScale() <= .5)
			zoom = zoomOut;
	}
}
