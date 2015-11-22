package balloonadventure.menus;

import gameengine.GameEngine;
import gameengine.GameState;
import gameengine.graphicengine.GraphicsDrawer;
import gameengine.graphicengine.InLayerDrawable;
import gameengine.graphicengine.Layer;
import gameengine.input.ClickSource;
import gameengine.input.Clickable;
import gameengine.menus.MenuManager;
import gameengine.menus.controls.GameButton;
import gameengine.menus.controls.GameText;
import gameengine.objects.ControlObjectFactory;
import gameengine.screens.GameScene;
import gameengine.world.Field;

import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.Callable;

import balloonadventure.level.BalloonAdventureLevel;
import balloonadventure.level.BalloonAdventureTimedLevel;
import balloonadventure.menus.customcontrols.CustomControlsFactory;

public class InGameMenu implements GameScene, Clickable, InLayerDrawable {
	private static final String scoreString = "Score: 0", timeLeftString = "Time: 0";
	private static final int textDefaultHeight = (int) (128*GraphicsDrawer.getGraphicResizeMultiplierY()),
							 textDefaultWidth = (int) (256*GraphicsDrawer.getGraphicResizeMultiplierX()),
							 buttonDefaultHeight = (int) (64*GraphicsDrawer.getGraphicResizeMultiplierY()),
							 buttonDefaultWidth = (int) (64*GraphicsDrawer.getGraphicResizeMultiplierX());
	private static final float margin = 64;
	private float offsetX = 0, dragStart, x, y, z, xEnd, yEnd;
	private Field backgroundField = null;
	private BalloonAdventureLevel level;
	private boolean pressed = false, paused = false, enabled = true;
	private ClickSource clickSource;
	private GameText scoreText, timeLeftText = null;
	private Collection<GameButton> buttons = new HashSet<GameButton>();
	private int currentScreen = 0, leftScreens = 1, rightScreens = 1;
	//opcjonalny: field (inaczej jest przezroczyste)
	//layery? brzmi okej, ale wtedy draw trzeba i tak przeformu³owaæ
	//offsetX musi byc, musi byc jakis tam domyslny rozklad
	
	private GameButton createReplayButton() {
		Callable<Void> clickEvent = new Callable<Void>() {
			@Override
			public Void call() {
				GameEngine.setGameState(GameState.LEVEL_RUNNING);
				return null;
			}
		};
		GameButton returnButton = CustomControlsFactory.createDefaultCloudButton((x-xEnd)*1/3, y+margin, z+1, buttonDefaultWidth, buttonDefaultHeight, 1, clickEvent);
		returnButton.setImage(ControlObjectFactory.createImage((x-xEnd)*1/3, y+margin, 32, 32, (int) z+2, "ICON_REPEAT"));
		return returnButton;
	}
	
	private GameButton createReturnButton() {
		Callable<Void> clickEvent = new Callable<Void>() {
			@Override
			public Void call() {
				GameEngine.changeGameScene(MenuManager.getMainMenu(), GameState.MENU_MAIN);
				return null;
			}
		};
		GameButton returnButton = CustomControlsFactory.createDefaultCloudButton( (x-xEnd)*2/3, y+margin, z+1, buttonDefaultWidth, buttonDefaultHeight, 1, clickEvent);
		returnButton.setImage(ControlObjectFactory.createImage((x-xEnd)*2/3, y+margin, 32, 32, (int) z+2, "ICON_BACK"));
		return returnButton;
	}
	
	public InGameMenu(BalloonAdventureLevel level, float x, float y, float xEnd, float yEnd) {
		this.level = level;
		this.x = x;
		this.y = y;
		this.z = 1;
		this.yEnd = yEnd;
		this.xEnd = xEnd;
		scoreText = new GameText(scoreString, x+margin*3, y+margin, z+1, textDefaultWidth, textDefaultHeight);
		scoreText.setColour("FF0000");
		buttons.add(createReturnButton());
		buttons.add(createReplayButton());
	}
	
