package balloonadventure.menus;

import gameengine.GameEngine;
import gameengine.GameState;
import gameengine.graphicengine.GraphicsDrawer;
import gameengine.input.ClickSource;
import gameengine.menus.controls.GameButton;
import gameengine.menus.controls.GameControl;
import gameengine.menus.controls.GameText;
import gameengine.objects.SpriteObject;
import gameengine.screens.MenuScene;
import gameengine.world.BackgroundScenery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;

import balloonadventure.level.BalloonAdventureLevelCreator;
import balloonadventure.menus.customcontrols.CustomControlsFactory;
import balloonadventure.transformations.MoveTransformation;
import balloonadventure.transformations.ShiftTransformation;
import balloonadventure.transformations.ShiftVelocityTransformation;

public class LevelSelectionScreen extends MenuScene {
	private static final float topMargin = 100,
							margin = 20,
							fbX = (GraphicsDrawer.getDefaultScreenWidth()/8+margin),
							fbY = GraphicsDrawer.getDefaultScreenHeight()/8+margin+topMargin,
							headerX = GraphicsDrawer.getDefaultScreenWidth()/2,
							headerY = 50;
	private static final int bWidth = (int) (96*GraphicsDrawer.getGraphicResizeMultiplierX()),
							 bHeight = (int) (96*GraphicsDrawer.getGraphicResizeMultiplierY()),
							 headerHeight = 256;
	private Collection<GameControl> controls = new ArrayList<GameControl>();
	private BackgroundScenery bgScenery;
	
	private void placeButtons(final BalloonAdventureLevelCreator balc) {
		GameButton gb;
		Callable<Void> c;
		int lCount = balc.getLevelCount();
		for(int i=1; i<=lCount; i++) {
			final int levelId = i;
			c = new Callable<Void>() {
				@Override
				public Void call() {
					GameEngine.changeGameScene(BalloonAdventureLevelCreator.createLevel(levelId), GameState.LEVEL_RUNNING);
					return null;
				}
			};
			gb = CustomControlsFactory.createDefaultCloudButton(fbX + ((i-1)%4)*(bWidth+margin), fbY + (i/5)*(bHeight+margin), 1, bWidth, bHeight, 2, c);
			gb.setText(new GameText(String.valueOf(i), gb.getX(), gb.getY(), gb.getTargetLayerId()+1, 10, gb.getHeight()/2));
			gb.setClickSource(clickSource);
			gb.addTransformation(new MoveTransformation<SpriteObject>(gb.getX(), GraphicsDrawer.getDefaultScreenHeight()+gb.getY(), gb.getX(), gb.getY(), 0, -10));
			placeInLayer(gb);
			controls.add(gb);
			clickables.add(gb);
		}
	}
	
	public LevelSelectionScreen(int layerCount, ClickSource clickSource, BalloonAdventureLevelCreator balc, BackgroundScenery bgScenery) {
		super(layerCount, clickSource);
		placeButtons(balc);
		bgScenery.addTransformation(new ShiftVelocityTransformation(2, 0));
		this.bgScenery = bgScenery;
		placeInLayer(bgScenery);
		bgScenery.addTransformation(new ShiftTransformation(0, GraphicsDrawer.getDefaultScreenHeight(), 0, 10));
		GameText gt = new GameText("Choose level!", headerX, headerY, topLayer, GraphicsDrawer.getDefaultScreenWidth()/2, headerHeight);
		placeInLayer(gt);
		activateClickables();
	}

	public synchronized void update() {
		for (GameControl gc : controls) {
			gc.update();
		}
		bgScenery.update();
	}
}
