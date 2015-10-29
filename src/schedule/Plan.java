package schedule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import robot.Movement;


public class Plan {

	private static final double tolerance = 0.000001;
	private static final double weight_data = 0.005;
	private static final double weight_smooth = 0.1;
	private final int cost = 1;
	private int[][] grid;
	private int[] startPosition;
	private int[] goal;
	private int[][] heuristic;


	public Plan(int[][] grid , int[] startPosition , int[] goal) {

		this.grid = grid;
		this.startPosition= startPosition;
		this.goal = goal;
		this.heuristic = makeHeuristic(grid,goal,cost);

	}

	private int[][] makeHeuristic(int[][] grid, int[] goal, int cost) {
		int[][] heuristic = new int[grid.length][grid[0].length];

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				int sum = Math.abs(i-goal[0]) + Math.abs(j-goal[1]);
				heuristic[i][j] = sum;
			}
		}
		return heuristic;
	}


	@Override
	public String toString() {
		return "Plan [cost=" + cost + ", startPosition=" + Arrays.toString(startPosition)
				+ ", goal=" + Arrays.toString(goal) + ", "
				+ ", heuristic=" + "\n" + arrayToString(heuristic) + ", grid=" + "\n" + arrayToString(grid) +  "]";
	}

	public static String arrayToString(int[][] a) {

	    String aString;
	    aString = "";
	    int column;
	    int row;

	    for (row = 0; row < a.length; row++) {
	        for (column = 0; column < a[0].length; column++ ) {
	        aString = aString + " " + a[row][column];
	        }
	    aString = aString + "\n";
	    }

	    return aString;
	}
	public static String printArray(Movement[][] a) {

	    String aString;
	    aString = "";
	    int column;
	    int row;

	    for (row = 0; row < a.length; row++) {
	        for (column = 0; column < a[0].length; column++ ) {
	        	System.out.print( "  " + a[row][column].getDirectionSymbol());
	        }
	       System.out.println();
	    }

	    return aString;
	}

	/**
	 * a star algortihm to find shortest path.
	 * @return
	 */
	public List<double[]> a_star(){

		List<double[]> path = new ArrayList<>();

		if (heuristic == null) {
			System.out.println("Pls. define heuristic for A*");
			return path;
		}

			int[][] closed = initIntegerArray(grid);
			Movement[][] action = initActionArray(grid);

			int x = startPosition[0];
			int y = startPosition[1];
			closed[x][y] = 1;
			int h = heuristic[x][y];
			int g = 0;
			int f = g + h;

			List<Integer> openInit = Arrays.asList(f,g,h,x,y);
			List<List<Integer>> openList = new ArrayList<List<Integer>>();
			openList.add(openInit);

			while (true) {

				if (openList.size() == 0) {
					System.out.println("Search terminated without sucess...");
					break;
				}else{
					openList.sort(new ArrayComparator());
					Collections.reverse(openList);

					x = openList.get(0).get(3);
					y = openList.get(0).get(4);
					g = openList.get(0).get(1);
					openList.remove(0);
				}

				if (x == goal[0] && y == goal[1]) {
					System.out.println("A* suchessfull");
					break;
				}else{
					//expand
					for (Movement move : Movement.values()) {
						int x2 = x + move.getMove();
						int y2 = y + move.getTurn();
						if ((x2 >= 0 ) && (x2 < grid.length) && (y2 >= 0) && (y2 < grid[0].length)) {
							if (closed[x2][y2]  == 0 && grid[x2][y2] == 0) {
								int g2 = g + cost;
								int h2 = h + heuristic[x2][y2];
								int f2 = h2 + g2;
								openList.add(Arrays.asList(f2,g2,h2,x2,y2));
								closed[x2][y2] = 1;
								action[x2][y2] = move;
							}
						}
					}
				}

			}
//			System.out.println(printArray(action));

			x = goal[0];
			y = goal[1];
			path.add(0 , new double[]{x,y});
			while (true) {

				if (x == startPosition[0] && y == startPosition[1]) {
					break;
				}else{
					int x2 = x - action[x][y].getMove();
					int y2 = y - action[x][y].getTurn();
					x = x2;
					y = y2;
					path.add(0 ,new double[]{x,y});
				}
			}
			return path;
	}

	/**
	 * Smooth find path.
	 * @param path
	 * @return
	 */
	public List<double[]> smoothPath(List<double[]> path) {
		if (path == null || path.size() ==0) {
			System.out.println("First call a star!");
			return path;
		}

		List<double[]> smooth_path = new ArrayList<double[]>();

		for (double[] d : path) {
			smooth_path.add(new double[]{d[0],d[1]});
		}

		double change = tolerance;
		while (change >= tolerance) {
			change = 0.0;

			for (int i = 1; i < smooth_path.size()-1; i++) {
				for (int j = 0; j < 1; j++) {
					double aux = smooth_path.get(i)[j];

					smooth_path.get(i)[j] += weight_data * (path.get(i)[j] - smooth_path.get(i)[j]);
					smooth_path.get(i)[j] += weight_smooth * (smooth_path.get(i-1)[j] + smooth_path.get(i+1)[j] - (2 * smooth_path.get(i)[j]));

					if (i>=2) {
						smooth_path.get(i)[j] += 0.5 * weight_smooth *  (2 * smooth_path.get(i-1)[j] - smooth_path.get(i-2)[j] - smooth_path.get(i)[j]);
					}

					if (i<=path.size()-3) {
						smooth_path.get(i)[j] += 0.5 * weight_smooth *  (2 * smooth_path.get(i+1)[j] - smooth_path.get(i+2)[j] - smooth_path.get(i)[j]);
					}
					change += Math.abs(aux - smooth_path.get(i)[j]);
				}
			}
		}
		return smooth_path;
	}

	private int[][] initIntegerArray(int[][] grid2) {
		int[][] copy = new int[grid.length][grid[0].length];
		for (int i = 0; i < copy.length; i++) {
			for (int j = 0; j < copy.length; j++) {
				copy[i][j] = 0;
			}
		}
		return copy;
	}

	private Movement[][] initActionArray(int[][] grid2) {
		Movement[][] copy = new Movement[grid.length][grid[0].length];
		for (int i = 0; i < copy.length; i++) {
			for (int j = 0; j < copy.length; j++) {
				copy[i][j] = Movement.NO_MOVE;
			}
		}
		return copy;
	}
}