	public void showTimeLeft() {
		scoreText.addX(-1.5f*margin);
		timeLeftText = new GameText(timeLeftString, x+margin*4, y+margin, z+1, textDefaultWidth, textDefaultHeight);
	}
	
	private boolean isWithin(float x, float y) {
		return (x >= this.x) && (x <= xEnd)&& (y >= this.y) && (y <= yEnd);
	}
	
	public void setBackgroundField(Field field) {
		backgroundField = field;
	}
	
	@Override
	public void draw(GraphicsDrawer graphicsDrawer) {
		if(backgroundField != null)
			backgroundField.draw(graphicsDrawer);
		scoreText.addX(offsetX);
		scoreText.draw(graphicsDrawer);
		scoreText.addX(-offsetX);
		if (timeLeftText != null) {
			timeLeftText.addX(offsetX);
			timeLeftText.draw(graphicsDrawer);
			timeLeftText.addX(-offsetX);
		}
		for(GameButton gb : buttons) {
			gb.addX(offsetX);
			gb.update();
			gb.draw(graphicsDrawer);
			gb.addX(-offsetX);
		}
	}

	@Override
	public void setClickSource(ClickSource cs) {
		clickSource = cs;
		cs.addListener(this);
		for(GameButton gb : buttons) {
			cs.addListener(gb);
		}
	}

	@Override
	public void onInitialPress(float x, float y) {
		pressed = isWithin(x, y) && enabled;
		if (pressed && !paused)
			dragStart = x;
	}

	@Override
	public void onHold(float x, float y) {
		pressed = pressed && enabled && isWithin(x, y);
		if(pressed && !paused) {
			offsetX = x - dragStart;
		}
	}

	private void moveEverything(float offsetX) {
		for (GameButton gb : buttons) {
			gb.setX(gb.getX()+offsetX);
			gb.update();
		}
		scoreText.setX(scoreText.getX()+offsetX);
		if (timeLeftText != null) {
			timeLeftText.setX(timeLeftText.getX() + offsetX);
		}
	}
	
	@Override
	public void onRelease(float x, float y) {
		pressed = false;
		if (offsetX >= (xEnd-this.x)/2 && currentScreen > -leftScreens) {
			currentScreen--;
			offsetX = 0;
			moveEverything(xEnd-this.x);
		} else if (offsetX <= (this.x-xEnd)/2 && currentScreen < rightScreens) {
			currentScreen++;
			offsetX = 0;
			moveEverything(this.x-xEnd);
		}
		if (currentScreen != 0) {
			level.pause();
		} else {
			level.resume();
		}
		offsetX = 0;
	}

	@Override
	public void update() {
		for(GameButton gb : buttons) {
			gb.update();
		}
		if(!paused) {
			scoreText.setText(scoreText.getText().replaceAll("-?\\d+", String.valueOf(level.getScore())));
			if (timeLeftText != null) {
				timeLeftText.setText(timeLeftText.getText().replaceAll("-?\\d+", String.valueOf(((BalloonAdventureTimedLevel) level).getTimeLeft())));
			}
		}
	}

	@Override
	public void pause() {
		paused = true;
		for(GameButton gb : buttons) {
			gb.setEnabled(false);
		}
	}

	@Override
	public void resume() {
		paused = false;
		for(GameButton gb : buttons) {
			gb.setEnabled(true);
		}
	}

	@Override
	public void destroy() {
		for(GameButton gb : buttons) {
			clickSource.removeListener(gb);
		}
		clickSource.removeListener(this);
	}

	@Override
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		for(GameButton gb : buttons) {
			gb.setEnabled(enabled);
		}		
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public int getPriority() {
		return (int) z;
	}

	@Override
	public boolean isInitialPressWithin(float x, float y) {
		return isWithin(x, y);
	}

	@Override
	public int getTargetLayerId() {
		return Layer.TOP_LAYER-1;
	}

}
