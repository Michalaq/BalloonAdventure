package balloonadventure.menus;

import gameengine.GameEngine;
import gameengine.GameState;
import gameengine.graphicengine.GraphicsDrawer;
import gameengine.input.ClickSource;
import gameengine.menus.MenuManager;
import gameengine.menus.controls.GameButton;
import gameengine.menus.controls.GameControl;
import gameengine.menus.controls.GameImage;
import gameengine.menus.controls.GameText;
import gameengine.objects.ControlObjectFactory;
import gameengine.objects.SpriteObject;
import gameengine.screens.MenuScene;
import gameengine.world.BackgroundScenery;

import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.Callable;

import balloonadventure.menus.customcontrols.CustomControlsFactory;
import balloonadventure.transformations.MoveTransformation;
import balloonadventure.transformations.ShiftTransformation;
import balloonadventure.transformations.ShiftVelocityTransformation;

public class MainMenu extends MenuScene { /// DEBUG CLASS
	private static final int logoHeight = 256, logoWidth = 416;
	private static final float firstButtonX = GraphicsDrawer.getDefaultScreenWidth()/2,//-GameButton.defaultWidth/2,
							   firstButtonY = GraphicsDrawer.getDefaultScreenHeight()/2+logoHeight,//+GameButton.defaultHeight/2,
							   logoX = (GraphicsDrawer.getDefaultScreenWidth())/2,
							   logoY = logoHeight/2+64;
	private static final String[] buttonText = new String[] {"Play!"};
	private static int buttonCount = 1;
	private Collection<GameControl> controls;
	private BackgroundScenery bgScenery;
	
	//DEBUG TODO!!!!
	private Collection<GameControl> createMenuControls() {
		Collection<GameControl> controls = new HashSet<GameControl>();
		final Callable<Void>[] events = new Callable[3];
		events[0] = new Callable<Void>() {
			@Override
			public Void call() {
				GameEngine.changeGameScene(MenuManager.getChooseLevelMenu(), GameState.MENU_CHOOSE_LEVEL);
				return null;
			}
		};
		GameButton b;
 		for(int i=0; i<buttonCount; i++) {
 			b = CustomControlsFactory.createDefaultCloudButton(firstButtonX, firstButtonY+GameButton.ButtonSize.MEDIUM_RECTANGLE.height*1.5f*i, i+1, GameButton.ButtonSize.MEDIUM_RECTANGLE.width, GameButton.ButtonSize.MEDIUM_RECTANGLE.height, 1, events[i]);
 			b.setText(new GameText(buttonText[i], b.getX(), b.getY(), b.getTargetLayerId()+1, GameButton.ButtonSize.MEDIUM_RECTANGLE.width-GameButton.ButtonSize.MEDIUM_RECTANGLE.xMargin,
 						GameButton.ButtonSize.MEDIUM_RECTANGLE.height-GameButton.ButtonSize.MEDIUM_RECTANGLE.yMargin));
 			controls.add(b);
 			clickables.add(b);
 			clickSource.addListener(b);
 			b.addTransformation(new MoveTransformation<SpriteObject>(firstButtonX, GraphicsDrawer.getDefaultScreenHeight()+firstButtonY, firstButtonX, firstButtonY, 0, -10));
 		}
 		GameImage gi = ControlObjectFactory.createImage(logoX, logoY, logoWidth, logoHeight,1, "GAME_LOGO");
 		gi.addTransformation(new MoveTransformation<SpriteObject>(logoX, GraphicsDrawer.getDefaultScreenHeight()+logoY, logoX, logoY, 0, -10));
		controls.add(gi);
		return controls;
	}
	
	public MainMenu(int layerCount, ClickSource clickSource, BackgroundScenery bgScenery) {
		super(layerCount, clickSource);
		controls = createMenuControls();
		bgScenery.addTransformation(new ShiftVelocityTransformation(2, 0));
		this.bgScenery = bgScenery;
		bgScenery.addTransformation(new ShiftTransformation(0, GraphicsDrawer.getDefaultScreenHeight(), 0, 10));
		placeAllInLayers(controls);
		placeInLayer(bgScenery);
		activateClickables();
	}

	@Override
	public synchronized void update() {
		for (GameControl c : controls) {
			c.update();
		}
		bgScenery.update();
	}
	
	
}
