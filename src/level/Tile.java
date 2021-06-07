package level;

import java.util.ArrayList;

public class Tile {
	public enum Passability {
		CanPass, CannotPass, TopBlocked, BottomBlocked, LeftBlocked, RightBlocked, UpToDown, DownToUp
	}

	public String name = "Air";
	public ArrayList<TileLayer> layers = new ArrayList<>();
	public Passability passable = Passability.CanPass;
	public boolean liquid = false;
	public int damage = 0;

	//public int tileSize;
	public int leftBorder;
	public int rightBorder;

	public Tile(int tileSize) {
		changeBorders(tileSize, tileSize);
	}

	public void changeBorders(int leftBorder, int rightBorder) {
		this.leftBorder = leftBorder;
		this.rightBorder = rightBorder;
	}
}
