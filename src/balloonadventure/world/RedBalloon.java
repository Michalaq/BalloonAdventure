package balloonadventure.world;

import gameengine.graphicengine.SpriteObjectGraphicData;

public class RedBalloon extends Balloon {
	private static final BalloonType type = BalloonType.BALLOON_RED;
	
	public RedBalloon(float x, float y, SpriteObjectGraphicData defaultGraphicData,
					 SpriteObjectGraphicData poppedGraphicData) {
		super(x, y, defaultGraphicData, poppedGraphicData, type);
	}


}
