package algorithms;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.operator.crossover.CrossoverOperator;
import org.uma.jmetal.operator.crossover.impl.SBXCrossover;
import org.uma.jmetal.operator.mutation.MutationOperator;
import org.uma.jmetal.operator.mutation.impl.PolynomialMutation;
import org.uma.jmetal.operator.selection.impl.BinaryTournamentSelection;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;
import problem.MO4IProblem;
import util.DirectorySettings;
import java.util.List;


public class NSGAII extends MO4IAlgorithm{
	
    public void runAlgorithm() throws JMetalException{
	    crossover = new SBXCrossover(crossoverProbability, crossoverDistributionIndex);
	    mutation = new PolynomialMutation(mutationProbability, mutationDistributionIndex);
	    selection = new BinaryTournamentSelection<DoubleSolution>(new RankingAndCrowdingDistanceComparator<DoubleSolution>());
	    
		    
	    algorithm = new NSGAIIBuilder<DoubleSolution>(problem, crossover, mutation, populationSize)
	            .setSelectionOperator(selection)
	            .setMaxEvaluations(25000)
	            .build();
    
	    algorithm.run();

	    List<DoubleSolution> population = algorithm.getResult();
	
	    printFinalSolutionSet(population);
  }
  
  
}