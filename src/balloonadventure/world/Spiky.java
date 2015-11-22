package balloonadventure.world;

import gameengine.graphicengine.GraphicsDrawer;
import gameengine.graphicengine.SpriteObjectGraphicData;
import gameengine.soundengine.SoundEffect;


public class Spiky extends Cloud {
	private static final int SPIKY_DEFAULT_LAUGHING_TIME = 1000 / GraphicsDrawer.getFPS();
	private SpriteObjectGraphicData defaultSpikyGraphicData, laughingSpikyGraphicData;
	private static SoundEffect laughingSpikySoundEffect;
	private boolean laughing = false;
	private int laughRemainingTime;
	
	public static void setSoundEffects(SoundEffect laughingSpiky) {
		laughingSpikySoundEffect = laughingSpiky;
	}
	
	public Spiky(float x, float y, SpriteObjectGraphicData defaultGraphicData,
				SpriteObjectGraphicData laughingGraphicData) {
		super(x, y, CloudType.SPIKY.width, CloudType.SPIKY.height, defaultGraphicData);
		defaultSpikyGraphicData = defaultGraphicData;
		laughingSpikyGraphicData = laughingGraphicData;
		currentGraphicData = defaultSpikyGraphicData;
	}
	
	public void laugh() {
		if (!laughing) {
			laughing = true;
			laughRemainingTime = SPIKY_DEFAULT_LAUGHING_TIME;
			//laughingSpikySoundEffect.play();
			//currentGraphicData = laughingSpikyGraphicData;
		}
	}
	
	public void update() {
		super.update();
		if (laughing) {
			laughRemainingTime--;
			laughing = laughRemainingTime > 0;
			if (!laughing) {
				currentGraphicData = defaultSpikyGraphicData;
			}
		}
		currentGraphicData.update(this);
		/*gameEngine.sendDebugMessage("Spiky koordynaty: ", "X: " + String.valueOf(x) + ", Y: " + String.valueOf(y));
		gameEngine.sendDebugMessage("Spiky predkosc: ", vel.toString());*/
	}
	


	@Override
	public void collideWithBalloon(Balloon b) {
		switch (b.getType()) {
			case BALLOON_BLUE : case BALLOON_GOLD : case BALLOON_RED : {
				b.setPointValue(-b.getPointValue());
				b.pop();
				laugh();
				break;
			}
		}
		
	}

}
