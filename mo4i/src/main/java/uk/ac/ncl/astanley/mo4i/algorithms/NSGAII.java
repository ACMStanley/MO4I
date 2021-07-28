package uk.ac.ncl.astanley.mo4i.algorithms;

import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.operator.crossover.impl.SBXCrossover;
import org.uma.jmetal.operator.mutation.impl.PolynomialMutation;
import org.uma.jmetal.operator.selection.impl.BinaryTournamentSelection;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;

import java.util.List;

public class NSGAII extends MO4IAlgorithm {

	protected void runAlgorithm() throws JMetalException {

		// initialise default operators
		crossover = new SBXCrossover(crossoverProbability, crossoverDistributionIndex);
		mutation = new PolynomialMutation(mutationProbability, mutationDistributionIndex);
		selection = new BinaryTournamentSelection<DoubleSolution>(
				new RankingAndCrowdingDistanceComparator<DoubleSolution>());

		// initialise algorithm builder and set operators
		NSGAIIBuilder<DoubleSolution> builder = new NSGAIIBuilder<DoubleSolution>(problem, crossover, mutation,
				populationSize)
						.setSelectionOperator(selection)
						.setMaxEvaluations(maxEvals)
						.setSolutionListEvaluator(evaluator);

		algorithm = builder.build();
		
		//Start algorithm in its own thread
		Thread thread = new Thread(algorithm);
		thread.start();
		try {
			thread.join();
		} catch (InterruptedException e) {
			throw new JMetalException("Error in thread.join()", e);
		}
		
		//close algorithm
		builder.getSolutionListEvaluator().shutdown();
		evaluator.shutdown();
		
		//output results
		List<DoubleSolution> population = algorithm.getResult();
		printFinalSolutionSet(population);
	}
}