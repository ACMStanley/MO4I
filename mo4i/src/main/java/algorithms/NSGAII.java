package algorithms;

import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.operator.crossover.impl.SBXCrossover;
import org.uma.jmetal.operator.mutation.impl.PolynomialMutation;
import org.uma.jmetal.operator.selection.impl.BinaryTournamentSelection;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;
import org.uma.jmetal.util.evaluator.impl.MultithreadedSolutionListEvaluator;

import Evaluators.MultiThreadEvaluator;

import java.util.List;


public class NSGAII extends MO4IAlgorithm{
	
    public void runAlgorithm() throws JMetalException{
	    crossover = new SBXCrossover(crossoverProbability, crossoverDistributionIndex);
	    mutation = new PolynomialMutation(mutationProbability, mutationDistributionIndex);
	    selection = new BinaryTournamentSelection<DoubleSolution>(new RankingAndCrowdingDistanceComparator<DoubleSolution>());
	    
		    
	    algorithm = new NSGAIIBuilder<DoubleSolution>(problem, crossover, mutation, populationSize)
	            .setSelectionOperator(selection)
	            .setMaxEvaluations(1000)
	            .setSolutionListEvaluator(new MultiThreadEvaluator<DoubleSolution>(8))
	            .build();
    
	    algorithm.run();

	    List<DoubleSolution> population = algorithm.getResult();
	
	    printFinalSolutionSet(population);
    }
}