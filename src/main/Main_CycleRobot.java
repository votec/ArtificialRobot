package main;

import schedule.Plan;
import world.World;

public class Main_CycleRobot {

	public static void main(String[] args) {

		Plan plan = new Plan(World.grid, World.start, World.goal);
		System.out.println(plan);
	}

}
