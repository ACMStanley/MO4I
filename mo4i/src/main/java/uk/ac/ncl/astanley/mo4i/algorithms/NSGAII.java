package uk.ac.ncl.astanley.mo4i.algorithms;

import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.operator.crossover.impl.SBXCrossover;
import org.uma.jmetal.operator.mutation.impl.PolynomialMutation;
import org.uma.jmetal.operator.selection.impl.BinaryTournamentSelection;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;

import java.util.List;


public class NSGAII extends MO4IAlgorithm{
	
    protected void runAlgorithm() throws JMetalException{
	    crossover = new SBXCrossover(crossoverProbability, crossoverDistributionIndex);
	    mutation = new PolynomialMutation(mutationProbability, mutationDistributionIndex);
	    selection = new BinaryTournamentSelection<DoubleSolution>(new RankingAndCrowdingDistanceComparator<DoubleSolution>());
	    
	    NSGAIIBuilder<DoubleSolution> builder = new NSGAIIBuilder<DoubleSolution>(problem, crossover, mutation, populationSize)
	            .setSelectionOperator(selection)
	            .setMaxEvaluations(maxEvals)
	            .setSolutionListEvaluator(evaluator);
	    
	    algorithm = builder.build();
	    
	    
	    Thread thread = new Thread(algorithm) ;
	      thread.start();
	      try {
	        thread.join();
	      } catch (InterruptedException e) {
	        throw new JMetalException("Error in thread.join()", e) ;
	      }
	    
	    builder.getSolutionListEvaluator().shutdown();
	    evaluator.shutdown();
	    
	    List<DoubleSolution> population = algorithm.getResult();
	
	    printFinalSolutionSet(population);
    }
}