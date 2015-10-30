package robot;



public class CycleRobot extends AbstractRobot{

	private double length = 0.5;
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
		 * apply noise TODO
		 */
		double steering2 = steering;
		double distance2 = distance;


		double turn = Math.tan(steering2) * distance2 / length;
		System.out.println("turn -> " + turn);
		if (Math.abs(turn) < straight_line_tolerance) {
			System.out.println("straight line");
			x = x + (distance * Math.cos(heading));
			y = y + (distance * Math.sin(heading));
			heading = (heading + turn) %  (2.0 * Math.PI);
		}else{

			double radius = distance2 / turn;
			double cx = x - (Math.sin(heading) * radius);
			double cy = y - (Math.cos(heading) * radius);
			heading = (heading + turn) %  (2.0 * Math.PI);
			x = cx + (Math.sin(heading)* radius);
			y = cy + (Math.cos(heading)* radius);

			System.out.println("bycicle model -> " + heading + " x: " + x + " y: " + y);

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

}
