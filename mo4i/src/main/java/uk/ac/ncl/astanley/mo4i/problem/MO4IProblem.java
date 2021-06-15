package uk.ac.ncl.astanley.mo4i.problem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.uma.jmetal.problem.doubleproblem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;
import main.Client;

@SuppressWarnings("serial")
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
		
		//solution.setVariable(1,-solution.getVariable(0));
		List<Double> variables = solution.getVariables();
		
		double[] objectives = Client.getProblemSettings().calculateObjective(variables);
		for(int i = 0; i < Client.getProblemSettings().getNumberOfObjectives(); i++) {
			solution.setObjective(i,(Client.getProblemSettings().getMinMax().get(i) ? -1 : 1) * objectives[i]);
		}
		
		String output = variables.get(0).toString();
		for(int i = 1; i < variables.size(); i++) {
			output = output + "," + variables.get(i);
		}
		for(int i = 0; i < objectives.length; i++) {
			output = output + "," + objectives[i];
		}
		output = output + "\n";
		
		try {
			Client.outputRun.write(output);
			Client.outputRun.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		long endTime = System.currentTimeMillis();
		System.out.println("Simulation on thread " + Thread.currentThread().getId() + " completed in: " + Long.toString(endTime - startTime) + "ms.");
	}
	
}
