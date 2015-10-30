package main;

import simulations.RunAwaySimulationHunter;
import simulations.RunAwaySimulationWithoutHunter;

public class Main_Runaway {

	public static void main(String[] args) {

		new RunAwaySimulationWithoutHunter().run();

//		RunAwaySimulationHunter simu = new RunAwaySimulationHunter();
//		simu.initSim();
//		simu.run();

	}

}
