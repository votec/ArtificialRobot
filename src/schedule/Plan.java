package schedule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
}
