package balloonadventure.transformations;

import gameengine.objects.TextureObject;
import gameengine.transformations.physics.VelocityTransformation;

public class ShiftVelocityTransformation extends VelocityTransformation<TextureObject> {

	public ShiftVelocityTransformation(float x, float y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void apply(TextureObject to) {
		to.shiftX(x);
		to.shiftY(y);
	}
}
