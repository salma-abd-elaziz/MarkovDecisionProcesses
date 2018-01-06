package MDP.algorithms;

import java.awt.Point;
import java.util.Arrays;

public class MDP {
	public static final int TRUE_DIRECTION = 0;
	public static final int RIGHT_DIRECTION = 1;
	public static final int LEFT_DIRECTION = 2;
	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int RIGHT = 2;
	public static final int LEFT = 3;
	private int noCol, noRow;
	private double discountFactor;
	
	
	private double[] transition;
	private String[] actions;
	private int[][] reward;
	public MDP(int noRow, int noCol, double discountFactor, int r,
					double trueDirection, double leftDirection, double rightDirection) {
		this.noRow = noRow;
		this.noCol = noCol;
		this.discountFactor = discountFactor;
		actions = new String [4];
		fillActions(actions);
		reward = new int [noRow][noCol];
		fillReward(reward);
		reward[0][0] = r;
		reward [0][2] = 10;
		transition = new double[3];
		transition[TRUE_DIRECTION] = trueDirection;
		transition[RIGHT_DIRECTION] = rightDirection;
		transition[LEFT_DIRECTION] = leftDirection;
	}

	public String[] getActions() {
		return actions;
	}

	private void fillActions(String[] actions) {
		actions[0] = "UP";
		actions[1] = "DOWN";
		actions[2] = "RIGHT";
		actions[3] = "LEFT";
	}
	
	public int[][] getReward() {
		return reward;
	}

	private void fillReward(int[][] reward) {
		for (int[] row: reward) Arrays.fill(row, -1);
	}

	public double[] getTransition() {
		return transition;
	}

	public double getDiscountFactor() {
		return discountFactor;
	}
	
	public int getNoRow() {
		return noRow;
	}
	
	public int getNoCol() {
		return noRow;
	}
	
	private boolean checkRowBorder(int rowIndex){
		if(0 <= rowIndex && rowIndex < noRow) {
			return true;
		}
		return false;
	}
	private boolean checkColBorder(int colIndex){
		if(0 <= colIndex && colIndex < noCol) {
			return true;
		}
		return false;
	}
	public Point[] getDirection(Point stateIndex, int direction) {
		Point[] upStates = new Point[3];
		int indicator = 0;
		if (UP == direction || LEFT == direction){
			indicator = -1;
		}else if(DOWN == direction || RIGHT == direction){
			indicator = 1;
		}
		if (UP == direction || DOWN == direction){
			if(checkRowBorder((stateIndex.x) + indicator))
				upStates[TRUE_DIRECTION] = new Point((stateIndex.x) + indicator, stateIndex.y);
			else
				upStates[TRUE_DIRECTION] = new Point(stateIndex.x, stateIndex.y);
			if(checkColBorder((stateIndex.y) + indicator))
				upStates[LEFT_DIRECTION] = new Point(stateIndex.x, (stateIndex.y) + indicator);
			else
				upStates[LEFT_DIRECTION] = new Point(stateIndex.x, stateIndex.y);
			if(checkColBorder((stateIndex.y) + (indicator * -1)))
				upStates[RIGHT_DIRECTION] = new Point(stateIndex.x, (stateIndex.y) + (indicator * -1));
			else
				upStates[RIGHT_DIRECTION] = new Point(stateIndex.x, stateIndex.y);
		}else if (LEFT == direction || RIGHT == direction){
			if(checkColBorder((stateIndex.y) + indicator))
				upStates[TRUE_DIRECTION] = new Point(stateIndex.x, (stateIndex.y) + indicator);
			else
				upStates[TRUE_DIRECTION] = new Point(stateIndex.x, stateIndex.y);
			if(checkRowBorder((stateIndex.x) + (indicator * -1)))
				upStates[LEFT_DIRECTION] = new Point(stateIndex.x + (indicator * -1), stateIndex.y);
			else
				upStates[LEFT_DIRECTION] = new Point(stateIndex.x, stateIndex.y);
			if(checkRowBorder((stateIndex.x) + indicator))
				upStates[RIGHT_DIRECTION] = new Point(stateIndex.x + indicator , stateIndex.y);
			else
				upStates[RIGHT_DIRECTION] = new Point(stateIndex.x, stateIndex.y);
		}
		return upStates;
	}	
}
