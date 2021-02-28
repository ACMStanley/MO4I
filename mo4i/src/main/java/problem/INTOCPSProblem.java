package problem;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.problem.doubleproblem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;

import Evaluators.BasicSystem;
import util.ReverseCoupler;

public class INTOCPSProblem extends AbstractDoubleProblem{
	
	private static boolean[] maximiseObjective;
	
	public INTOCPSProblem(int noOfVars, int noOfObjectives, List<Double> lowerLimits, List<Double> upperLimits) {
		if(noOfVars < 1) {
			throw new IllegalArgumentException("Number of variables must be greater than 0!");
		}
		if(noOfObjectives < 1) {
			throw new IllegalArgumentException("Number of objectives must be greater than 0!");
		}
		
		if(upperLimits.size() != lowerLimits.size() || upperLimits.size() != noOfVars || lowerLimits.size() != noOfVars) {
			throw new IllegalArgumentException("Number of bounds must match number of variables!");
		}
		
		setNumberOfVariables(noOfVars);
	    setNumberOfObjectives(noOfObjectives);
	    setName("INTOCPSProblem");

	    for (int i = 0; i < getNumberOfVariables(); i++) {
	      lowerLimits.add(0.0);
	      upperLimits.add(1.0);
	    }

	    setVariableBounds(lowerLimits, upperLimits);
	}
	
	public boolean[] getMinOrMax(){
		return maximiseObjective;
	}
	
	@Override
	public void evaluate(DoubleSolution solution) {
		double[] objectives = BasicSystem.evaluate(solution);
		for(int i = 0; i < getNumberOfObjectives(); i++) {
			solution.setObjective(0,(ProblemSettings.getMinOrMax()[i] ? -1 : 1) * objectives[i]);
		}
	}
	
}
