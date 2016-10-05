package robot;

import java.util.Random;

public class CycleRobot extends AbstractRobot{

	private double length = 0.1;
	private int num_collisions = 0;

	public CycleRobot(double x, double y, double heading, double turning,
			double distance) {
		super(x, y, heading, turning, distance);

	}

	public CycleRobot(double x, double y, double orientation) {
		super(x, y, 0.0, 0.0, 0.0);
		this.heading = orientation % (2* Math.PI);
	}

	@Override
	public void move(double steering , double distance){
		double straight_line_tolerance = 0.001;
		double max_steering_angle = Math.PI / 4.0;
		if (steering > max_steering_angle) {
			steering = max_steering_angle;
		}
		if (steering < - max_steering_angle) {
			steering = -max_steering_angle;
		}
		if (distance < 0.0) {
			distance = 0.0;
		}

		/**
		 * apply noise
		 *
		 *      steering2 = random.gauss(steering, self.steering_noise)
				distance2 = random.gauss(distance, self.distance_noise)
		 */

		double steering2 = steering + turning_noise * new Random().nextGaussian();
		double distance2 = distance + distance_noise * new Random().nextGaussian();


		double turn = Math.tan(steering2) * distance2 / length;

		if (Math.abs(turn) < straight_line_tolerance) {
			x = x + (distance * Math.cos(heading));
			y = y + (distance * Math.sin(heading));
			heading = (heading + turn) %  (2.0 * Math.PI);
		}else{

			double radius = distance2 / turn;
			double cx = x - (Math.sin(heading) * radius);
			double cy = y + (Math.cos(heading) * radius);
			heading = (heading + turn) %  (2.0 * Math.PI);
			x = cx + (Math.sin(heading)* radius);
			y = cy - (Math.cos(heading)* radius);
		}
	}

	public boolean checkGoal(int[] goal) {
		double threshold = 0.2;
		double dist = Math.sqrt(Math.pow((goal[0] - x),2) + Math.pow((goal[1] - y),2));
		return dist< threshold;
	}

	public void checkCollissions(int[][] grid) {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == 1) {
					double dist = Math.sqrt(Math.pow((x - i),2) + Math.pow((y - j),2));
					if (dist < 0.5) {
						num_collisions++;
					}
				}
			}
		}
	}

	public int getCollisions() {
		return num_collisions;
	}


	public double measurementProb(double[] measurement){

		double errorX = measurement[0] - x;
		double errorY = measurement[1] - y;

		double error = Math.exp(-1* (errorX * errorX) / (measurement_noise * measurement_noise) / 2 / Math.sqrt(2.0 * Math.PI * (measurement_noise * measurement_noise)) );
		error *= Math.exp(-1* (errorY * errorY) / (measurement_noise * measurement_noise) / 2 / Math.sqrt(2.0 * Math.PI * (measurement_noise * measurement_noise)) );

		return error;
	}

}
