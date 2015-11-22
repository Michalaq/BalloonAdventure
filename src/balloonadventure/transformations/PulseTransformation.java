package balloonadventure.transformations;

import gameengine.graphicengine.GraphicsDrawer;
import gameengine.objects.SpriteObject;
import gameengine.transformations.Transformation;

public class PulseTransformation extends Transformation<SpriteObject> {
	protected final float maxScaleX, maxScaleY, frequency;
	protected int dX, dY, maxWidth, maxHeight, minWidth, minHeight;
	
	public PulseTransformation(float maxScaleX, float maxScaleY, float frequency) {
		this.maxScaleX = maxScaleX;
		this.maxScaleY = maxScaleY;
		this.frequency = frequency;
	}
	
	@Override
	public void prepare(SpriteObject so) {
		dX = (int) (so.getWidth() * maxScaleX * frequency / (GraphicsDrawer.getFPS()));
		dY = (int) (so.getHeight() * maxScaleY * frequency / (GraphicsDrawer.getFPS()));
		maxWidth = (int) (so.getWidth() * maxScaleX);
		maxHeight = (int) (so.getHeight() * maxScaleY);
		minWidth = (int) (so.getWidth() / maxScaleX);
		minHeight = (int) (so.getHeight() / maxScaleY);
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public void apply(SpriteObject so) {
		so.setWidth(so.getWidth() + dX);
		so.setHeight(so.getHeight() + dY);
		if (so.getWidth() >= maxWidth || so.getWidth() <= minWidth) {
			dX = -dX;
		}
		if (so.getHeight() >= maxHeight || so.getHeight() <= minHeight) {
			dY = -dY;
		}
	}

}
