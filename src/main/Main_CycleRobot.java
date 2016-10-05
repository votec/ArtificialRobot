package main;

import gui.Window;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javafx.application.Application;
import javafx.application.Platform;
import robot.Movement;
import schedule.Plan;
import simulations.PathFindSimulation;
import world.World;

public class Main_CycleRobot {

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		new Thread() {
	            @Override
	            public void run() {
	        		Application.launch(Window.class);
	            }
	    }.start();

	    Window window = Window.waitForStartUpTest();
		new PathFindSimulation(window);

	}

}
