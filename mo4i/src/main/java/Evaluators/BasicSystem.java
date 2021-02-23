package Evaluators;

import org.uma.jmetal.solution.doublesolution.DoubleSolution;

public class BasicSystem {
	
	public static double[] evaluate(DoubleSolution solution) {
		return evaluate(solution.getVariable(0),solution.getVariable(1),solution.getVariable(2));
	}
	
	public static double[] evaluate(double x, double y, double z) {
		
		double objective1 = (x + y - z);
		double objective2 = (x + y - z);
		
		double[] objectives = {objective1,objective2};
		
		return objectives;
	}
}
