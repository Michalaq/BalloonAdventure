package balloonadventure.world;

import gameengine.graphicengine.SpriteObjectGraphicData;
import gameengine.objects.SpriteObject;
import gameengine.soundengine.SoundEffect;
import gameengine.transformations.Transformation;
import gameengine.transformations.physics.VelocityTransformation;
import gameengine.world.WorldObject;

public abstract class Balloon extends WorldObject {
	protected BalloonType type;
	protected SpriteObjectGraphicData balloonGraphicData, poppedBalloonGraphicData;
	protected static SoundEffect balloonPopSoundData, balloonFlyHighSoundData;
	protected boolean popped = false, destroyed = false, finished = false;
	protected int poppedDecayTime, pointValue;

	public static void setSoundEffects(SoundEffect balloonPop, SoundEffect balloonFlyHigh) {
		balloonFlyHighSoundData = balloonFlyHigh;
		balloonPopSoundData = balloonPop;
	}
	
	public Balloon(float x, float y, SpriteObjectGraphicData defaultGraphicData,
			  SpriteObjectGraphicData poppedGraphicData, BalloonType type) {
		super(x, y, type.defaultWidth, type.defaultHeight);
		balloonGraphicData = defaultGraphicData;
		poppedBalloonGraphicData = poppedGraphicData;
		currentGraphicData = balloonGraphicData;
		this.type = type;
		poppedDecayTime = type.getActualPoppedDecayTime();
		pointValue = type.pointValue;
	}	
	
	public synchronized void pop() {
		finished = true;
		popped = true;
		transformations.clear();
		transformations.add(new VelocityTransformation<SpriteObject>(0, -type.getActualDefaultVelocityY()/2));
		currentGraphicData = poppedBalloonGraphicData;
		balloonPopSoundData.play();
	}
	
	public void update() {
		super.update();
		if (y < 0) {
			flyHigh();
		}
		if (popped) {
			poppedDecayTime -= 1;
			destroyed = poppedDecayTime <= 0;
		}
		currentGraphicData.update(this);
	}
	
	public BalloonType getType() {
		return type;
	}
	
	public int getPointValue() {
		return pointValue;
	}
	
	public void setPointValue(int newPointValue) {
		pointValue = newPointValue;
	}
	
	public boolean isPopped() {
		return popped;
	}
	
	public boolean isToBeDestroyed() {
		return destroyed;
	}
	
	public boolean isFinished() {
		return finished;
	}
	
	public void flyHigh() {
		finished = true;
		destroyed = true;
		balloonFlyHighSoundData.play();
	}

	@Override
	public void addTransformation(Transformation<SpriteObject> transformation) {
		if (!popped) {
			super.addTransformation(transformation);
		}
	}
}
