package uk.ac.ncl.astanley.mo4i.algorithms;

import java.util.List;

import org.uma.jmetal.algorithm.multiobjective.moead.AbstractMOEAD;
import org.uma.jmetal.algorithm.multiobjective.moead.MOEADBuilder;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.operator.crossover.impl.DifferentialEvolutionCrossover;
import org.uma.jmetal.operator.crossover.impl.SBXCrossover;
import org.uma.jmetal.operator.mutation.impl.PolynomialMutation;
import org.uma.jmetal.operator.selection.impl.BinaryTournamentSelection;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.comparator.RankingAndCrowdingDistanceComparator;

public class MOEAD extends MO4IAlgorithm{
	public void runAlgorithm() throws JMetalException{
		
		double cr = 1.0;
	    double f = 0.5;
	    
	    populationSize = 300;
		
		crossover = new DifferentialEvolutionCrossover(cr, f, DifferentialEvolutionCrossover.DE_VARIANT.RAND_1_BIN);
	    mutation = new PolynomialMutation(mutationProbability, mutationDistributionIndex);
	    selection = new BinaryTournamentSelection<DoubleSolution>(new RankingAndCrowdingDistanceComparator<DoubleSolution>());
	    
		    
	    algorithm = new NSGAIIBuilder<DoubleSolution>(problem, crossover, mutation, populationSize)
	            .setSelectionOperator(selection)
	            .setMaxEvaluations(25000)
	            .build();
    
	    algorithm =
	            new MOEADBuilder(problem, MOEADBuilder.Variant.MOEAD)
	                .setCrossover(crossover)
	                .setMutation(mutation)
	                .setMaxEvaluations(150000)
	                .setPopulationSize(populationSize)
	                .setResultPopulationSize(100)
	                .setNeighborhoodSelectionProbability(0.9)
	                .setMaximumNumberOfReplacedSolutions(2)
	                .setNeighborSize(20)
	                .setFunctionType(AbstractMOEAD.FunctionType.TCHE)
	                .build();

	    List<DoubleSolution> population = algorithm.getResult();
	
	    printFinalSolutionSet(population);
    }
}
