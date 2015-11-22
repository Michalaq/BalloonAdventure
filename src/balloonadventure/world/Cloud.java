package balloonadventure.world;

import gameengine.graphicengine.SpriteObjectGraphicData;
import gameengine.world.WorldObject;

public abstract class Cloud extends WorldObject {
	protected float spawnX, spawnY, rangeX, rangeY;
	
	public Cloud(float x, float y, int width, int height,
				SpriteObjectGraphicData defaultGraphicData) {
		super(x, y, width, height);
		spawnX = x;
		spawnY = y;
		layerId++;
		currentGraphicData = defaultGraphicData;
	}

	public abstract void collideWithBalloon(Balloon b);
}
