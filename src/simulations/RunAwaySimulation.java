package simulations;

import filter.KalmanFilter;
import robot.AbstractRobot;
import robot.RunAwayRobot;

public class RunAwaySimulation {


	public void run(){
		AbstractRobot testRobot = new RunAwayRobot(2.1, 4.3, 0.5, 2 * Math.PI, 1.5);
		testRobot.setNoise(0.0, 0.0, 0.0);

		boolean localized = false;
		int counter = 0;
		double distance_tolerance = 0.01 * testRobot.getDistance();

		while (true) {

			testRobot.sense();
			double[] nextPos = KalmanFilter.estimate_next_position(testRobot);
			((RunAwayRobot) testRobot).move_in_Circle();

			double error = AbstractRobot.calculateDistance(nextPos, new double[]{testRobot.getX() , testRobot.getY()});

			System.out.println(" distance -> "+ error);

			if (error <= distance_tolerance) {
				localized = true;
			}

			if (localized) {
				System.out.println("Yeah -> Steps: " + counter);
				break;
			}

			if (counter > 10 ) {
				System.out.println("Sorry!");
				break;
			}





			counter++;

		}


	}


}
