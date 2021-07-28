package uk.ac.ncl.astanley.mo4i.problem;

import java.util.Random;

/*
Author: Aiden Stanley
Purpose: An abstract class to represent an objective in a MO4I Multi-Objective optimisation problem
*/

public abstract class Objective {
	private boolean maximise;
	private String id;
	
	public Objective(boolean maximise) {
		this.maximise = maximise;
		id = generateHexId();
	}
	
	public String getId() {
		return id;
	}

	public boolean isMaximise() {
		return maximise;
	}
	
	public void setToMaximise(boolean maximise) {
		this.maximise = maximise;
	}
	
	public abstract double evaluate(String resultsPath,String threadId);
	
	private static String generateHexId(){
		int length = 20;
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        while(sb.length() < length){
            sb.append(Integer.toHexString(r.nextInt()));
        }
        return sb.toString().substring(0, length);
    }
}
