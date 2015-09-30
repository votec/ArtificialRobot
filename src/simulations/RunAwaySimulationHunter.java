package simulations;

import robot.AbstractRobot;
import robot.RunAwayHunterRobot;

/**
 * Simulation for kalman filter functionality
 * @author fraenklt
 *
 */
public class RunAwaySimulationHunter {

	private static final int RUNS = 1000;
	private RunAwayHunterRobot targetRobot;
	private double measurementNoise;
	private RunAwayHunterRobot hunterRobot;


	public void initSim(){

		targetRobot = new RunAwayHunterRobot(0.0, 10.0, 0.5, 2 * Math.PI / 30.0, 1.5);
		measurementNoise = targetRobot.getDistance() * 0.0005;

		targetRobot.setNoise(0.0, 0.0, measurementNoise);
		hunterRobot = new RunAwayHunterRobot(-10.0, -10.0, 0.0, 2 * Math.PI / 10.0, 1.0);
		hunterRobot.setNoise(0.0, 0.0, measurementNoise);
	}

	public void run(){

		boolean caught = false;
		int counter = 0;
		double separation_tolerance = 0.02 * targetRobot.getDistance();
		double maxDistance = targetRobot.getDistance()*0.98;


		while (true) {
			counter++;
			double error = AbstractRobot.calculateDistance(new double[]{hunterRobot.getX() , hunterRobot.getY()}, new double[]{targetRobot.getX() , targetRobot.getY()});

			System.out.println("estimate Distance -> " + error);
			if (error <= separation_tolerance) {
				caught = true;
			}

			if (caught) {
				System.out.println("Yeah you got him! -> Steps: " + counter + " , distance -> " + error);
				break;
			}

			double[] measurementTarget = targetRobot.sense();
			double[] turning_Distance = hunterRobot.next_move_Hunter(hunterRobot.getX() , hunterRobot.getY() , hunterRobot.getHeading() , measurementTarget , maxDistance);
			double distanceHunter = turning_Distance[1];

			if ( distanceHunter > maxDistance) {
				distanceHunter = maxDistance;
			}

			hunterRobot.move(turning_Distance[0], distanceHunter);
			targetRobot.move_in_circle();

			if (counter > RUNS ) {
				System.out.println("Sorry not caught the target!");
				break;
			}
		}
	}
}
