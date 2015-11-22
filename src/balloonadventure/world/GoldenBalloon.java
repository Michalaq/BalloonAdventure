package balloonadventure.world;

import gameengine.graphicengine.SpriteObjectGraphicData;

public class GoldenBalloon extends Balloon {
	private static final BalloonType type = BalloonType.BALLOON_GOLD;
	
	public GoldenBalloon(float x, float y, SpriteObjectGraphicData defaultGraphicData,
					 SpriteObjectGraphicData poppedGraphicData) {
		super(x, y, defaultGraphicData, poppedGraphicData, type);
	}

}	
