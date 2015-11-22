package balloonadventure.level;

import gameengine.graphicengine.GraphicsDrawer;
import gameengine.input.ClickSource;
import gameengine.input.Clickable;
import gameengine.objects.BackgroundObjectFactory;
import gameengine.screens.Level;
import gameengine.soundengine.Music;
import gameengine.world.BackgroundScenery;
import gameengine.world.Field;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import balloonadventure.menus.InGameMenu;
import balloonadventure.world.Balloon;
import balloonadventure.world.Cloud;

public class BalloonAdventureLevel extends Level implements Clickable {
	protected BackgroundScenery bgScenery;
	protected Field field;
	protected InLevelBalloonManager balloonManager;
	protected Collection<Balloon> balloons = new HashSet<Balloon>(),
			  					 balloonsToAdd = new HashSet<Balloon>();
		protected Iterator<Balloon> balloonIterator;
	protected Collection<Cloud> clouds;
	protected boolean pressed, enabled = true;
	protected Music levelMusic;
	protected InGameMenu subMenu;
	protected ClickSource clickSource;
	
	public BalloonAdventureLevel(int layerCount, int id,
								BackgroundScenery bgScenery, Field field,
								Collection<Cloud> clouds, InLevelBalloonManager balloonManager) {
		super(layerCount, id);
		this.bgScenery = bgScenery;
		this.field = field;
		this.clouds = clouds;
		this.balloonManager = balloonManager;
		subMenu = new InGameMenu(this, 0, 0, GraphicsDrawer.getDefaultScreenWidth(), 96);
		subMenu.setBackgroundField(BackgroundObjectFactory.createRectangularField("CLOUD_HORIZONTAL", -GraphicsDrawer.getDefaultScreenWidth(), 0, 3*GraphicsDrawer.getDefaultScreenWidth(), 96));
		 // DEBUG powinien byc porzadek mozliwy do ustalenia
		layers[layerCount-2].addInLayerDrawable(subMenu);
		placeInLayer(bgScenery);
		placeInLayer(field);
		placeInLayer(clouds);
	}
	
	/*private boolean aboveScreen(WorldObject wo) {
		return wo.getY() < 0;
	}*/
	
	protected void checkForCollision() {
		balloonIterator = balloons.iterator();
		Balloon b;
		while (balloonIterator.hasNext()) {
			b = balloonIterator.next();
			if (b.isToBeDestroyed()) {
				score += b.getPointValue();
				balloonIterator.remove();
				removeFromLayer(b);
			} else {
				for (Cloud c : clouds) {
					if (!b.isPopped()
						&& b.intersects(c) ) {
						c.collideWithBalloon(b);
						if (b.isFinished()) {
							score += b.getPointValue();
							b.setPointValue(0);
						}
						break;
					}
				}
			}
		}
	}
	
	protected void addNewWorldObjects() {
		if(!balloonsToAdd.isEmpty()) {
			balloons.addAll(balloonsToAdd);
			placeInLayer(balloonsToAdd);
			balloonsToAdd.clear();
		}
	}
	
	protected void updateBackground() {
		subMenu.update();
		bgScenery.update();
		field.update();
	}
	
	protected void updateWorldObjects() {
		for (Balloon b : balloons) {
			b.update();
		}
		for (Cloud c : clouds) {
			c.update();
		}
	}
	
	@Override
	public synchronized void update() {
		if(!paused) {
			addNewWorldObjects();
			checkForCollision();
			updateWorldObjects();
			updateBackground();
		}
	}

	//////////////
	public int getScore() {
		return score;
	}
	
	////////////// CLICKABLE DEBUG ??????
	
	@Override
	public void setClickSource(ClickSource cs) {
		cs.addListener(this);
		subMenu.setClickSource(cs);
		clickSource = cs;
	}

	@Override
	public void onHold(float x, float y) {
		pressed = !paused && pressed && field.isWithin(x, y);
	}

	@Override
	public void onRelease(float x, float y) {
		if (!paused && pressed) {
			balloonsToAdd.add(balloonManager.getNextBalloon(x, y));
			pressed = false;
		}
		
	}

	@Override
	public void onInitialPress(float x, float y) {
		pressed = !paused && field.isWithin(x, y);
		
	}

	@Override
	public boolean isInitialPressWithin(float x, float y) {
		return field.isWithin(x, y);
	}

	@Override
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public int getPriority() {
		return 0;
	}	
	
	/////////////////////// CLICKABLE HELP
	
	@Override
	public void pause() {
		super.pause();
		if (levelMusic != null)
			levelMusic.pause();
		enabled = false;
	}
	
	@Override
	public void resume() {
		super.resume();
		if (levelMusic != null)
			levelMusic.resume();
		enabled = true;
	}
	
	@Override
	public void destroy() {
		super.destroy();
		if (levelMusic != null)
			levelMusic.stop();
		enabled = false;
		subMenu.destroy();
		clickSource.removeListener(this);
	}
	
	/////////////////////// MUSICAL DEBUG ?????????
	
	@Override
	public void setMusic(Music music) {
		levelMusic = music;
		music.play();
	}

	@Override
	public Music getMusic() {
		return levelMusic;
	}


}
