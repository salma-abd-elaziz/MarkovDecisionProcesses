
import java.util.Arrays;

import MDP.algorithms.MDP;
import MDP.algorithms.policyitr.Policyitr;
import MDP.algorithms.valueitr.ValueIteration;

public class Main {
	public static void main(String[] args)  {
		
		int[] rs = {100, 3, 0, -3};
		MDP mdp = null;
		for (int p = 0; p < 4; p++) { 	
			mdp =  new MDP(3, 3, 0.9, rs[p], 0.8, 0.1, 0.1);
			
			ValueIteration vi = new ValueIteration();
			double[][] u = vi.valueIteration(mdp, 0.01);
			System.out.println("--------------------- r = " + rs[p] + " ----------------------------------");
			System.out.println("The Utilities!");
			for (int i = 0; i < u.length; i++) 
				System.out.println(Arrays.toString(u[i]));
			
			System.out.println("The Policy!");
			for (int k = 0; k < ValueIteration.policy.length; k++) 
				System.out.println(Arrays.toString(ValueIteration.policy[k]));	
		}
		
		int r = 100;
		mdp = new MDP(3, 3, 0.9, r, 0.8, 0.1, 0.1);
		System.out.println("------------------ r = "+ r + " -------------------");
		Policyitr polictyIteration = new Policyitr(mdp);
		r = 3;
		mdp = new MDP(3, 3, 0.9, r, 0.8, 0.1, 0.1);
		System.out.println("------------------ r = "+ r + " -------------------");
		polictyIteration = new Policyitr(mdp);
		r = 0;
		mdp = new MDP(3, 3, 0.9, r, 0.8, 0.1, 0.1);
		System.out.println("------------------ r = "+ r + " -------------------");
		polictyIteration = new Policyitr(mdp);
		r = -3;
		mdp = new MDP(3, 3, 0.9, r, 0.8, 0.1, 0.1);
		System.out.println("------------------ r = "+ r + " -------------------");
		polictyIteration = new Policyitr(mdp);
	}
}
