package balloonadventure.transformations;

import gameengine.objects.GraphicalObject;
import gameengine.transformations.BoundTransformation;

public class MoveTransformation<T extends GraphicalObject> extends BoundTransformation<T> {
	protected float velX, velY, x, y;
	protected boolean xReached, yReached;
	
	public MoveTransformation(float xStart, float yStart, float xEnd, float yEnd, float velX, float velY) {
		super(xStart, yStart, xEnd, yEnd);
		this.velX = velX;
		this.velY = velY;
	}

	@Override
	protected void onXReached() {
		xReached = true;
	}

	@Override
	protected void onYReached() {
		yReached = true;
	}

	@Override
	public void prepare(GraphicalObject so) {
		so.setX(xStart);
		so.setY(yStart);
	}

	@Override
	public boolean isFinished() {
		return xReached && yReached;
	}
	
	protected void checkBounds() {
		if ((xStart <= xEnd && x >= xEnd) || (xStart >= xEnd && x <= xEnd)) {
			onXReached();
		}
		if ((yStart <= yEnd && y >= yEnd) || (yStart >= yEnd && y <= yEnd)) {
			onYReached();
		}
	}

	@Override
	public void apply(GraphicalObject so) {
		so.addX(velX);
		so.addY(velY);
		x = so.getX();
		y = so.getY();
		checkBounds();
	}

}
