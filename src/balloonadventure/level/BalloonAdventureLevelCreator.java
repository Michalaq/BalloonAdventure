package balloonadventure.level;

import gameengine.input.ClickSource;
import gameengine.levels.LevelCreator;
import gameengine.soundengine.MusicManager;

public abstract class BalloonAdventureLevelCreator extends LevelCreator {
	protected static BalloonAdventureLevelData levelData[];
	protected static ClickSource gameClickSource;
	
	public BalloonAdventureLevelCreator(ClickSource cs) {
		gameClickSource = cs;
		levelData = null;
	}
	
	protected static BalloonAdventureLevel createLevelFromData(BalloonAdventureLevelData bald) {	
		BalloonAdventureLevel bal = null;
		if (bald.getTimeLeft() <= 0) {
			bal = new BalloonAdventureLevel(bald.getLayerCount(), bald.getId(), 
										bald.getBackground(), bald.getClickField(),
										bald.getClouds(), bald.getBalloonManager());
		} else {
			bal = new BalloonAdventureTimedLevel(bald.getLayerCount(), bald.getId(), 
										bald.getBackground(), bald.getClickField(),
										bald.getClouds(), bald.getBalloonManager(), bald.getTimeLeft());
		}
		bal.setClickSource(gameClickSource);
		bal.setMusic(MusicManager.getLevelMusicByLevelId(bald.getId()));
		return bal;
	}
	
	public static BalloonAdventureLevel createLevel(int id) {
		return createLevelFromData(levelData[id]);
	}
}
