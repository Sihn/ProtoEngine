package entities;

import java.awt.Color;
import java.awt.Point;

import level.Level;
import level.Tile;
import level.Tile.Passability;
import loops.GameLoop;
import sprites.particles.ParticleStar.StarColor;
import core.Main;
import core.ParticlesEngine;
import core.Screen;
import core.tools.Direction;
import core.tools.ZIndex;

public class Player extends Entity {
	private Point startPoint;
	public State state = State.Normal;

	private final int standTile = 0;
	private final int brakeTile = 1;
	private final int jumpTile = 2;
	private final int spinTile = 3;
	private final int fallTile = 3;

	private final int fixedTilesLine = 0;
	private final int walkTilesLine = 3;
	private final int runTilesLine = 1;
	private final int waitTilesLine = 2;
	private int currentWalkTile = 0;

	private final int waitAnimDuration = 10;
	private int waitAnim = 0;

	private final int minStep = 3;
	private final int maxStep = 10;
	private int step = 0;

	private final float acceleration = .1F;
	private final float deceleration = .2F;
	private final float airDeceleration = .08F;
	private final float revert = .3F;
	private final float airRevert = .1F;
	private final float minSpeed = 1F;
	private final float maxSpeed = 10F;
	private float speedX = 0;
	private float previousSpeed = 0;

	private final float minGravity = acceleration;
	private final float maxGravity = maxSpeed;
	private float speedY = 0;

	private final int jumpPower = 3;
	private final float jumpSpeed = 4;
	private int jumpStart = 0;
	private boolean canJump = true;

	private final float slopResistance = 1;
	private final float waterResistance = .4F;

	private boolean isJumping = false;
	private boolean onGround = false;
	private boolean onWater = false;
	// private Tile currentTile = null;
	private Tile onTile = null;
	private boolean onSlop = false;

	private final int maxAttackDuration = 200;
	private int attackCurrentDuration = 0;
	private boolean isAttacking = false;

	private final float Hue = 350F / 360F;
	private Color aura = new Color(0xFFFFFF);

	private int itemsFound = 0;

	private final int maxLife = 3;
	private int life = maxLife;

	private final int protectionDurationMax = 100;
	private int protectionDuration = 0;

	private final int collapseDuration = 240;
	private int collapseStep = 0;

	public Player(int x, int y, Level level) {
		this(x, y, lookAtRight, level);
	}

	public Player(int x, int y, boolean lookAt, Level level) {
		super("!warioland4_black_cat.png", 32, 32, x, y, 20, 20, 4, 8, level);
		z = ZIndex.player;
		startPoint = new Point(x, y);
		this.lookAt = lookAt;
		hue = Hue;
	}

	public void spawn() {
		onTile = level.getTile(getX() / level.getTileSizeIngame(), getY() / level.getTileSizeIngame());
		x = startPoint.x;
		y = startPoint.y;
		ParticlesEngine.squaresBlast((int) x, (int) y - spriteWidth / 2, 200, 32, false);
		speedY = -getJumpSpeed();
	}

	public float getResistance() {
		float res = 1;
		if (onSlop)
			res *= slopResistance;
		if (onWater)
			res *= waterResistance;
		return res;
	}

	public float getAcceleration() {
		return acceleration * getResistance();
	}

	public float getDeceleration() {
		return deceleration * getResistance();
	}

	public float getAirDeceleration() {
		return airDeceleration * getResistance();
	}

	public float getRevert() {
		return revert * getResistance();
	}

	public float getAirRevert() {
		return airRevert * getResistance();
	}

	public float getMinSpeedX() {
		return minSpeed * getResistance();
	}

	public float getMaxSpeedX() {
		return maxSpeed * getResistance();
	}

	public float getMinSpeedY() {
		return minGravity * getResistance();
	}

	public float getMaxSpeedY() {
		return maxGravity * getResistance();
	}

	public int getJumpPower() {
		return jumpPower;
	}

	public float getJumpSpeed() {
		return jumpSpeed * getResistance();
	}

	public void addSpeed(float value) {
		if (value != 0) {
			if (speedX == 0 && Math.abs(value) < getMinSpeedX())
				speedX = value < 0 ? -getMinSpeedX() : getMinSpeedX();
			else
				speedX += value;
		}
	}

	public void bounce() {
		speedY *= (speedY > 0) ? -1 : 2;
		canJump = false;
	}

	public int getAttackDuration() {
		return attackCurrentDuration;
	}

	public int getMaxAttackDuration() {
		return maxAttackDuration;
	}

