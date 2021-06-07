package entities;

import java.awt.Rectangle;

import level.Level;
import level.Tile;
import core.Main;
import core.Screen;
import core.tools.Direction;
import core.tools.ZIndex;

public abstract class Entity implements Target {
	private String spriteName = "";
	protected int spriteWidth = 0;
	protected int spriteHeight = 0;
	protected float x = 0;
	protected float y = 0;
	protected int z = 0;
	private int hitboxWidth = 0;
	private int hitboxHeight = 0;
	protected Level level;

	protected double angle = 0;
	protected float hue = 0;

	public static final boolean lookAtLeft = false;
	public static final boolean lookAtRight = true;
	protected boolean lookAt = lookAtRight;

	protected int maxColumns = 1;
	protected int maxLines = 1;
	protected int column = 0;
	protected int line = 0;
	private boolean visible = true;
	private boolean blink = false;
	private boolean disposed = false;

	public Entity(String spriteName, int spriteWidth, int spriteHeight, int x, int y, int hitboxWidth, int hitboxHeight, int maxColumns, int maxLines, Level level) {
		this.spriteName = spriteName;
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
		this.x = x;
		this.y = y;
		this.z = ZIndex.entities;
		this.hitboxWidth = hitboxWidth;
		this.hitboxHeight = hitboxHeight;
		this.maxColumns = maxColumns;
		this.maxLines = maxLines;
		this.level = level;
	}

	public String getSpriteName() {
		return spriteName;
	}

	public void setSpriteName(String spriteName) {
		this.spriteName = spriteName;
	}

	public int getSpriteWidth() {
		return spriteWidth;
	}

	public int getSpriteHeight() {
		return spriteHeight;
	}

	@Override
	public int getX() {
		return (int) x;
	}

	public void setX(int x) {
		this.x = x;
	}

	@Override
	public int getY() {
		return (int) y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public float getHue() {
		return hue;
	}

	public void setHue(float hue) {
		this.hue = hue;
	}

	public boolean lookAt() {
		return lookAt;
	}

	public int getMaxColumns() {
		return maxColumns;
	}

	public int getMaxLines() {
		return maxLines;
	}

	public int getColumn() {
		return column;
	}

	public int getLine() {
		return line;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setBlinking(boolean blink) {
		this.blink = blink;
	}

	public boolean isBlinking() {
		return blink;
	}

	public static int convertDirection(boolean lookAt) {
		return lookAt == lookAtLeft ? -1 : 1;
	}

	public int convertDirection() {
		return convertDirection(lookAt);
	}

	public boolean isDisposed() {
		return disposed;
	}

	public void dispose() {
		disposed = true;
	}

	public boolean canPass(Direction direction) {
		Rectangle hitbox = getHitbox();
		int minX = hitbox.x;
		int maxX = hitbox.x + hitbox.width - 1;
		int minY = hitbox.y;
		int maxY = hitbox.y + hitbox.height - 1;
		int tileSize = level.getTileSizeIngame();
		switch (direction) {
		case UP:
			minY--;
			maxY = minY;
			break;
		case DOWN:
			maxY++;
			minY = maxY;
			break;
		case LEFT:
			minX--;
			maxX = minX;
			break;
		case RIGHT:
			maxX++;
			minX = maxX;
			break;
		default:
			break;
		}
		for (int x = minX; x <= maxX; x++) {
			for (int y = minY; y <= maxY; y++) {
				if (!level.isOnLevel(x, y))
					return false;
				Tile tile = level.getTile(x / tileSize, y / tileSize);
				switch (tile.passable) {
				case CannotPass:
					return false;
				case TopBlocked:
					if (y % tileSize == 0 && direction == Direction.DOWN)
						return false;
					break;
				case BottomBlocked:
					if (y % tileSize == tileSize - 1 && direction == Direction.UP)
						return false;
					break;
				case LeftBlocked:
					if (x % tileSize == 0 && direction == Direction.RIGHT)
						return false;
					break;
				case RightBlocked:
					if (x % tileSize == tileSize - 1 && direction == Direction.LEFT)
						return false;
					break;
				case UpToDown:
					// return x > y;
				case DownToUp:
					// return x + y < tileSize - 1;
				case CanPass:
				default:
					break;
				}
			}
		}
		return true;
	}

	public Rectangle getHitbox() {
		return getHitbox(getX(), getY());
	}

	public Rectangle getHitbox(int x, int y) {
		return new Rectangle(x - hitboxWidth / 2, y - hitboxHeight, hitboxWidth, hitboxHeight);
	}

	public void basicUpdate(Main main, Screen screen) {
		update(main, screen);
	}

	protected abstract void update(Main main, Screen screen);

	public abstract void collisionWithEntity(Entity entity);
}
