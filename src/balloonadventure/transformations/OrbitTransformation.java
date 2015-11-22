package balloonadventure.transformations;

import gameengine.GameEngine;
import gameengine.graphicengine.GraphicsDrawer;
import gameengine.objects.SpriteObject;
import gameengine.transformations.Transformation;

public class OrbitTransformation extends Transformation<SpriteObject> {
	protected SpriteObject obj = null;
	protected float x, y, phi, rvel, r;
	protected double t = 0;
	
	public OrbitTransformation(float x, float y, float rvel) {
		this.x = x;
		this.y = y;
		this.rvel = rvel / 57.29f;
	}
	
	public OrbitTransformation(SpriteObject centre, float rvel) {
		obj = centre;
		this.rvel = rvel;
	}
	
	protected float getX() {
		return obj == null ? x : obj.getX();
	}
	
	protected float getY() {
		return obj == null ? y : obj.getY();
	}
	
	@Override
	public void prepare(SpriteObject so) {
		//phi = Math.atan((so.getY() - y)/(so.getX() - x)
		r = (float) Math.sqrt((so.getY() - getY())*(so.getY() - getY()) + (so.getX() - getX())*(so.getX() - getX()));
		GameEngine.sendDebugMessage("ORBIT", "Promien: " + String.valueOf(r));
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public void apply(SpriteObject so) {
		so.setX((float) (getX() + r * Math.cos(rvel * t)));
		so.setY((float) (getY() + r * Math.sin(rvel * t)));
		t += 1d / GraphicsDrawer.getFPS();
	}

}
