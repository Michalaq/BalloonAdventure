package balloonadventure.level;

import gameengine.graphicengine.GraphicsDrawer;
import gameengine.menus.controls.GameText;
import gameengine.world.BackgroundScenery;
import gameengine.world.Field;

import java.util.Collection;

import balloonadventure.world.Cloud;

public class BalloonAdventureTimedLevel extends BalloonAdventureLevel {
	protected static final String counterString = "Get ready!   0";
	protected static final int counterWidth = GraphicsDrawer.getDefaultScreenWidth(),
							   counterHeight = counterWidth;
	protected int timeLeft, milisecondCounter = 0, timeToStart = 3;
	protected boolean started = false, ended = false;
	protected GameText counter;
	public BalloonAdventureTimedLevel(int layerCount, int id,
			BackgroundScenery bgScenery, Field field, Collection<Cloud> clouds,
			InLevelBalloonManager balloonManager, int time) {
		super(layerCount, id, bgScenery, field, clouds, balloonManager);
		timeLeft = time;
		subMenu.showTimeLeft();
		counter = new GameText(counterString, GraphicsDrawer.getDefaultScreenWidth()/2, GraphicsDrawer.getDefaultScreenHeight()/2,
								layerCount-2, counterWidth, counterHeight);
		counter.setText(counter.getText().replaceAll("-?\\d+", String.valueOf((timeToStart))));
		placeInLayer(counter);
		super.update();
	}
	
	public int getTimeLeft() {
		return timeLeft;
	}
	
	@Override
	public boolean isEnabled() {
		return started && !ended && super.isEnabled();
	}
	
	@Override
	public void update() {
		if (started && !ended) {
			super.update();
		}
		if (!paused && !ended) {
			milisecondCounter++;
			if (started && milisecondCounter >= GraphicsDrawer.getFPS()) {
				milisecondCounter -= GraphicsDrawer.getFPS();
				timeLeft--;
				if (timeLeft == 0) {
					subMenu.update();
					endLevel();
				}
			}
		}
		if (!started && !paused) {
			if (milisecondCounter >= GraphicsDrawer.getFPS()) {
				milisecondCounter -= GraphicsDrawer.getFPS();
				timeToStart -= 1;
				counter.setText(counter.getText().replaceAll("-?\\d+", String.valueOf((timeToStart))));
			}
			if (timeToStart == 0) {
				removeFromLayer(counter);
				started = true;
				resume();
			}
		}
	}
	
	@Override
	public void resume() {
		if (!ended) {
			super.resume();
		}
	}
	
	public void endLevel() {
		ended = true;
		pause();
	}

}
