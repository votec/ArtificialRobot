package simulations;

import robot.AbstractRobot;
import robot.RunAwayRobot;

public class RunAwaySimulation {


	public void run(){
		AbstractRobot testRobot = new RunAwayRobot(2.1, 4.3, 0.5, 2 * Math.PI, 1.5);
		testRobot.setNoise(0.0, 0.0, 0.0);

		


	}


}
