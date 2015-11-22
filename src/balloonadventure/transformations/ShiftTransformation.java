package balloonadventure.transformations;

import gameengine.GameEngine;
import gameengine.objects.TextureObject;

public class ShiftTransformation extends MoveTransformation<TextureObject> {
	
	public ShiftTransformation(float offsetX, float offsetY, float velX, float velY) {
		super(0, 0, offsetX, offsetY, velX, velY);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void prepare(TextureObject to) {
		x = 0;
		y = 0;
	}
	
	@Override
	public void apply(TextureObject to) {
		to.shiftX(velX);
		to.shiftY(velY);
		x += velX;
		y += velY;
		checkBounds();
		GameEngine.sendDebugMessage("Shift", String.format("Przesuniecie: x=%f, y=%f, xStart=%f, yStart=%f"
				+ ", xEnd=%f, yEnd=%f.", x, y, xStart, yStart, xEnd, yEnd));
	}

}
