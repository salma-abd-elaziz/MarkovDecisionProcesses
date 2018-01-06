package MDP.algorithms.valueitr;
import java.awt.Point;
import java.util.Arrays;

import MDP.algorithms.MDP;
public class ValueIteration {
	
	static final Point termialState= new Point(0, 2);
	static final int TRUE_DIRECTION = 0;
	static final int RIGHT_DIRECTION = 1;
	static final int LEFT_DIRECTION = 2;
	static final int UP = 0;
	static final int DOWN = 1;
	static final int RIGHT = 2;
	static final int LEFT = 3;
	
	public static String[][] policy;
	
	public ValueIteration(){
		policy = new String[3][3];
	}
	
	public double[][] valueIteration(MDP mdp, double eps) {
		double delta = 0.0;
		double[][] u = new double[3][3];
		double[][] uDash = new double[3][3];
		uDash[termialState.x][termialState.y] = 10.0;
		
		do{
			copyArray(u, uDash);
			delta = 0.0;
			for (int i = 2; i >= 0; i--) {
				for (int j = 0; j < 3; j++) {
					if (termialState.equals(new Point(i, j))) break;
					uDash[i][j] = getMaxUtility(i, j, u, mdp);
					if (Math.abs(uDash[i][j] - u[i][j]) > delta) 
						delta = Math.abs(uDash[i][j] - u[i][j]);	
				}
			}
		}while (delta >= (eps * (1 - mdp.getDiscountFactor()))/ mdp.getDiscountFactor());
			
		return u;
	}
	
	private void copyArray(double[][] x, double[][] y) {
		for (int i = 0; i < y.length; i++) {
			x[i] = Arrays.copyOf(y[i], y[i].length);
		}
	}
	
	private double getMaxUtility(int row, int col, double[][] uDash, MDP mdp) {
		double maximum = Double.NEGATIVE_INFINITY;
		double[] transition= mdp.getTransition();
		
		//up
		Point[] neighbors = mdp.getDirection(new Point(row, col), UP);
		double val = transition[TRUE_DIRECTION] * (mdp.getReward()[neighbors[TRUE_DIRECTION].x][neighbors[TRUE_DIRECTION].y] + mdp.getDiscountFactor() * uDash[neighbors[TRUE_DIRECTION].x][neighbors[TRUE_DIRECTION].y]) + 
				transition[RIGHT_DIRECTION]  * (mdp.getReward()[neighbors[RIGHT_DIRECTION].x][neighbors[RIGHT_DIRECTION].y] + mdp.getDiscountFactor() * uDash[neighbors[RIGHT_DIRECTION].x][neighbors[RIGHT_DIRECTION].y]) +
				transition[LEFT_DIRECTION] * (mdp.getReward()[neighbors[LEFT_DIRECTION].x][neighbors[LEFT_DIRECTION].y] + mdp.getDiscountFactor() * uDash[neighbors[LEFT_DIRECTION].x][neighbors[LEFT_DIRECTION].y]);
		if (maximum < val) {
			maximum = val;
			policy[row][col] = "up";
		}
		// left
		neighbors = mdp.getDirection(new Point(row, col), LEFT);
		val = transition[TRUE_DIRECTION] * (mdp.getReward()[neighbors[TRUE_DIRECTION].x][neighbors[TRUE_DIRECTION].y] + mdp.getDiscountFactor() * uDash[neighbors[TRUE_DIRECTION].x][neighbors[TRUE_DIRECTION].y]) + 
				transition[RIGHT_DIRECTION]  * (mdp.getReward()[neighbors[RIGHT_DIRECTION].x][neighbors[RIGHT_DIRECTION].y] + mdp.getDiscountFactor() * uDash[neighbors[RIGHT_DIRECTION].x][neighbors[RIGHT_DIRECTION].y]) +
				transition[LEFT_DIRECTION] * (mdp.getReward()[neighbors[LEFT_DIRECTION].x][neighbors[LEFT_DIRECTION].y] + mdp.getDiscountFactor() * uDash[neighbors[LEFT_DIRECTION].x][neighbors[LEFT_DIRECTION].y]);
		if (maximum <= val) {
			maximum = val;
			policy[row][col] = "left";
		}
		// right
		neighbors = mdp.getDirection(new Point(row, col), RIGHT);
		val = transition[TRUE_DIRECTION] * (mdp.getReward()[neighbors[TRUE_DIRECTION].x][neighbors[TRUE_DIRECTION].y] + mdp.getDiscountFactor() * uDash[neighbors[TRUE_DIRECTION].x][neighbors[TRUE_DIRECTION].y]) + 
				transition[RIGHT_DIRECTION]  * (mdp.getReward()[neighbors[RIGHT_DIRECTION].x][neighbors[RIGHT_DIRECTION].y] + mdp.getDiscountFactor() * uDash[neighbors[RIGHT_DIRECTION].x][neighbors[RIGHT_DIRECTION].y]) +
				transition[LEFT_DIRECTION] * (mdp.getReward()[neighbors[LEFT_DIRECTION].x][neighbors[LEFT_DIRECTION].y] + mdp.getDiscountFactor() * uDash[neighbors[LEFT_DIRECTION].x][neighbors[LEFT_DIRECTION].y]);
				
		if (maximum <= val) {
			maximum = val;
			policy[row][col] = "right";
		}
		// down
		neighbors = mdp.getDirection(new Point(row, col), DOWN);
		val = transition[TRUE_DIRECTION] * (mdp.getReward()[neighbors[TRUE_DIRECTION].x][neighbors[TRUE_DIRECTION].y] + mdp.getDiscountFactor() * uDash[neighbors[TRUE_DIRECTION].x][neighbors[TRUE_DIRECTION].y] )+ 
				transition[RIGHT_DIRECTION]  * (mdp.getReward()[neighbors[RIGHT_DIRECTION].x][neighbors[RIGHT_DIRECTION].y] + mdp.getDiscountFactor() * uDash[neighbors[RIGHT_DIRECTION].x][neighbors[RIGHT_DIRECTION].y]) +
				transition[LEFT_DIRECTION] * (mdp.getReward()[neighbors[LEFT_DIRECTION].x][neighbors[LEFT_DIRECTION].y] + mdp.getDiscountFactor() * uDash[neighbors[LEFT_DIRECTION].x][neighbors[LEFT_DIRECTION].y]);
		if (maximum < val) {
			maximum = val;
			policy[row][col] = "down";
		}
		return maximum;
	}
	
}
