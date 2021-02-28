package mo4i.main;

import org.uma.jmetal.lab.visualization.plot.PlotFront;
import org.uma.jmetal.lab.visualization.plot.impl.PlotSmile;

import algorithms.MO4IAlgorithm;
import algorithms.NSGAII;
import util.FrontAdjuster;
import util.ReverseCoupler;

public class MO4IAlgorithmRunner {
	
	String problemName = "mo4i.main.INTOCPSProblem";
	MO4IAlgorithm algorithm;
	
	public MO4IAlgorithmRunner(MO4IAlgorithm alg) {
		this.algorithm = alg;
	}
	
	public void run() {
	    INTOCPSProblem problem = new INTOCPSProblem();
	    
	    ReverseCoupler.setProblem(problem);
	    
	    try {
			ReverseCoupler.setToMaximiseObjective(1, true);
			ReverseCoupler.setToMaximiseObjective(0, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
	    algorithm.run();
	    //PlotFront plot = new PlotSmile(front);
	    //plot.plot();
	}
}
