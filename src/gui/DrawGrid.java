package gui;

import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawGrid {

	private static final int SIZE_CIRCLE = 40;
	private static final int OFFSET = 40;
	private static final int OFFSET_MID = 60;
	private GraphicsContext gc;

	public DrawGrid(GraphicsContext gc ) {
		this.gc = gc;
	}

	/**
	 * Draw given grid.
	 * @param grid
	 */
	public void drawGrid(int[][] grid) {

		gc.setFill(Color.BLACK);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);

        gc.strokeRect(OFFSET, OFFSET,grid[0].length  * SIZE_CIRCLE , grid.length  * SIZE_CIRCLE);

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == 1) {
					gc.fillOval(j*SIZE_CIRCLE + OFFSET, i*SIZE_CIRCLE +OFFSET, SIZE_CIRCLE, SIZE_CIRCLE);
				}
			}
		}
		gc.setFill(Color.RED);
	    gc.fillText("Z", grid[0].length  * SIZE_CIRCLE + 15,  grid.length  * SIZE_CIRCLE + 20);
		gc.setFill(Color.GREEN);
	    gc.fillText("S", OFFSET_MID, OFFSET_MID);
//      gc.strokeLine(40, 10, 10, 40);

	}

	/**
	 * Draw planned path
	 * @param path
	 */
	public void drawPath(List<double[]> path , Color c) {

        gc.setStroke(c);
        gc.setLineWidth(1);
		for (int i = 1; i < path.size() ; i++) {
			gc.strokeLine(path.get(i-1)[1]*SIZE_CIRCLE + OFFSET_MID , path.get(i-1)[0]*SIZE_CIRCLE+ OFFSET_MID , path.get(i)[1]*SIZE_CIRCLE + OFFSET_MID,path.get(i)[0] * SIZE_CIRCLE + OFFSET_MID );
		}
	}

	public void drawRobot(double x, double y) {
		gc.setFill(Color.RED);
		gc.fillOval(-1*y*SIZE_CIRCLE + OFFSET_MID, x*SIZE_CIRCLE +OFFSET_MID, 3, 3);
	}

}
