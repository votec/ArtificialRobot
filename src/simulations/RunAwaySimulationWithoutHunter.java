package simulations;

import filter.KalmanFilterProcessor;
import robot.AbstractRobot;
import robot.RunAwayRobot;

public class RunAwaySimulationWithoutHunter {

	private static final int RUNS = 1000;

	public void run(){
		AbstractRobot targetRobot = new RunAwayRobot(2.1, 4.3, 0.5, 2 * Math.PI / 34.0, 2);
		double measurementNoise = targetRobot.getDistance() * 0.05;
		targetRobot.setNoise(0.0, 0.0, measurementNoise);

		boolean localized = false;
		int counter = 0;
		double distance_tolerance = 0.1 * targetRobot.getDistance();

		KalmanFilterProcessor filter = new KalmanFilterProcessor(1. , 1. , 0. , 0. , 1. , measurementNoise);

		while (true) {

			double[] measurement = targetRobot.sense();
			double[] nextPos = filter.estimate_next_position(measurement[0],measurement[1]);
			targetRobot.move_in_circle();

			double error = AbstractRobot.calculateDistance(nextPos, new double[]{targetRobot.getX() , targetRobot.getY()});
//			System.out.println( "estimate -> " + nextPos[0] + " , "+  nextPos[1]);
//			System.out.println( "IS...... -> " + testRobot.getX() + " , " + testRobot.getY());
			System.out.println(counter + " distance -> "+ error + " > " + distance_tolerance);

			if (error <= distance_tolerance) {
				localized = true;
			}

			if (localized) {
				System.out.println("Yeah -> Steps: " + counter + " , distance -> " + error);
				break;
			}

			if (counter > RUNS ) {
				System.out.println("Sorry!");
				break;
			}

			counter++;

		}


	}


}
