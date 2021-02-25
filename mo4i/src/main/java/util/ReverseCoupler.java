package util;

import mo4i.main.INTOCPSProblem;

public class ReverseCoupler {
	public static boolean[] maximiseObjective;
	private static INTOCPSProblem problem;
	private static boolean problemSet = false;
	
	public static void setProblem(INTOCPSProblem p) {
		problem = p;
		problemSet = true;
		maximiseObjective = new boolean[problem.getNumberOfObjectives()];
	}
	
	public static void setToMaximiseObjective(int index, boolean maximise) throws Exception {
		if(!problemSet) {
			throw new Exception("Problem is not set!");
		}
		if(index >= problem.getNumberOfObjectives() || index < 0) {
			throw new IllegalArgumentException("Index is out of bounds!");
		}
		maximiseObjective[index] = maximise;
	}
	
	public static boolean[] getMinMax(){
		return maximiseObjective;
	}
}
