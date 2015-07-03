package main;

import robot.Plan;
import world.World;

public class Main {

	public static void main(String[] args) {
		
		
		Plan plan = new Plan(World.grid, World.start, World.goal);
		System.out.println(plan);
		

	}

}