	public void input(Direction direction, boolean callJump, boolean forceJump, boolean attacking) {
		switch (direction) {
		case DOWN_LEFT:
		case UP_LEFT:
		case LEFT:
			if (speedX < 0)
				addSpeed(-getAcceleration());
			else if (onGround)
				addSpeed(-getRevert());
			else
				addSpeed(-getAirRevert());
			break;
		case DOWN_RIGHT:
		case UP_RIGHT:
		case RIGHT:
			if (speedX > 0)
				addSpeed(getAcceleration());
			else if (onGround)
				addSpeed(getRevert());
			else
				addSpeed(getAirRevert());
			break;
		default:
			if (onGround) {
				if (speedX < 0)
					addSpeed(getDeceleration());
				else if (speedX > 0)
					addSpeed(-getDeceleration());
			} else {
				if (speedX < 0)
					addSpeed(getAirDeceleration());
				else if (speedX > 0)
					addSpeed(-getAirDeceleration());
			}
		}
		if (callJump) {
			if (isJumping && (jumpStart - getY() >= (getJumpPower() - 1) * level.getTileSizeIngame() || speedY == 0)) {
				isJumping = false;
			}
			if ((onGround && canJump) || onWater) {
				canJump = false;
				isJumping = true;
				jumpStart = getY();
			}
		} else {
			if (onGround)
				canJump = true;
			isJumping = false;
		}
		if (isJumping || forceJump)
			speedY = -getJumpSpeed();
		else
			speedY += getMinSpeedY();
		isAttacking = false;
		if (!onWater && attacking) {
			if (attackCurrentDuration < maxAttackDuration) {
				isAttacking = true;
				attackCurrentDuration += 2;
			}
		} else {
			if (attackCurrentDuration > 0)
				attackCurrentDuration--;
		}
	}

	@Override
	protected void update(Main main, Screen screen) {
		switch (state) {
		case Normal:
			standardUpdate(main);
			break;
		case Winning:
			break;
		case Loosing:
		case Collapse:
			collapseUpdate(main);
			break;
		}
	}

	public void standardUpdate(Main main) {
		updateDamages(main);
		speedX = limitSpeed(speedX, getMinSpeedX(), getMaxSpeedX());
		speedY = limitSpeed(speedY, getMinSpeedY(), getMaxSpeedY());
		updateX();
		updateY();
		updateApparence(main);
		updateLightTrail();
		prepareNextUpdate();
	}

	public void collapseUpdate(Main main) {
		collapseStep++;
		if (collapseStep >= collapseDuration)
			main.setLoop(new GameLoop());
	}

	public void updateDamages(Main main) {
		if (onTile.damage != 0)
			addDamage(onTile.damage);
		if (isProtected())
			protectionDuration--;
		setBlinking(isProtected());
	}

	private float limitSpeed(float speed, float min, float max) {
		if (speed != 0) {
			if (Math.abs(speed) < min)
				speed = 0;
			else if (speed > max)
				speed = max;
			else if (speed < -max)
				speed = -max;
		}
		return speed;
	}

	private void updateX() {
		if (speedX != 0) {
			float newX = x + speedX;
			if (speedX < 0) {
				while (x > newX) {
					if (canPass(Direction.LEFT)) {
						x--;
						/* } else if (canPass(getHitbox(x - 1, y - 1), Direction.LEFT)) {
						 * x--;
						 * y--; */
					} else {
						speedX = 0;
						break;
					}
				}
				if (x < newX)
					x = newX;
			} else if (speedX > 0) {
				while (x < newX) {
					if (canPass(Direction.RIGHT)) {
						x++;
						/* } else if (canPass(getHitbox(x + 1, y - 1), Direction.RIGHT)) {
						 * x++;
						 * y--; */
					} else {
						speedX = 0;
						break;
					}
				}
				if (x > newX)
					x = newX;
			}
		}
	}

	private void updateY() {
		if (speedY != 0) {
			float newY = y + speedY;
			if (speedY < 0) {
				while (y > newY) {
					if (canPass(Direction.UP)) {
						y--;
					} else {
						speedY = 0;
						break;
					}
				}
				if (y < newY)
					y = newY;
			} else if (speedY > 0) {
				while (y < newY) {
					if (canPass(Direction.DOWN)) {
						y++;
					} else {
						speedY = 0;
						break;
					}
				}
				if (y > newY)
					y = newY;
			}
		}
	}

