package algorithms;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.operator.crossover.CrossoverOperator;
import org.uma.jmetal.operator.crossover.impl.SBXCrossover;
import org.uma.jmetal.operator.mutation.MutationOperator;
import org.uma.jmetal.operator.mutation.impl.PolynomialMutation;
import org.uma.jmetal.operator.selection.SelectionOperator;
import org.uma.jmetal.operator.selection.impl.BinaryTournamentSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;
import org.uma.jmetal.util.fileoutput.SolutionListOutput;
import org.uma.jmetal.util.fileoutput.impl.DefaultFileOutputContext;

import problem.INTOCPSProblem;
import util.DirectorySettings;
import java.util.List;


public class NSGAII extends MO4IAlgorithm{
    Algorithm<List<DoubleSolution>> algorithm;
    CrossoverOperator<DoubleSolution> crossover;
    MutationOperator<DoubleSolution> mutation;
    SelectionOperator<List<DoubleSolution>, DoubleSolution> selection;
    
    double crossoverProbability;
    double crossoverDistributionIndex;
    
    double mutationProbability;
    double mutationDistributionIndex;
    
    int populationSize;
    
	
    public void runAlgorithm(INTOCPSProblem problem) throws JMetalException{
	  
	  	crossoverProbability = 0.9;
		crossoverDistributionIndex = 20.0;
	    crossover = new SBXCrossover(crossoverProbability, crossoverDistributionIndex);
		  
	    mutationProbability = 1.0 / problem.getNumberOfVariables();
	    mutationDistributionIndex = 20.0;
	    mutation = new PolynomialMutation(mutationProbability, mutationDistributionIndex);
	    
	    selection = new BinaryTournamentSelection<DoubleSolution>(new RankingAndCrowdingDistanceComparator<DoubleSolution>());
	    
	    populationSize = 100;
	    
	    algorithm = new NSGAIIBuilder<>(problem, crossover, mutation, populationSize)
            .setSelectionOperator(selection)
            .setMaxEvaluations(25000)
            .build();
    
    Thread thread = new Thread(algorithm) ;
    thread.start();
    try {
      thread.join();
    } catch (InterruptedException e) {
      throw new JMetalException("Error in thread.join()", e) ;
    }

    List<DoubleSolution> population = algorithm.getResult();

    printFinalSolutionSet(population);
  }
  
  public void printFinalSolutionSet(List<? extends Solution<?>> population) {

	    new SolutionListOutput(population)
	        .setVarFileOutputContext(new DefaultFileOutputContext(DirectorySettings.getVariablesPath(), ","))
	        .setFunFileOutputContext(new DefaultFileOutputContext(DirectorySettings.getFrontPath(), ","))
	        .print();
	  }
}