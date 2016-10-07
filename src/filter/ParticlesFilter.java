package filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import robot.CycleRobot;

public class ParticlesFilter {

	private final int FILTERSIZE = 100;
	List<CycleRobot> filter = new ArrayList<CycleRobot>();

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

		for (int i = 0; i < FILTERSIZE; i++) {
			CycleRobot partical = new CycleRobot(x, y, heading);
			partical.setNoise(turningNoise, distanceNoise, measurementNoise);
			filter.add(partical);
		}
	}

	public double[] getPosition() {
		double x = 0.0;
		double y = 0.0;
		double heading = 0.0;

		for (CycleRobot particle : filter) {
			x += particle.getX();
			y += particle.getY();
			heading += ((particle.getHeading() - filter.get(0).getHeading() + Math.PI) % (2.0 * Math.PI)) + (filter.get(0).getHeading() -Math.PI);
		}
		return new double[]{x / FILTERSIZE , y / FILTERSIZE , heading/ FILTERSIZE};
	}


	public void sense(double[] z) {

		List<Double> errors = new ArrayList<Double>();
		List<CycleRobot> seedingList = new ArrayList<CycleRobot>();

		for (CycleRobot particle : filter) {
			double error = particle.measurementProb(z);
			errors.add(error);
		}

		int index = (int) (Math.random() * FILTERSIZE);
		double beta = 0.0;
		double mw = Collections.max(errors);

		for (int i = 0; i < FILTERSIZE; i++) {
			beta += (Math.random() * 2.0 * mw);
			while (beta > errors.get(index)) {
				beta -= errors.get(index);
				index = (index + 1) % FILTERSIZE;
			}
			seedingList.add(filter.get(index));
		}

		filter = seedingList;
	}

	public void move(double steer, double speed) {
		for (CycleRobot particle : filter) {
			particle.move(steer, speed);
		}
	}

	public List<CycleRobot> getParticles() {
		return filter;
	}

}
