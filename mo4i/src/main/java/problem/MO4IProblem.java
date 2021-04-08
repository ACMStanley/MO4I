package problem;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.problem.doubleproblem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;

import Evaluators.BasicSystem;
import mo4i.main.Client;

public class MO4IProblem extends AbstractDoubleProblem{
	
	public MO4IProblem() {
		setNumberOfVariables(Client.getProblemSettings().getNumberOfVars());
	    setNumberOfObjectives(Client.getProblemSettings().getNumberOfObjectives());
	    setName("INTOCPSProblem");

	    setVariableBounds(Client.getProblemSettings().getLowerVarLimits(), Client.getProblemSettings().getUpperVarLimits());
	}
	
	@Override
	public void evaluate(DoubleSolution solution) {
		long startTime = System.currentTimeMillis();
		
		double[] objectives = Client.getProblemSettings().calculateObjective(solution.getVariables());
		for(int i = 0; i < Client.getProblemSettings().getNumberOfObjectives(); i++) {
			solution.setObjective(i,(Client.getProblemSettings().getMinMax().get(i) ? -1 : 1) * objectives[i]);
		}
		
		long endTime = System.currentTimeMillis();
		System.out.println("Iteration completed in: " + Long.toString(endTime - startTime) + "ms.");
	}
	
}
