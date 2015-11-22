package balloonadventure.menus.customcontrols;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import gameengine.graphicengine.SpriteObjectGraphicData;
import gameengine.menus.controls.GameButton;
import gameengine.menus.controls.GameButton.State;
import gameengine.objects.ControlObjectFactory;

public class CustomControlsFactory extends ControlObjectFactory {
	
	private static Map<State, SpriteObjectGraphicData> getDefaultGraphics(int width, int height) {
		Map<State, String> tmpMap = new HashMap<State, String>();
		for (State s : GameButton.State.values()) {
			tmpMap.put(s, GameButton.ButtonSize.MEDIUM_RECTANGLE.getStateSprite(s));
		}
		
		Map<State, SpriteObjectGraphicData> graphicsMap = new HashMap<State, SpriteObjectGraphicData>();
		for (State s : State.values()) {
			graphicsMap.put(s, graphicDataFactory.createResizedSpriteObjectGraphicData
											(tmpMap.get(s), width, height));
		}
		return graphicsMap;
	}
	
	public static GameButton createDefaultCloudButton(float x, float y, float priority_z, int width, int height, final int layerId,
									  final Callable<Void> onClickEvent) {
		
		
		return new GameButton(x, y, priority_z, width, height, layerId, getDefaultGraphics(width, height)) {
			@Override
			public void onRelease(float x, float y) {
				if (currentState == State.PRESSED) {
					super.onRelease(x, y);
					try {
						onClickEvent.call();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		};
	}
	
	
	
}
