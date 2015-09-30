package main;

import robot.AbstractRobot;
import robot.RunAwayRobot;
import schedule.Plan;
import simulations.RunAwaySimulationHunter;
import simulations.RunAwaySimulationWithoutHunter;
import world.World;

public class Main {

	public static void main(String[] args) {


//		Plan plan = new Plan(World.grid, World.start, World.goal);
//		System.out.println(plan);
//		System.out.println("Test");

//		new RunAwaySimulationWithoutHunter().run();

		RunAwaySimulationHunter simu = new RunAwaySimulationHunter();
		simu.initSim();
		simu.run();

	}

}
