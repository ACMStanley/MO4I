package problem;

public class ProblemSettings {
	private static boolean[] maximiseObjective;
	private static INTOCPSProblem problem;
	private static boolean problemSet = false;
	
	public static void setProblem(INTOCPSProblem p) {
		problem = p;
		problemSet = true;
		maximiseObjective = new boolean[problem.getNumberOfObjectives()];
	}
	
	public static void setToMaximiseObjective(int index, boolean maximise){
		if(!problemSet) {
			throw new NullPointerException("Problem is not set!");
		}
		if(index >= problem.getNumberOfObjectives() || index < 0) {
			throw new IllegalArgumentException("Index is out of bounds!");
		}
		maximiseObjective[index] = maximise;
	}
	
	public static boolean getMinOrMax(int index){
		if(index < 1 || index > maximiseObjective.length - 1) {
			throw new IllegalArgumentException();
		}
		return maximiseObjective[index];
	}
}
