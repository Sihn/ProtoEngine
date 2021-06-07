package engineV2;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.HashMap;

public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
	public Input(Component component) {
		component.addKeyListener(this);
		component.addMouseListener(this);
		component.addMouseMotionListener(this);
		component.addMouseWheelListener(this);
		component.requestFocus();
	}

	// Directions
	public final static int UP = KeyEvent.VK_UP;
	public final static int DOWN = KeyEvent.VK_DOWN;
	public final static int LEFT = KeyEvent.VK_LEFT;
	public final static int RIGHT = KeyEvent.VK_RIGHT;

	// Options
	public final static int SPACE = KeyEvent.VK_SPACE;
	public final static int CTRL = KeyEvent.VK_CONTROL;
	public final static int SHIFT = KeyEvent.VK_SHIFT;
	public final static int ALT = KeyEvent.VK_ALT;
	public final static int ENTER = KeyEvent.VK_ENTER;
	public final static int ESCAPE = KeyEvent.VK_ESCAPE;

	// Letters
	public final static int A = KeyEvent.VK_A;
	public final static int B = KeyEvent.VK_B;
	public final static int C = KeyEvent.VK_C;
	public final static int D = KeyEvent.VK_D;
	public final static int E = KeyEvent.VK_E;
	public final static int F = KeyEvent.VK_F;
	public final static int G = KeyEvent.VK_G;
	public final static int H = KeyEvent.VK_H;
	public final static int I = KeyEvent.VK_I;
	public final static int J = KeyEvent.VK_J;
	public final static int K = KeyEvent.VK_K;
	public final static int L = KeyEvent.VK_L;
	public final static int M = KeyEvent.VK_M;
	public final static int N = KeyEvent.VK_N;
	public final static int O = KeyEvent.VK_O;
	public final static int P = KeyEvent.VK_P;
	public final static int Q = KeyEvent.VK_Q;
	public final static int R = KeyEvent.VK_R;
	public final static int S = KeyEvent.VK_S;
	public final static int T = KeyEvent.VK_T;
	public final static int U = KeyEvent.VK_U;
	public final static int V = KeyEvent.VK_V;
	public final static int W = KeyEvent.VK_W;
	public final static int X = KeyEvent.VK_X;
	public final static int Y = KeyEvent.VK_Y;
	public final static int Z = KeyEvent.VK_Z;

	// Shortcuts
	public final static int N0 = KeyEvent.VK_0;
	public final static int N1 = KeyEvent.VK_1;
	public final static int N2 = KeyEvent.VK_2;
	public final static int N3 = KeyEvent.VK_3;
	public final static int N4 = KeyEvent.VK_4;
	public final static int N5 = KeyEvent.VK_5;
	public final static int N6 = KeyEvent.VK_6;
	public final static int N7 = KeyEvent.VK_7;
	public final static int N8 = KeyEvent.VK_8;
	public final static int N9 = KeyEvent.VK_9;

	// Mouses
	public final static int MOUSE_LEFT = MouseEvent.BUTTON1;
	public final static int MOUSE_MIDDLE = MouseEvent.BUTTON2;
	public final static int MOUSE_RIGHT = MouseEvent.BUTTON3;

	// Actions
	public static int[] Up = { UP, Z };
	public static int[] Down = { DOWN, S };
	public static int[] Left = { LEFT, Q };
	public static int[] Right = { RIGHT, D };
	public static int[] Attack1 = { SHIFT, A };
	public static int[] Attack2 = { MOUSE_LEFT };
	public static int[] Jump = { Z, UP, SPACE };
	public static int[] Inventory = { E };
	public static int[] Validate = { ENTER };
	public static int[] Cancel = { ESCAPE };
	public static int[] Reset = { R };

	// Others actions
	public static int[] Alpha = {};
	public static int[] Beta = {};
	public static int[] Gamma = {};
	public static int[] Delta = {};
	public static int[] Epsilon = {};
	public static int[] Omega = {};

	private HashMap<Integer, Boolean> state = new HashMap<>();
	private int mouseX = 0;
	private int mouseY = 0;
	private int wheelState = 0;

	public int get4Dir() {
		if (isStillPressed(Up))
			return 8;
		else if (isStillPressed(Down))
			return 2;
		else if (isStillPressed(Left))
			return 4;
		else if (isStillPressed(Right))
			return 6;
		else
			return 0;
	}

	public int get8Dir() {
		boolean left = isStillPressed(Left);
		boolean right = isStillPressed(Right);
		if (isStillPressed(Up)) {
			if (left)
				return 7;
			if (right)
				return 9;
			return 8;
		} else if (isStillPressed(Down)) {
			if (left)
				return 1;
			if (right)
				return 3;
			return 2;
		} else if (left)
			return 4;
		else if (right)
			return 6;
		else
			return 0;
	}

	public boolean isPressed(int... keyCodes) {
		for (int keyCode : keyCodes)
			if (isPressed(keyCode))
				return true;
		return false;
	}

	public boolean isPressed(int keyCode) {
		Boolean value = state.get(keyCode);
		if (value == null)
			return false;
		else if (!value) {
			state.put(keyCode, true);
			return true;
		}
		return false;
	}

	public boolean isStillPressed(int... keyCodes) {
		for (int keyCode : keyCodes)
			if (isStillPressed(keyCode))
				return true;
		return false;
	}

	public boolean isStillPressed(int keyCode) {
		return state.containsKey(keyCode);
	}

	public int getX() {
		return mouseX;
	}

	public int getY() {
		return mouseY;
	}

	public int getWheel() {
		int wheel = wheelState;
		wheelState = 0;
		return wheel;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		state.put(e.getKeyCode(), state.containsKey(e.getKeyCode()));
	}

	@Override
	public void keyReleased(KeyEvent e) {
		state.remove(e.getKeyCode());
	}

	@Override
	public void mousePressed(MouseEvent e) {
		state.put(e.getButton(), state.containsKey(e.getButton()));
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		state.remove(e.getButton());
	}

	private void move(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		move(e);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		move(e);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		wheelState = e.getWheelRotation();
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
}
