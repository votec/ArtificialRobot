package robot;

public enum Movement {

		LEFT (0 , -1 , "<"),
		RIGHT (0, 1 , ">"),
		UP (-1 , 0 , "^"),
		DOWN (1 , 0 ,"v" ),
		NO_MOVE(0,0,"-");

		private int move;
		private int turn;
		private String directionSymbol;

		Movement(int move, int turn , String directionSymbol){
			this.move = move;
			this.turn = turn;
			this.directionSymbol = directionSymbol;
		}

		public int getMove() {
			return move;
		}

		public int getTurn() {
			return turn;
		}

		public String getDirectionSymbol() {
			return directionSymbol;
		}


}
