package balloonadventure.level;

import balloonadventure.menus.LevelSelectionScreen;
import balloonadventure.menus.MainMenu;
import gameengine.input.ClickSource;
import gameengine.menus.MenuManager;
import gameengine.objects.BackgroundObjectFactory;
import gameengine.screens.MenuScene;

public class BalloonAdventureMenuManager extends MenuManager {
	public BalloonAdventureMenuManager(ClickSource menuClickSource, BalloonAdventureLevelCreator balc) {
		super(menuClickSource, balc);
	}

	@Override
	protected MenuScene createMainMenu() {
		return new MainMenu(3, menuClickSource, BackgroundObjectFactory.createBackgroundScenery("BLUE_SKY_CLOUDS"));
	}

	@Override
	protected MenuScene createChooseLevelMenu() {
		return new LevelSelectionScreen(3, menuClickSource, (BalloonAdventureLevelCreator) levelCreator, BackgroundObjectFactory.createBackgroundScenery("BLUE_SKY_CLOUDS"));
	}

}
