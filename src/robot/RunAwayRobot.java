package robot;

import java.util.Random;

public class RunAwayRobot extends AbstractRobot{

	private final double tolerance = 0.001;
	private final double max_turning_angle = Math.PI;


	public RunAwayRobot(double x , double y , double heading , double turning , double distance) {
		super(x,y,heading,turning,distance);
	}

	public void move(double turning , double distance){


		turning = new Random().nextGaussian() * measurement_noise + turning;
		distance = new Random().nextGaussian() * measurement_noise + distance;

		//physical limitations
		turning = Math.max(-max_turning_angle, turning);
		turning = Math.min(max_turning_angle, turning);
		distance = Math.max(0.0, distance);

		// motion
		heading += turning;
		heading = angle_trunc(heading);
		x += distance * Math.cos(heading);
		y += distance * Math.sin(heading);

	}

	public void sense(){
		// add gaussian measurement noise to location
		x = new Random().nextGaussian() * measurement_noise + x;
		y = new Random().nextGaussian() * measurement_noise + y;
	}

	private double angle_trunc(double angle){
		while (angle < 0.0) {
			angle += Math.PI * 2;
		}
		return ((angle+Math.PI) % (Math.PI * 2 )) - Math.PI;
	}

}
