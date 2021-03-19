package problem;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;

import link.Link;

public class ProblemSettings {
	private Link link;
	private List<Double> lowerVarLimits;
	private List<Double> upperVarLimits;
	private List<String> vars;
	private List<String> objectives;
	private double simTime;
	private List<Boolean> maximiseObjective;
	LinkedHashMap<String, Double> inputParams;
	
	public ProblemSettings(Link link) {
		this.link = link;
		vars = new ArrayList<String>();
		objectives = new ArrayList<String>();
		maximiseObjective = new LinkedList<Boolean>();
		simTime = 60;
		inputParams = new LinkedHashMap<String,Double>();
	}
	
	public void setSimTime(double time) {
		if(time <= 0) {
			throw new IllegalArgumentException("Simulation time must be greater than 0!");
		}
		simTime = time;
	}
	
	public int getNumberOfVars() {
		return vars.size();
	}
	
	public ProblemSettings addVar(String paramName) {
		if(!link.checkParamExists(paramName)) {
			throw new IllegalArgumentException("Parameter '" + paramName + "' is not recognised for the current multi-model!");
		}
		vars.add(paramName);
		return this;
	}
	
	public ProblemSettings removeVar(String paramName) {
		vars.remove(paramName);
		return this;
	}
	
	public String getVar(int i) {
		if(i < 0 || i > (vars.size() - 1)) {
			throw new IllegalArgumentException("index was out of bounds fro problem variables!");
		}
		
		return vars.get(i);
	}
	
	/*
	public ProblemSettings setNumberOfVars(int numberOfVars) {
		this.numberOfVars = numberOfVars;
		return this;
	}
	*/
	
	public int getNumberOfObjectives() {
		return objectives.size();
	}
	
	public ProblemSettings addObjective(String paramName) {
		objectives.add(paramName);
		maximiseObjective.add(false);
		return this;
	}
	
	public ProblemSettings removeObjective(String paramName) {
		int index = objectives.indexOf(paramName);
		objectives.remove(paramName);
		if(index != -1) {
			maximiseObjective.remove(index);
		}
		return this;
	}
	
	/*public ProblemSettings setNumberOfObjectives(int numberOfObjectives) {
		maximiseObjective = new boolean[numberOfObjectives];
		return this;
	}*/
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
		maximiseObjective.set(index, maximise);
		return this;
	}
	
	public List<Boolean> getMinMax(){
		return maximiseObjective;
	}
	
	public double[] calculateObjective(List<Double> values){
		if(values.size() != getNumberOfVars()) {
			throw new IllegalArgumentException("List of values must be same size as number fo variables!");
		}
		
		for(int i = 0; i < getNumberOfVars(); i++) {
			inputParams.put(vars.get(i),values.get(i));
		}
		
		return link.getSimResults(simTime,inputParams, objectives);
	}
	
}
