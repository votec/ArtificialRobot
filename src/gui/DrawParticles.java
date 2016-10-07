package gui;

import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import robot.CycleRobot;

public class DrawParticles {

	private static final double SIZE_PARTICLE = 2;
	private static final double OFFSET = 40;
	private static final double SIZE_CIRCLE = 40;
	private GraphicsContext gc;

	public DrawParticles(GraphicsContext gc) {
		this.gc = gc;
	}

	public void drawParticles(List<CycleRobot> particles) {

		gc.setFill(Color.BLUE);
        gc.setStroke(Color.BLUE);

        for (CycleRobot r : particles) {
        	System.out.println("draw x: " + r.getY()*SIZE_CIRCLE + OFFSET);
        	System.out.println("draw y: " + r.getX()*SIZE_CIRCLE + OFFSET);
        	gc.fillOval(r.getY()*SIZE_CIRCLE + OFFSET, r.getX()*SIZE_CIRCLE +OFFSET, SIZE_PARTICLE, SIZE_PARTICLE);
		}

		System.out.println("---------------------------------------------");

	}

	public void clear() {
		gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

	}

}
