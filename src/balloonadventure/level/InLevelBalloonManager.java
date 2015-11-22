package balloonadventure.level;

import gameengine.transformations.physics.VelocityTransformation;

import java.util.Queue;

import balloonadventure.world.Balloon;
import balloonadventure.world.BalloonType;
import balloonadventure.world.WorldObjectFactory;

public abstract class InLevelBalloonManager {
	protected final int predictionSize;
	protected Queue<BalloonType> balloonPrediction;
	public InLevelBalloonManager(int predictionSize) {
		this.predictionSize = predictionSize;
	}
	
	public final Balloon getNextBalloon(float x, float y) {
		Balloon result = WorldObjectFactory.createBalloon(balloonPrediction.poll(), x, y);
		result.addTransformation(new VelocityTransformation(result.getType().getActualDefaultVelocityX(),
															result.getType().getActualDefaultVelocityY()));
		balloonPrediction.add(getNextBalloonType());
		return result;
	}
	protected abstract BalloonType getNextBalloonType();
}
