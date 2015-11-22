package balloonadventure.world;

import gameengine.graphicengine.SpriteObjectGraphicData;
import gameengine.objects.GraphicalObjectFactory;

public class WorldObjectFactory extends GraphicalObjectFactory {
	
	public static Cloud createCloud(CloudType cloudType, float x, float y) {
		Cloud c = null;
		switch (cloudType) {
			case SPIKY : {
				c = new Spiky(x, y,
						graphicDataFactory.createSpriteObjectGraphicData("SPIKY"),
						graphicDataFactory.createSpriteObjectGraphicData("SPIKY_LAUGHING"));
				break;
			}
			case YUMMYSTRATUS : {
				c = new Yummystratus(x, y,
						graphicDataFactory.createSpriteObjectGraphicData("YUMMYSTRATUS"));
			}
		}
		return c;
	}
	
	public static Balloon createBalloon(BalloonType colour, float x, float y) {
		Balloon b = null;
		SpriteObjectGraphicData defaultGD = graphicDataFactory.createSpriteObjectGraphicData(colour.defaultSprite),
								poppedGD = graphicDataFactory.createSpriteObjectGraphicData(colour.defaultPopSprite);
		switch (colour) {
			case BALLOON_RED : {
				b = new RedBalloon(x, y, defaultGD, poppedGD);
			} break;
			case BALLOON_BLUE : {
				b = new BlueBalloon(x, y, defaultGD, poppedGD);
			} break;
			case BALLOON_GOLD : {
				b = new GoldenBalloon(x, y, defaultGD, poppedGD);
			} break;
		}
		return b;
	}
	
}
