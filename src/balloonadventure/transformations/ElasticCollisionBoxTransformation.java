package balloonadventure.transformations;

import gameengine.GameEngine;
import gameengine.objects.SpriteObject;
import gameengine.transformations.BoundTransformation;

public class ElasticCollisionBoxTransformation extends BoundTransformation<SpriteObject> {
	private float velX, velY;
	
	public ElasticCollisionBoxTransformation(float xStart, float yStart,
			float xEnd, float yEnd, float velX, float velY) {
		super(xStart, yStart, xEnd, yEnd);
		this.velX = velX;
		this.velY = velY;
		GameEngine.sendDebugMessage("TRANS", String.format("Tworze sie: %f, %f, %f, %f, %f, %f.", xStart,
				yStart, xEnd, yEnd, velX, velY));
	}

	@Override
	public void prepare(SpriteObject so) {
		
	}
	
	@Override
	public boolean isFinished() {
		return false;
	}
	
	@Override
	public void apply(SpriteObject so) {
		so.addX(velX);
		so.addY(velY);
		if (so.getX() < xStart || so.getX() > xEnd) {
			onXReached();
		}
		if (so.getY() < yStart || so.getY() > yEnd) {
			onYReached();
		}
	}
	
	@Override
	protected void onYReached() {
		velY = -velY;
	}
	
	@Override
	protected void onXReached() {
		velX = -velX;
	}

}