	private void standAnim() {
		if (line != waitTilesLine) {
			line = fixedTilesLine;
			column = standTile;
			angle = 0;
			step = 0;
			currentWalkTile = 0;
		}
		if (line == waitTilesLine || Math.random() < .01) {
			line = waitTilesLine;
			column = currentWalkTile;
			waitAnim++;
			if (waitAnim >= waitAnimDuration) {
				waitAnim = 0;
				currentWalkTile++;
				if (currentWalkTile >= maxColumns) {
					line = fixedTilesLine;
					column = standTile;
					currentWalkTile = 0;
				}
			}
		}
	}

	private void walkAnim() {
		line = Math.abs(speedX) == getMaxSpeedX() ? runTilesLine : walkTilesLine;
		step++;
		if (step > maxStep - Math.abs(speedX) * maxStep / getMaxSpeedX() + minStep) {
			step = 0;
			currentWalkTile++;
			while (currentWalkTile >= maxColumns)
				currentWalkTile -= maxColumns;
			column = currentWalkTile;
		}
		angle = 0;
	}

	private void slowAnim() {
		if (onGround && !onWater && Math.abs(speedX) < Math.abs(previousSpeed) && Math.abs(speedX) >= getMaxSpeedX() / 2)
			ParticlesEngine.addStars(x, y, 5, 2, 2, StarColor.RED, 5, false);
		line = fixedTilesLine;
		column = brakeTile;
		angle = 0;
	}

	private void spinAnim() {
		line = fixedTilesLine;
		column = spinTile;
		angle += 30;
	}

	private void jumpAnim() {
		line = fixedTilesLine;
		if (speedX == 0 && speedY > 0) {
			column = fallTile;
			angle = 0;
		} else {
			column = jumpTile;
			angle = speedY / getMaxSpeedY() * 30;
		}
	}

	private void updateLightTrail() {
		if (isAttacking())
			ParticlesEngine.addFireTrail(x, y - spriteWidth / 2, false);
		if (onWater || (speedX == 0 && speedY == 0 && onGround))
			return;
		ParticlesEngine.addLightTrail(x, y - spriteWidth / 2, aura, false);
	}

	public void updateApparence(Main main) {
		if (Math.abs(previousSpeed) < Math.abs(speedX))
			lookAt = speedX > 0 ? lookAtRight : lookAtLeft;
		if (onWater)
			if ((int) (Math.random() * Main.fps) == 0)
				ParticlesEngine.addBubbles(x, y - spriteWidth / 2, 20, 10, 4, (int) (Math.random() * 3) + 1, false);
		if (speedX == 0 && speedY == 0 && onGround) {
			standAnim();
		} else {
			if (isBlinking())
				spinAnim();
			else if (onWater) {
				walkAnim();
			} else {
				if (onGround) {
					if (Math.abs(previousSpeed) > Math.abs(speedX))
						slowAnim();
					else
						walkAnim();
				} else {
					jumpAnim();
				}
			}
		}
		if (getLife() <= 1)
			line += maxLines / 2;
	}

	public boolean isAttacking() {
		return isAttacking;
	}

	public void prepareNextUpdate() {
		previousSpeed = speedX;
		// currentTile = level.getTile(getX() / level.getTileSizeIngame(), (getY() - spriteHeight / 2) / level.getTileSizeIngame());
		onTile = level.getTile(getX() / level.getTileSizeIngame(), getY() / level.getTileSizeIngame());
		// onTile = level.getTile((getX() + spriteWidth / 2 * convertDirection(lookAt)) / level.getTileSizeIngame(), getY() / level.getTileSizeIngame());
		onSlop = onTile.passable == Passability.UpToDown || onTile.passable == Passability.DownToUp;
		onGround = !canPass(Direction.DOWN);
		onWater = onTile.liquid;
	}

	@Override
	public void collisionWithEntity(Entity entity) {}

	public boolean isMoving() {
		return speedX != 0;
	}

	public void addItems(int items) {
		itemsFound += items;
	}

	public int getItemsFound() {
		return itemsFound;
	}

	public void addDamage(int damage) {
		int prevLife = life;
		life -= damage;
		if (life > 0) {
			if (damage > 0) {
				ParticlesEngine.addStars(x, y, 20, 20, 20, StarColor.RED, 20, false);
				setProtection();
			}
		} else if (prevLife > 0) {
			ParticlesEngine.collapse(this, collapseDuration / 3, spriteHeight, 15);
			setVisible(false);
			state = State.Collapse;
		}
	}

	public int getLife() {
		return life;
	}

	public int getMaxLife() {
		return maxLife;
	}

	public boolean isProtected() {
		return protectionDuration > 0;
	}

	public void setProtection() {
		protectionDuration = protectionDurationMax;
	}
}
