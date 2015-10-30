package simulations;

import filter.ParticlesFilter;
import gui.DrawGrid;
import gui.Window;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javafx.concurrent.Task;
import javafx.scene.paint.Color;
import robot.CycleRobot;
import schedule.Plan;
import world.World;

public class PathFindSimulation {


	protected static final double turning_noise = 0.1;
	protected static final double distance_noise = 0.03;
	protected static final double measurement_noise = 0.2;
	protected static final double speed = 0.05;
	protected static final int timeout = 1000;
	protected static final double p_gain = 1.5;
	protected static final double d_gain = 4.0;
	private DrawGrid drawing;

	public PathFindSimulation(Window window) throws InterruptedException, ExecutionException {

		this.drawing = new DrawGrid(window.getContext().getCanvas().getGraphicsContext2D());
		List<double[]> path = runPlanning();
		runSimulation(path);

	}

	/**
	 * run planning to get goal in grid
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	private List<double[]>  runPlanning() throws InterruptedException, ExecutionException{

		Task<List<double[]>> planning_task = new Task<List<double[]>>() {
	    @Override
	    protected List<double[]> call() throws Exception {
	    	drawing.drawGrid(World.grid);
	    	List<double[]> path= makePlanning();
			return path;
	    }

		private List<double[]> makePlanning() {
			Plan plan = new Plan(World.grid, World.start, World.goal);
			List<double[]> path = plan.a_star();
			drawing.drawPath(path , Color.GOLD);
			System.out.println("Smooth planning...");
			path = plan.smoothPath(path);
			drawing.drawPath(path , Color.GREEN);
			return path;
		}
		};
		Thread planningThread = new Thread(planning_task);
		planningThread.start();

		while (planningThread.isAlive()) {
			System.out.println("Waiting for planning...");
			Thread.sleep(100);
		}
		return planning_task.get();

	}

	/**
	 * run the whole simulation
	 * @param path
	 */
	public void runSimulation(List<double[]> path){

		Task<Integer> simulation = new Task<Integer>() {
	    @Override
	    protected Integer call() throws Exception {
	    	CycleRobot r = new CycleRobot(0.0, 0.0, 0.0);
	    	r.setNoise(turning_noise, distance_noise, measurement_noise);
	    	ParticlesFilter filter =new ParticlesFilter(r.getX(), r.getY(), r.getHeading() , turning_noise , distance_noise, measurement_noise);

	    	double cte = 0.0;
	    	double err = 0.0;
	    	int N = 0;
	    	int index = 0;

	    	while (true) {

	    		if (N> timeout) {
					System.out.println("Timeout! -> find no goal!");
	    			break;
				}

	    		if (r.checkGoal(World.goal)) {

	    			System.out.println("Find Goal! -> steps: " + N);
	    			break;
				}

	    		double diff_cte = -cte;

	    		double[] estimate_position = filter.getPosition();
	    		if (estimate_position == null) {
	    			// take robot position instead of measurement
					estimate_position = new double[]{r.getX(), r.getY()};
				}


	    		double[] path_pos = path.get(index);
	    		double[] next_path_pos = path.get(index+1);
	    		System.out.println("PATH POS: " + path_pos[0] + " , " + path_pos[1] + " NEXT PATH POS-> " + next_path_pos[0] + " ," +  next_path_pos[1]);
	    		double u = (estimate_position[0] - path_pos[0]) * (next_path_pos[0] - path_pos[0])  + (estimate_position[1] - path_pos[1]) * (next_path_pos[1] - path_pos[1]) ;
	    		u /= Math.pow((next_path_pos[0] - path_pos[0]), 2) + Math.pow((next_path_pos[1] - path_pos[1]), 2);

	    		cte = (estimate_position[1] - path_pos[1]) * (next_path_pos[0] - path_pos[0]) - (estimate_position[0] - path_pos[0])* (next_path_pos[1] - path_pos[1]);
	    		cte /= Math.pow((next_path_pos[0] - path_pos[0]), 2) + Math.pow((next_path_pos[1] - path_pos[1]), 2);

	    		if ((u > 1) && (index<path.size()-1)){
					index++;
				}





	    		diff_cte += cte;

	    		System.out.println("diff cte-> "+ diff_cte + " cte -> " + cte );

	    		double steer = -p_gain * cte - d_gain * diff_cte;

	    		System.out.println("steering angle ->   " + steer * 180 / Math.PI );

	    		/**
	    		 * move robot & particles
	    		 */
	    		r.move(steer, speed);
	    		filter.move(steer,speed);

	    		/**
	    		 * Correct particles
	    		 */
	    		double[] Z = r.sense();
	    		filter.sense(Z);

	    		r.checkCollissions(World.grid);

	    		err +=(cte*cte);
	    		N++;


	    		drawing.drawRobot(r.getX(),r.getY());

	    		System.out.println("err: " + err +  "  cte: "+ cte +"  index: "+ index + " u:  " + u + " collisions: " + r.getCollisions());
	    		Thread.sleep(20);
			}

			return 0;
	    }


		};
		new Thread(simulation).start();
	}


}
