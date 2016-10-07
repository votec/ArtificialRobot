package main;

import java.util.List;
import java.util.concurrent.ExecutionException;

import robot.CycleRobot;
import world.World;
import filter.ParticlesFilter;
import gui.DrawGrid;
import gui.DrawParticles;
import gui.Window;
import javafx.application.Application;
import javafx.concurrent.Task;

public class ParticalfilterTest {

	protected static final double turning_noise = 0.1;
	protected static final double distance_noise = 0.03;
	protected static final double measurement_noise = 0.3;
	private static DrawGrid drawing;
	private static ParticlesFilter filter;

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		new Thread() {
            @Override
            public void run() {
        		Application.launch(Window.class);
            }
    }.start();

    Window window = Window.waitForStartUpTest();

    drawing = new DrawGrid(window.getContext().getCanvas().getGraphicsContext2D());

    new ParticalfilterTest().drawGrid();

    filter = new ParticlesFilter(0, 0, 0, turning_noise, distance_noise, measurement_noise);

    DrawParticles gui = new DrawParticles(window.getContext().getCanvas().getGraphicsContext2D() );

    startTest(filter , gui);

	}


	private static void startTest(ParticlesFilter filter2, DrawParticles gui) throws InterruptedException {

		CycleRobot r = new CycleRobot(0, 0, 0);
		r.setNoise(turning_noise, distance_noise, measurement_noise);

		for (int i = 0; i < 20; i++) {

		r.move(0, 0.1);
		filter.move(0,0.1);

		/**
		 * Correct particles
		 */
		double[] Z = r.sense();
		filter.sense(Z);
		gui.drawParticles(filter.getParticles());

		System.out.println(filter.getParticles().size());

		drawing.drawRobot(r.getX(),r.getY());

		Thread.sleep(300);

		}
	}


	/**
	 * run planning to get goal in grid
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	private void  drawGrid() throws InterruptedException, ExecutionException{

		new Task<Void>() {
	    @Override
	    protected Void call() throws Exception {
	    	drawing.drawGrid(World.grid);
			return null;
	    }
		}.run();

	}

}
