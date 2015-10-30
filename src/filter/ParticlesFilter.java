package filter;

import robot.AbstractRobot;

public class ParticlesFilter extends AbstractRobot{

	/**
	 * Implement particles filter.
	 * @param x
	 * @param y
	 * @param heading
	 * @param turningNoise
	 * @param distanceNoise
	 * @param measurementNoise
	 */
	public ParticlesFilter(double x, double y, double heading,
			double turningNoise, double distanceNoise, double measurementNoise) {
		super(x, y, heading, turningNoise, distanceNoise);

	}

	public double[] getPosition() {
		/**
		 * TODO
		 */
		return null;
	}


	public void sense(double[] z) {


	}




}
