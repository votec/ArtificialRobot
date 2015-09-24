package robot;

public class AbstractRobot {


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

}
