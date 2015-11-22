package balloonadventure.world;

import gameengine.graphicengine.SpriteObjectGraphicData;

public class Yummystratus extends Cloud {
	
	public Yummystratus(float x, float y,
			SpriteObjectGraphicData defaultGraphicData) {
		super(x, y, CloudType.YUMMYSTRATUS.width, CloudType.YUMMYSTRATUS.height,defaultGraphicData);
	}

	@Override
	public void collideWithBalloon(Balloon b) {
		switch (b.getType()) {
			case BALLOON_BLUE : case BALLOON_GOLD : case BALLOON_RED : {
				b.setPointValue(2*b.getPointValue());
				b.flyHigh();
				break;
			}
		}
	}

}
