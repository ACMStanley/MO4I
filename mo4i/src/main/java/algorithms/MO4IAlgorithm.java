package algorithms;
import java.util.List;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.operator.crossover.CrossoverOperator;
import org.uma.jmetal.operator.mutation.MutationOperator;
import org.uma.jmetal.operator.selection.SelectionOperator;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;

import mo4i.main.Client;
import problem.MO4IProblem;
import util.DirectorySettings;
import util.FrontAdjuster;

public abstract class MO4IAlgorithm {
	
	protected MO4IProblem problem;
	protected Algorithm<List<DoubleSolution>> algorithm;
    protected CrossoverOperator<DoubleSolution> crossover;
    protected MutationOperator<DoubleSolution> mutation;
    protected SelectionOperator<List<DoubleSolution>, DoubleSolution> selection;
    protected double crossoverProbability = 0.9;
    protected double crossoverDistributionIndex = 20.0;
    protected double mutationProbability;
    protected double mutationDistributionIndex = 20.0;
    protected int populationSize = 100;
	
	public abstract void runAlgorithm();
	
	public final void run() {
		this.problem = new MO4IProblem();
		mutationProbability = 1.0 / problem.getNumberOfVariables();
		
		runAlgorithm();
		
		FrontAdjuster.flipFront(Client.getProblemSettings().getMinMax());
	}
	
	public void printFinalSolutionSet(List<? extends Solution<?>> population) {

	    new SolutionListOutput(population)
	        .setVarFileOutputContext(new DefaultFileOutputContext(DirectorySettings.getVariablesPath(), ","))
	        .setFunFileOutputContext(new DefaultFileOutputContext(DirectorySettings.getFrontPath(), ","))
	        .print();
	  }
}
