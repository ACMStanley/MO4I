package uk.ac.ncl.astanley.mo4i.algorithms;

import java.util.List;

import org.uma.jmetal.algorithm.multiobjective.spea2.SPEA2Builder;
import org.uma.jmetal.operator.crossover.impl.SBXCrossover;
import org.uma.jmetal.operator.mutation.impl.PolynomialMutation;
import org.uma.jmetal.operator.selection.impl.BinaryTournamentSelection;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;

public class SPEA2 extends MO4IAlgorithm{

	@Override
	public void runAlgorithm() {
		crossover = new SBXCrossover(crossoverProbability, crossoverDistributionIndex);
	    mutation = new PolynomialMutation(mutationProbability, mutationDistributionIndex);
	    selection = new BinaryTournamentSelection<DoubleSolution>(new RankingAndCrowdingDistanceComparator<DoubleSolution>());
	    
		algorithm = new SPEA2Builder<>(problem, crossover, mutation)
	            .setSelectionOperator(selection)
	            .setMaxIterations(250)
	            .setPopulationSize(populationSize)
	            .setK(1)
	            .build();
		
		algorithm.run();
		
		List<DoubleSolution> population = algorithm.getResult();
		
	    printFinalSolutionSet(population);
	}
	
}
