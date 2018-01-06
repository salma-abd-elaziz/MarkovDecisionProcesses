package MDP.algorithms.policyitr;

import java.awt.Point;
import java.lang.reflect.Field;
import java.util.Arrays;
import MDP.algorithms.MDP;

public class Policyitr {
	private MDP mdp;
	private double[][] utilities;
	private String[][] policies;
	
	public Policyitr(MDP mdp) {
		this.mdp = mdp;
		utilities = new double [mdp.getNoRow()][mdp.getNoCol()];
		utilities[0][2] = 10;
		policies = new String [mdp.getNoRow()][mdp.getNoCol()];
		IntializePolicies();
		System.out.println("Intial Utility Values:");
		System.out.println("======================");
		System.out.println(Arrays.deepToString(utilities));
		System.out.println("Intial Policies:");
		System.out.println("=================");
		System.out.println(Arrays.deepToString(policies));
		mainAlgorithm();
		System.out.println("Final Utility Values:");
		System.out.println("======================");
		System.out.println(Arrays.deepToString(utilities));
		System.out.println("Final Policies:");
		System.out.println("======================");
		System.out.println(Arrays.deepToString(policies));
	}

	private void IntializePolicies() {
		for (int row = 0; row < policies.length; row++) {
		    for (int col = 0; col < policies[row].length; col++) {
		    	policies[row][col] = mdp.getActions()[(int)(Math.random() * 4)];
		    }
		}
	}
	
	private double[][] policyEvaluation() {
		double[][] utilitesTemp = new double [mdp.getNoRow()][mdp.getNoCol()];
		utilitesTemp [0][2] = 10; 
		Point[] neighbours = new Point [3];
		for (int row = 0; row < utilities.length; row++) {
		    for (int col = 0; col < utilities[row].length; col++) {
		    	if(col == 2 && row == 0) break;
		    	try {
		    		Field f = MDP.class.getField(policies[row][col]);
					neighbours = mdp.getDirection(new Point(row, col), f.getInt(null));
				} catch (NoSuchFieldException e1) {
					e1.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
		    	utilitesTemp[row][col] = getStateUtility ("TRUE_DIRECTION", neighbours) + 
		    							 getStateUtility ("RIGHT_DIRECTION", neighbours) + 
		    			                 getStateUtility ("LEFT_DIRECTION", neighbours);
		    }
		}
		return utilitesTemp;
	}
	private double getStateUtility (String action, Point[] neighbours) {
		int direction = 0;
		try {
    		Field f = MDP.class.getField(action);
    		direction = f.getInt(null);
		} catch (NoSuchFieldException e1) {
			e1.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return (mdp.getTransition()[direction]) * 
    				((mdp.getReward()[neighbours[direction].x][neighbours[direction].y]) + 
    						(mdp.getDiscountFactor() * utilities[neighbours[direction].x][neighbours[direction].y]));
	}
	private Object[] getMaxUtility(Point state) {
		double maximum = Double.POSITIVE_INFINITY;
		double val = 0;
		String maxDirection = "UP";
		Object[] returnArr = new Object[2];
		//up
		maximum = getStateUtility("TRUE_DIRECTION", mdp.getDirection(state, mdp.UP)) +
				  getStateUtility("RIGHT_DIRECTION", mdp.getDirection(state, mdp.UP)) +
				  getStateUtility("LEFT_DIRECTION", mdp.getDirection(state, mdp.UP));
		// left
		val = getStateUtility("TRUE_DIRECTION", mdp.getDirection(state, mdp.LEFT)) +
		      getStateUtility("RIGHT_DIRECTION", mdp.getDirection(state, mdp.LEFT)) +
		      getStateUtility("LEFT_DIRECTION", mdp.getDirection(state, mdp.LEFT));
		if (maximum < val) {
			maximum = val;
			maxDirection = "LEFT";
		}
		// right
		val = getStateUtility("TRUE_DIRECTION", mdp.getDirection(state, mdp.RIGHT)) +
			  getStateUtility("RIGHT_DIRECTION", mdp.getDirection(state, mdp.RIGHT)) +
			  getStateUtility("LEFT_DIRECTION", mdp.getDirection(state, mdp.RIGHT));
		if (maximum < val) {
			maximum = val;
			maxDirection = "RIGHT";
		}
		// down
		val = getStateUtility("TRUE_DIRECTION", mdp.getDirection(state, mdp.DOWN)) +
			  getStateUtility("RIGHT_DIRECTION", mdp.getDirection(state, mdp.DOWN)) +
			  getStateUtility("LEFT_DIRECTION", mdp.getDirection(state, mdp.DOWN));
		if (maximum < val) {
			maximum = val;
			maxDirection = "DOWN";
		}
		returnArr[0] = maximum;
		returnArr[1] = maxDirection;
		return returnArr;
	}
	
	private void mainAlgorithm() {
		boolean unchanged = true;
		do {
			utilities = policyEvaluation();
			unchanged = true;
			for (int row = 0; row < utilities.length; row++) {
				for (int col = 0; col < utilities[row].length; col++) {
					if(col == 2 && row == 0) break;
					Object[] maximum = getMaxUtility(new Point(row, col));
					if((double)maximum[0] > utilities[row][col]) {
						policies [row][col] = (String)maximum[1];
						unchanged = false;
					}
				}
			}
		} while (!unchanged);	
	}
}
