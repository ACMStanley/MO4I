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
		double[] objectives = BasicSystem.evaluate(solution);
		solution.setObjective(0,(Client.getProblemSettings().getMinMax()[0] ? -1 : 1) * objectives[0]);
		solution.setObjective(1,(Client.getProblemSettings().getMinMax()[1] ? -1 : 1) * objectives[1]);
	}
	
}
