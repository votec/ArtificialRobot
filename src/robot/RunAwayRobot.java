package robot;

import java.util.Random;

public class RunAwayRobot extends AbstractRobot{

	private final double tolerance = 0.001;



	public RunAwayRobot(double x , double y , double heading , double turning , double distance) {
		super(x,y,heading,turning,distance);
	}



	public void move_in_Circle(){
		super.move(turning, distance);
	}





}
