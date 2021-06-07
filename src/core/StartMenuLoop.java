package core;

import java.awt.Color;
import java.io.File;
import java.lang.reflect.Constructor;

import level.Sky;
import sprites.SpriteSelectionWindow;
import sprites.SpriteSky;
import windows.SelectionWindow;
import core.tools.ExtensionFilter;
import core.tools.RandomColor;

public class StartMenuLoop extends BaseLoop {
	private final static String loopPackage = "loops";
	private final static String javaExt = ".java";
	private SelectionWindow window;
	private Sky sky;
	private final int changeDuration = Main.fps * 2;
	private int change = 0;

	@Override
	public void start(Main main, Screen screen) {
		File[] files = new File("src/" + loopPackage + "/").listFiles(new ExtensionFilter(javaExt));
		String[] list = new String[files.length];
		for (int i = 0; i < files.length; i++) {
			String filename = files[i].getName();
			list[i] = filename.substring(0, filename.length() - javaExt.length());
			Log.debug("Added : " + list[i]);
		}
		window = new SelectionWindow(list);
		screen.addDrawable(new SpriteSelectionWindow(window));
		Color c1 = RandomColor.getSaturatedColor();
		Color c2 = RandomColor.getSaturatedColor();
		sky = new Sky(new Color[] { c1, c2, c1 });
		screen.addDrawable(new SpriteSky(sky));
	}

	@Override
	public void update(Main main, Screen screen, Input input) {
		if (input.isPressed(Input.Cancel))
			main.stop();
		sky.update();
		if (input.isPressed(Input.Up)) {
			window.previous();
		} else if (input.isPressed(Input.Down)) {
			window.next();
		} else if (input.isPressed(Input.Validate)) {
			try {
				Class<? extends BaseLoop> loopClass = Class.forName(loopPackage + "." + window.getValue()).asSubclass(BaseLoop.class);
				Constructor<? extends BaseLoop> loopConstructor = loopClass.getConstructor(loopClass.getClasses());
				BaseLoop loop = loopConstructor.newInstance(new Object[0]);
				main.setLoop(loop);
			} catch (Exception e) {
				Log.error(e.getMessage());
			}
		}
		if (change <= 0) {
			change = changeDuration;
			Color c1 = RandomColor.getSaturatedColor();
			Color c2 = RandomColor.getSaturatedColor();
			sky.setColors(changeDuration, c1, c2, c1);
		}
		change--;
	}
}
