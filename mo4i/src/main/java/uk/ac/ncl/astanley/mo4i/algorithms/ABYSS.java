package uk.ac.ncl.astanley.mo4i.algorithms;

import java.util.List;

import org.uma.jmetal.algorithm.multiobjective.abyss.ABYSSBuilder;
import org.uma.jmetal.solution.doublesolution.DoubleSolution;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.archive.Archive;
import org.uma.jmetal.util.archive.impl.CrowdingDistanceArchive;

public class ABYSS extends MO4IAlgorithm{
	
	public void runAlgorithm() throws JMetalException{
		archive = new CrowdingDistanceArchive<DoubleSolution>(100);
		    
	    algorithm = new ABYSSBuilder(problem, archive)
		        .setMaxEvaluations(25000)
		        .build();
    
	    algorithm.run();

	    List<DoubleSolution> population = algorithm.getResult();
	
	    printFinalSolutionSet(population);
    }
	
}
