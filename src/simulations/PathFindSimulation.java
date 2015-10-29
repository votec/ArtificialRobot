package simulations;

import gui.DrawGrid;
import gui.Window;

import java.util.List;

import javafx.concurrent.Task;
import javafx.scene.paint.Color;
import robot.Movement;
import schedule.Plan;
import world.World;

public class PathFindSimulation {


	private DrawGrid drawing;

	public PathFindSimulation(Window window) {

		this.drawing = new DrawGrid(window.getContext().getCanvas().getGraphicsContext2D());
		runPlanning();
	}

	public void runPlanning(){

		Task<Integer> task = new Task<Integer>() {
	    @Override
	    protected Integer call() throws Exception {

	    	drawing.drawGrid(World.grid);
	    	Plan plan = new Plan(World.grid, World.start, World.goal);
			List<double[]> path = plan.a_star();

			drawing.drawPath(path , Color.GOLD);

			path = plan.smoothPath(path);

			drawing.drawPath(path , Color.GREEN);

			return 0;
	    }
		};
		new Thread(task).start();
	}


}
