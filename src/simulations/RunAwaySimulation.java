package simulations;

import filter.KalmanFilterProcessor;
import robot.AbstractRobot;
import robot.RunAwayRobot;

public class RunAwaySimulation {


	private static final int RUNS = 30;


	public void run(){
		AbstractRobot testRobot = new RunAwayRobot(2.1, 4.3, 0.5, 2 * Math.PI, 1.5);
		testRobot.setNoise(0.0, 0.0, 0.0);

		boolean localized = false;
		int counter = 0;
		double distance_tolerance = 0.01 * testRobot.getDistance();

		KalmanFilterProcessor filter = new KalmanFilterProcessor(0 , 0 , 1 , 1 , 0.2);

		while (true) {

			testRobot.sense();
			double[] nextPos = filter.estimate_next_position(testRobot.getX() , testRobot.getY());
			testRobot.move( 0.1, 0.2);

			double error = AbstractRobot.calculateDistance(nextPos, new double[]{testRobot.getX() , testRobot.getY()});
			System.out.println( "estimate -> " + nextPos[0] + " , "+  nextPos[1]);
			System.out.println( "IS...... -> " + testRobot.getX() + " , " + testRobot.getY());
			System.out.println(" distance -> "+ error);

			if (error <= distance_tolerance) {
				localized = true;
			}

			if (localized) {
				System.out.println("Yeah -> Steps: " + counter);
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
