package balloonadventure.level;

import gameengine.world.BackgroundScenery;
import gameengine.world.Field;

import java.util.Collection;

import balloonadventure.world.Cloud;

public class BalloonAdventureLevelData {
	private final int id, layerCount, timeLeft;
	private final BackgroundScenery background;
	private final Collection<Cloud> clouds;
	private final Field clickField;
	private final InLevelBalloonManager balloonManager;
	
	public BalloonAdventureLevelData(int layerCount, int id, int timeLeft, BackgroundScenery background, Collection<Cloud> clouds,
									Field clickField, InLevelBalloonManager balloonManager) {
		this.background = background;
		this.clickField = clickField;
		this.clouds = clouds;
		this.balloonManager = balloonManager;
		this.layerCount = layerCount;
		this.id = id;
		this.timeLeft = timeLeft;
	}
	
	public int getTimeLeft() {
		return timeLeft;
	}

	public BackgroundScenery getBackground() {
		return background;
	}

	public Collection<Cloud> getClouds() {
		return clouds;
	}

	public Field getClickField() {
		return clickField;
	}

	public InLevelBalloonManager getBalloonManager() {
		return balloonManager;
	}

	public int getId() {
		return id;
	}

	public int getLayerCount() {
		return layerCount;
	}
}
