package filter;

import java.util.ArrayList;
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

		for (CycleRobot particle : filter) {



		}

	/*

    w = []
	        for i in range(self.N):
	            w.append(self.data[i].measurement_prob(Z))

	        # resampling (careful, this is using shallow copy)
	        p3 = []
	        index = int(random.random() * self.N)
	        beta = 0.0
	        mw = max(w)

	        for i in range(self.N):
	            beta += random.random() * 2.0 * mw
	            while beta > w[index]:
	                beta -= w[index]
	                index = (index + 1) % self.N
	            p3.append(self.data[index])
	        self.data = p3

	*/
	}

	public void move(double steer, double speed) {

		for (CycleRobot particle : filter) {
			particle.move(steer, speed);
		}

	}

}
