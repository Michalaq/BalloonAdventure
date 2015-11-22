package balloonadventure.world;

import gameengine.graphicengine.GraphicsDrawer;

public enum BalloonType {
	// ( wartosc, czas rozkladu, domyslna wysokosc, szerokosc, predkosc.x, predkosc.y )
	BALLOON_RED  (1, 1500, 32, 32, 0, -400, "BALLOON_RED", "BALLOON_RED_POPPED"),
	BALLOON_BLUE (2, 1500, 32, 32, 0, -400, "BALLOON_BLUE", "BALLOON_BLUE_POPPED"),
	BALLOON_GOLD (5, 1500, 32, 32, 0, -400, "BALLOON_GOLD", "BALLOON_GOLD_POPPED"),
	;
	
	public final int pointValue;
	public final int poppedDecayTime;
	public final int defaultHeight;
	public final int defaultWidth;
	public final float defaultVelocityX;
	public final float defaultVelocityY;
	public final String defaultSprite;
	public final String defaultPopSprite;
	
	private BalloonType(int value, int poppedDecayTime, int defaultHeight, int defaultWidth,
						float defaultVelocityX, float defaultVelocityY, String defaultSprite,
						String defaultPopSprite) {
		this.pointValue = value;
		this.poppedDecayTime = poppedDecayTime;
		this.defaultHeight = defaultHeight;
		this.defaultWidth = defaultWidth;
		this.defaultVelocityX = defaultVelocityX;
		this.defaultVelocityY = defaultVelocityY;
		this.defaultSprite = defaultSprite;
		this.defaultPopSprite = defaultPopSprite;
	}
	
	public float getActualDefaultVelocityX() {
		return defaultVelocityX / GraphicsDrawer.getFPS();
	}
	public float getActualDefaultVelocityY() {
		return defaultVelocityY / GraphicsDrawer.getFPS();
	}
	public int getActualPoppedDecayTime() {
		return poppedDecayTime / GraphicsDrawer.getFPS();
	}
}
