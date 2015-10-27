package schedule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import robot.Movement;


public class Plan {

	private final int cost = 1;
	private int[][] grid;
	private int[] startPosition;
	private int[] goal;
	private List<int[]> path;
	private int[][] heuristic;


	public Plan(int[][] grid , int[] startPosition , int[] goal) {

		this.grid = grid;
		this.startPosition= startPosition;
		this.goal = goal;
		this.path = new ArrayList<>();
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
				+ ", goal=" + Arrays.toString(goal) + ", path=" + path + "\n"
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

	private void a_star(){
		if (heuristic == null) {
			System.out.println("Pls. define heuristic for A*");


			int[][] closed = initIntegerArray(grid);
			String[][] action = initStringArray(grid);

			int x = startPosition[0];
			int y = startPosition[1];
			closed[x][y] = 1;
			int h = heuristic[x][y];
			int g = 0;
			int f = g + h;

			List<Integer> openInit = Arrays.asList(f,g,h,x,y);
			List<List<Integer>> openList = Arrays.asList(openInit);



			int count = 0;
			while (true) {

				if (openList.size() == 0) {
					System.out.println("Search terminated without sucess...");
					break;
				}else{
					//proof sort!
					openList.sort(null);
					// reverse
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
						if ((x2 > 0 ) && (x2 < grid.length) && (y2 > 0) && (y2 < grid[0].length)) {
							if (closed[x2][y2]  == 0 && grid[x2][y2] == 0) {
								int g2 = g + cost;
								int h2 = h + heuristic[x2][y2];
								int f2 = h2 + g2;
								openList.add(Arrays.asList(f2,g2,h2,x2,y2));
								closed[x2][y2] = 1;
								action[x2][y2] = move.getDirectionSymbol();
							}
						}

					}
				}
				count++;
			}
			return;
		}

	}

	private int[][] initIntegerArray(int[][] grid2) {
		int[][] copy = grid2.clone();
		for (int i = 0; i < copy.length; i++) {
			for (int j = 0; j < copy.length; j++) {
				copy[i][j] = 0;
			}
		}
		return copy;
	}

	private String[][] initStringArray(int[][] grid2) {
		String[][] copy = new String[grid.length][grid[0].length];
		for (int i = 0; i < copy.length; i++) {
			for (int j = 0; j < copy.length; j++) {
				copy[i][j] = "";
			}
		}
		return copy;
	}

}
