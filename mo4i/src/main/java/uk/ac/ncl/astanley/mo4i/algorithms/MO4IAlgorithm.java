package uk.ac.ncl.astanley.mo4i.algorithms;

import java.util.List;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.operator.crossover.CrossoverOperator;
import org.uma.jmetal.operator.mutation.MutationOperator;
import org.uma.jmetal.operator.selection.SelectionOperator;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;
import org.uma.jmetal.util.archive.Archive;
import org.uma.jmetal.util.evaluator.SolutionListEvaluator;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;

import uk.ac.ncl.astanley.mo4i.evaluators.MO4IMultiThreadEvaluator;
import uk.ac.ncl.astanley.mo4i.main.Client;
import uk.ac.ncl.astanley.mo4i.problem.MO4IProblem;
import uk.ac.ncl.astanley.mo4i.util.DirectorySettings;
import uk.ac.ncl.astanley.mo4i.util.FrontAdjuster;
/*
Author: Aiden Stanley
Purpose: An abstract class to define the structure and defaults of multi-objective optimisation algorithms within the MO4I application
*/
public abstract class MO4IAlgorithm {

	protected MO4IProblem problem;
	protected Algorithm<List<DoubleSolution>> algorithm;
	protected CrossoverOperator<DoubleSolution> crossover;
	protected MutationOperator<DoubleSolution> mutation;
	protected SelectionOperator<List<DoubleSolution>, DoubleSolution> selection;
	protected Archive<DoubleSolution> archive;
	protected SolutionListEvaluator<DoubleSolution> evaluator;
	
	protected int maxEvals = Client.getProblemSettings().getMaxEvals();
	protected double crossoverProbability = 0.9;
	protected double crossoverDistributionIndex = 20.0;
	protected double mutationDistributionIndex = 20.0;
	protected int populationSize;
	protected double mutationProbability;

	protected abstract void runAlgorithm();
	
	//runs the derived algorithm
	//declared final to ensure this function is called through the base class
	public final void run() {
		this.problem = new MO4IProblem();
		mutationProbability = 1.0 / problem.getNumberOfVariables();
		populationSize = 10 * problem.getNumberOfVariables();
		int threadCount = Client.getProblemSettings().getThreadCount();
		
		//choose an evaluator based on the number of threads chosen
		if(threadCount == 1) {
			evaluator = new SequentialSolutionListEvaluator<DoubleSolution>();
		}
		else {
			evaluator = new MO4IMultiThreadEvaluator<DoubleSolution>(threadCount - 1);
		}
		
		//run the algorithm
		long startTime = System.currentTimeMillis();
		runAlgorithm();
		long endTime = System.currentTimeMillis();
		
	    System.out.println("Took " + (endTime - startTime) + "ms to complete " + maxEvals + " evaluations");
		FrontAdjuster.flipFront(Client.getProblemSettings().getMinMax());
		
	}
	
	//writes the optimisation results to file
	public void printFinalSolutionSet(List<? extends Solution<?>> population) {
		new SolutionListOutput(population)
				.setVarFileOutputContext(new DefaultFileOutputContext(DirectorySettings.getVariablesPath(), ","))
				.setFunFileOutputContext(new DefaultFileOutputContext(DirectorySettings.getFrontPath(), ",")).print();
	}
}
