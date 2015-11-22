package balloonadventure.world;

import gameengine.graphicengine.SpriteObjectGraphicData;

public class BlueBalloon extends Balloon {
	private static final BalloonType type = BalloonType.BALLOON_BLUE;
	
	public BlueBalloon(float x, float y, SpriteObjectGraphicData defaultGraphicData,
					 SpriteObjectGraphicData poppedGraphicData) {
		super(x, y, defaultGraphicData, poppedGraphicData, type);
	}

}
