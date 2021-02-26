package algorithms;
import problem.INTOCPSProblem;
import util.FrontAdjuster;
import util.ReverseCoupler;

public abstract class MO4IAlgorithm {
	
	INTOCPSProblem problem;
	
	public abstract void runAlgorithm();
	
	public final void run() {
		runAlgorithm();
		FrontAdjuster.flipFront(ReverseCoupler.getMinMax());
	}
}
