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
	    
		
	    MultithreadedSolutionListEvaluator<DoubleSolution> evaluator = new MultithreadedSolutionListEvaluator<DoubleSolution>(8);
	    
	    int maxEvals = 3000;
	    
	    NSGAIIBuilder<DoubleSolution> builder = new NSGAIIBuilder<DoubleSolution>(problem, crossover, mutation, populationSize)
	            .setSelectionOperator(selection)
	            .setMaxEvaluations(maxEvals);
	            //.setSolutionListEvaluator(evaluator);
	    
	    algorithm = builder.build();
	    long startTime = System.currentTimeMillis();
	    
	    Thread thread = new Thread(algorithm) ;
	      thread.start();
	      try {
	        thread.join();
	      } catch (InterruptedException e) {
	        throw new JMetalException("Error in thread.join()", e) ;
	      }
	    
	    builder.getSolutionListEvaluator().shutdown();
	    evaluator.shutdown();
	    long endTime = System.currentTimeMillis();
	    System.out.println("Took " + (endTime - startTime) + "ms to complete " + maxEvals + " evaluations");
	    List<DoubleSolution> population = algorithm.getResult();
	
	    printFinalSolutionSet(population);
    }
}