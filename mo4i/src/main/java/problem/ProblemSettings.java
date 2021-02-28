package problem;

import java.util.List;

public class ProblemSettings {
	private int numberOfVars;
	private int numberOfObjectives;
	private List<Double> lowerVarLimits;
	private List<Double> upperVarLimits;
	private boolean[] maximiseObjective;
	
	public int getNumberOfVars() {
		return numberOfVars;
	}
	public ProblemSettings setNumberOfVars(int numberOfVars) {
		this.numberOfVars = numberOfVars;
		return this;
	}
	public int getNumberOfObjectives() {
		return numberOfObjectives;
	}
	public ProblemSettings setNumberOfObjectives(int numberOfObjectives) {
		maximiseObjective = new boolean[numberOfObjectives];
		this.numberOfObjectives = numberOfObjectives;
		return this;
	}
	public List<Double> getLowerVarLimits() {
		return lowerVarLimits;
	}
	public ProblemSettings setLowerVarLimits(List<Double> lowerVarLimits) {
		this.lowerVarLimits = lowerVarLimits;
		return this;
	}
	public List<Double> getUpperVarLimits() {
		return upperVarLimits;
	}
	public ProblemSettings setUpperVarLimits(List<Double> upperVarLimits) {
		this.upperVarLimits = upperVarLimits;
		return this;
	}
	
	public ProblemSettings setToMaximiseObjective(int index, boolean maximise){
		if(index >= getNumberOfObjectives() || index < 0) {
			throw new NullPointerException("Index is out of bounds!");
		}
		maximiseObjective[index] = maximise;
		return this;
	}
	
	public boolean[] getMinMax(){
		return maximiseObjective;
	}
	
}
