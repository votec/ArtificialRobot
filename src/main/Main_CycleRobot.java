package main;

import gui.Window;

import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import robot.Movement;
import schedule.Plan;
import simulations.PathFindSimulation;
import world.World;

public class Main_CycleRobot {

	public static void main(String[] args) {

		new Thread() {
	            @Override
	            public void run() {
	        		Application.launch(Window.class);
	            }
	    }.start();


	    Window window = Window.waitForStartUpTest();
	    window.printSomething();
	    System.out.println("GC -> " + window.getContext().getCanvas().getGraphicsContext2D());
		new PathFindSimulation(window);

	}

}
