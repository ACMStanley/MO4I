package uk.ac.ncl.astanley.mo4i.problem;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import uk.ac.ncl.astanley.mo4i.link.Link;

public class ProblemSettings {
	private Link link;
	private List<Double> lowerVarLimits;
	private List<Double> upperVarLimits;
	private List<String> vars;
	private List<Objective> objectives;
	private double simTime;
	private int maxEvals;
	private int threads;
	
	public int getMaxEvals() {
		return maxEvals;
	}

	public void setMaxEvals(int maxEvals) {
		this.maxEvals = maxEvals;
	}
	
	public int getThreadCount() {
		return threads;
	}
	
	public void setThreadCount(int threadCount) {
		if(threadCount < 1) {
			throw new IllegalArgumentException("Thread count must be greater than 0!");
		}
		threads = threadCount;
	}

	LinkedHashMap<String, Double> inputParams;
	
	public ProblemSettings(Link link) {
		this.link = link;
		vars = new ArrayList<String>();
		objectives = new ArrayList<Objective>();
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
	
	public ProblemSettings addObjective(Objective o) {
		objectives.add(o);
		return this;
	}
	
	public ProblemSettings removeObjective(int index) {
		objectives.remove(index);
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
		objectives.get(index).setToMaximise(maximise);
		return this;
	}
	
	public List<Boolean> getMinMax(){
		List<Boolean> out = new ArrayList<Boolean>();
		for(Objective o : objectives) {
			out.add(o.isMaximise());
		}
		return out;
	}
	
	public double[] calculateObjective(List<Double> values){
		if(values.size() != getNumberOfVars()) {
			throw new IllegalArgumentException("List of values must be same size as number fo variables!");
		}
		
		for(int i = 0; i < getNumberOfVars(); i++) {
			inputParams.put(vars.get(i),values.get(i));
		}
		
		return link.getSimResults(simTime,inputParams,objectives);
	}
	
}
