package balloonadventure.level;

import java.security.InvalidParameterException;
import java.util.LinkedList;
import java.util.Random;

import balloonadventure.world.BalloonType;

public class RandomBalloonManager extends InLevelBalloonManager {
	private static final Random rand = new Random();
	private final BalloonType[] balloonTypes;
	private final float[] chances;
	public RandomBalloonManager(int predictionSize, BalloonType[] allowedBalloonTypes,
							   float[] chancesRespectivelyAscending) {
		super(predictionSize);
		balloonTypes = allowedBalloonTypes;
		chances = chancesRespectivelyAscending;
		balloonPrediction = new LinkedList<BalloonType>();
		if (chances.length < balloonTypes.length-1)
			throw new InvalidParameterException("Incompatible array sizes in RandomBalloonManager.");
		for(int i=0; i<predictionSize; i++)
			balloonPrediction.add(getNextBalloonType());
	}

	@Override
	protected BalloonType getNextBalloonType() {
		int i = 0;
		float chance = rand.nextFloat(), currentChance = 0;
		while (i < balloonTypes.length-1) {
			currentChance += chances[i];
			if (currentChance >= chance) {
				return balloonTypes[i];
			}
			i++;
		}
		return balloonTypes[i];
	}

}
