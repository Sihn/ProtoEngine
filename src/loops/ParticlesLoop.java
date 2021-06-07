package loops;

import sprites.SpriteSizeBar;
import sprites.SpriteSoundBackground;
import sprites.particles.ParticleStar.StarColor;
import core.BaseLoop;
import core.Input;
import core.Main;
import core.ParticlesEngine;
import core.Screen;
import core.Synthe;

public class ParticlesLoop extends BaseLoop {
	final int MinSize = 4;
	final int MaxSize = 30;
	int size = MinSize;

	final int NoteMin = 40;
	final int NoteMax = 100;

	final int MinVelocity = 30;
	final int MaxVelocity = 100;

	int lastNote = 0;
	boolean autoMode = false;

	@Override
	public void start(Main main, Screen screen) {
		Synthe.program(0, "Music Box");
		Synthe.program(1, "Crystal");
		Synthe.program(2, "Piano 1");
		screen.addDrawable(new SpriteSoundBackground(NoteMin, NoteMax));
		screen.addDrawable(new SpriteSizeBar(this));
	}

	@Override
	public void update(Main main, Screen screen, Input input) {
		if (input.isPressed(Input.Gold))
			autoMode = !autoMode;
		size -= input.getWheel() * 2;
		if (size < MinSize)
			size = MinSize;
		if (size > MaxSize)
			size = MaxSize;
		int maxWidth = screen.getGameWidth();
		int x = Math.max(0, Math.min(input.getX(), screen.getGameWidth() - 1));
		int y = Math.max(0, Math.min(input.getY(), screen.getGameWidth() - 1));
		float position = x;
		int note = (int) (position / maxWidth * (NoteMax - NoteMin + 1) + NoteMin);
		int velocity = getVelocity();
		if (autoMode) {
			if (input.isStillPressed(Input.Red)) {
				ParticlesEngine.addStars(x, y, 20, size, 20, StarColor.RED, 1, true);
				Synthe.play(0, (int) note - 5, velocity);
				Synthe.play(0, (int) note, velocity);
				Synthe.play(0, (int) note + 5, velocity);
			}
			if (input.isStillPressed(Input.Green)) {
				ParticlesEngine.addCircles(x, y, 20, size, 20, 1, true);
				Synthe.play(1, (int) note - 5, velocity);
				Synthe.play(1, (int) note, velocity);
				Synthe.play(1, (int) note + 5, velocity);
			}
			if (input.isStillPressed(Input.Blue)) {
				ParticlesEngine.addBubbles(x, y, 20, size, 20, 1, true);
				Synthe.play(2, (int) note - 5, velocity);
				Synthe.play(2, (int) note, velocity);
				Synthe.play(2, (int) note + 5, velocity);
			}
		} else {
			if (input.isPressed(Input.Red)) {
				ParticlesEngine.addStars(x, y, 20, size, 20, StarColor.RED, 20, true);
				Synthe.play(0, (int) note - 5, velocity);
				Synthe.play(0, (int) note, velocity);
				Synthe.play(0, (int) note + 5, velocity);
			}
			if (input.isPressed(Input.Green)) {
				ParticlesEngine.addCircles(x, y, 20, size, 20, 20, true);
				Synthe.play(1, (int) note - 5, velocity);
				Synthe.play(1, (int) note, velocity);
				Synthe.play(1, (int) note + 5, velocity);
			}
			if (input.isPressed(Input.Blue)) {
				ParticlesEngine.addBubbles(x, y, 20, size, 20, 20, true);
				Synthe.play(2, (int) note - 5, velocity);
				Synthe.play(2, (int) note, velocity);
				Synthe.play(2, (int) note + 5, velocity);
			}
		}
		ParticlesEngine.updateParticles();
	}

	public float getSizeRatio() {
		return (size - MinSize + 0F) / (MaxSize - MinSize + 0F);
	}

	public int getVelocity() {
		return (int) (getSizeRatio() * (MaxVelocity - MinVelocity) + MinVelocity);
	}
}
