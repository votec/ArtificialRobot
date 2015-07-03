package world;

public class World {
	
	
		public static int[][] grid = {{0,1,0,0,0,0} 
									, {0,1,0,1,1,0}
									, {0,1,0,1,0,0}
									, {0,0,0,1,0,1}
									, {0,1,0,1,0,0}};
		
		public static int[] start = {0,0};
	
		public static int[] goal = {grid.length -1, grid[0].length -1};
	
	

}
