package uk.ac.ncl.astanley.mo4i.algorithms;

import java.util.List;

import org.uma.jmetal.algorithm.multiobjective.smpso.jmetal5version.SMPSOBuilder;
import org.uma.jmetal.operator.mutation.impl.PolynomialMutation;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.archive.BoundedArchive;
import org.uma.jmetal.util.archive.impl.CrowdingDistanceArchive;
import org.uma.jmetal.util.evaluator.impl.SequentialSolutionListEvaluator;

public class SMPSO extends MO4IAlgorithm{

	@Override
	public void runAlgorithm() throws JMetalException{
		archive = new CrowdingDistanceArchive<DoubleSolution>(100);
	    mutation = new PolynomialMutation(mutationProbability, mutationDistributionIndex);
	    
	    algorithm = new SMPSOBuilder(problem, (BoundedArchive<DoubleSolution>) archive)
	            .setMutation(mutation)
	            .setMaxIterations(maxEvals/populationSize)
	            .setSwarmSize(populationSize)
	            .setSolutionListEvaluator(evaluator)
	            .build();
    
	    algorithm.run();

	    List<DoubleSolution> population = algorithm.getResult();
	
	    printFinalSolutionSet(population);
    }
}
