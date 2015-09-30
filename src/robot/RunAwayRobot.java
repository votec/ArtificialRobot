package robot;

import filter.KalmanFilterProcessor;

public class RunAwayRobot extends AbstractRobot{

	private final double tolerance = 0.001;
	private KalmanFilterProcessor filter;


	public RunAwayRobot(double x , double y , double heading , double turning , double distance) {
		super(x,y,heading,turning,distance);
		filter = new KalmanFilterProcessor(1. , 1. , 1. , 1. , 1. , measurement_noise);
	}

	public double[] next_move_Hunter(double x, double y, double heading,
			double[] measurementTarget, double maxDistance) {

		double newTurning = 0.0;
		double newDistance = 0.0;
		double[] nextPos = filter.estimate_next_position(measurementTarget[0] , measurementTarget[1]);

		double angle = Math.atan2(nextPos[1] - y, nextPos[0] -x);

		newTurning = angle_trunc(angle) - heading;
		newDistance = calculateDistance(nextPos, new double[]{x,y});

		return new double[]{newTurning , newDistance};
	}

}
