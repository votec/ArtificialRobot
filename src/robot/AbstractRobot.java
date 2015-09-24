package robot;

import java.util.Random;

public class AbstractRobot {

	private final double max_turning_angle = Math.PI;

	protected double x;
	protected double y;
	protected double distance;
	protected double turning;
	protected double heading;

	protected double turning_noise;
	protected double distance_noise;
	protected double measurement_noise;

	public AbstractRobot(double x , double y ,double heading, double turning , double distance) {
		this.x = x;
		this.y = y;
		this.distance = distance;
		this.heading = heading;
		this.turning = turning;
		this.distance_noise = 0.0;
		this.measurement_noise = 0.0;
		this.turning_noise = 0.0;
	}

	/**
	 * Maybe only relevant for particle
	 * @param turning_noise
	 * @param distance_noise
	 * @param turning_noise
	 */
	public void setNoise(double turning_noise , double distance_noise , double measurement_noise){
		this.distance_noise = distance_noise;
		this.measurement_noise = measurement_noise;
		this.turning_noise = turning_noise;
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

	public static double calculateDistance(double[] point1 , double[] point2){
		return Math.sqrt(((point2[0] - point1[0])*(point2[0] - point1[0]) + ((point2[1] - point1[1])*(point2[1] - point1[1]))));
	}

	private double angle_trunc(double angle){
		while (angle < 0.0) {
			angle += Math.PI * 2;
		}
		return ((angle+Math.PI) % (Math.PI * 2 )) - Math.PI;
	}

	public void sense(){
		// add gaussian measurement noise to location
		x = new Random().nextGaussian() * measurement_noise + x;
		y = new Random().nextGaussian() * measurement_noise + y;
	}

	public double getDistance() {
		return distance;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

}
