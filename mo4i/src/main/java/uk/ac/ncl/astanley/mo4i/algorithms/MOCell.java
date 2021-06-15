package uk.ac.ncl.astanley.mo4i.algorithms;

import java.util.List;

import org.uma.jmetal.algorithm.multiobjective.mocell.MOCellBuilder;
import org.uma.jmetal.operator.crossover.impl.SBXCrossover;
import org.uma.jmetal.operator.mutation.impl.PolynomialMutation;
import org.uma.jmetal.operator.selection.impl.BinaryTournamentSelection;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.archive.impl.CrowdingDistanceArchive;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;

public class MOCell extends MO4IAlgorithm{
	public void runAlgorithm() throws JMetalException{
		
	    crossover = new SBXCrossover(crossoverProbability, crossoverDistributionIndex);
	    mutation = new PolynomialMutation(mutationProbability, mutationDistributionIndex);
	    selection = new BinaryTournamentSelection<DoubleSolution>(new RankingAndCrowdingDistanceComparator<DoubleSolution>());
	    
		    
	    algorithm = new MOCellBuilder<DoubleSolution>(problem, crossover, mutation)
	            .setSelectionOperator(selection)
	            .setMaxEvaluations(25000)
	            .setPopulationSize(populationSize)
	            .setArchive(new CrowdingDistanceArchive<DoubleSolution>(100))
	            .build();
    
	    algorithm.run();

	    List<DoubleSolution> population = algorithm.getResult();
	
	    printFinalSolutionSet(population);
    }
}
