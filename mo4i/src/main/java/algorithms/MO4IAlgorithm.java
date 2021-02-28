package algorithms;
import problem.INTOCPSProblem;
import problem.ProblemSettings;
import util.FrontAdjuster;

public abstract class MO4IAlgorithm {
	
	protected abstract void runAlgorithm(INTOCPSProblem problem);
	
	public final void run(INTOCPSProblem problem) {
		runAlgorithm(problem);
		FrontAdjuster.flipFront(ProblemSettings.getMinOrMax());
	}
}
