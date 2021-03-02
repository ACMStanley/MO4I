package algorithms;

import java.util.List;

import org.uma.jmetal.algorithm.multiobjective.ibea.IBEABuilder;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.operator.crossover.impl.SBXCrossover;
import org.uma.jmetal.operator.mutation.impl.PolynomialMutation;
import org.uma.jmetal.operator.selection.impl.BinaryTournamentSelection;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;
import org.uma.jmetal.util.JMetalException;

public class IBEA extends MO4IAlgorithm{
	public void runAlgorithm() throws JMetalException{
	    crossover = new SBXCrossover(crossoverProbability, crossoverDistributionIndex);
	    mutation = new PolynomialMutation(mutationProbability, mutationDistributionIndex);
	    selection = new BinaryTournamentSelection<DoubleSolution>();
	    
		    
	    algorithm = new IBEABuilder(problem)
	    	      .setArchiveSize(100)
	    	      .setPopulationSize(populationSize)
	    	      .setMaxEvaluations(25000)
	    	      .setCrossover(crossover)
	    	      .setMutation(mutation)
	    	      .setSelection(selection)
	    	      .build() ;
    
	    algorithm.run();

	    List<DoubleSolution> population = algorithm.getResult();
	
	    printFinalSolutionSet(population);
    }
}
