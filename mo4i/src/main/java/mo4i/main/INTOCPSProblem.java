package mo4i.main;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.problem.doubleproblem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;

import Evaluators.BasicSystem;
import util.ReverseCoupler;

public class INTOCPSProblem extends AbstractDoubleProblem{
	
	public INTOCPSProblem() {
		setNumberOfVariables(3);
	    setNumberOfObjectives(2);
	    setName("INTOCPSProblem");
	    
	    List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables()) ;
	    List<Double> upperLimit = new ArrayList<>(getNumberOfVariables()) ;

	    for (int i = 0; i < getNumberOfVariables(); i++) {
	      lowerLimit.add(0.0);
	      upperLimit.add(1.0);
	    }

	    setVariableBounds(lowerLimit, upperLimit);
	}
	
	@Override
	public void evaluate(DoubleSolution solution) {
		double[] objectives = BasicSystem.evaluate(solution);
		solution.setObjective(0,(ReverseCoupler.maximiseObjective[0] ? -1 : 1) * objectives[0]);
		solution.setObjective(1,(ReverseCoupler.maximiseObjective[1] ? -1 : 1) * objectives[1]);
	}
	
}
