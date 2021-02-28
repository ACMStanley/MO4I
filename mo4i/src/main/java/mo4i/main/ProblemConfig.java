package mo4i.main;

public class ProblemConfig {
	private static int noOfObjectives;
	private static int noOfVariables;
	
	protected static void setNoOfObjectives(int amt) {
		if(amt < 1) {
			throw new IllegalArgumentException("Amount of objectives must be greater than 0!");
		}
		noOfObjectives = amt;
	}
	
	protected static void setNoOfVariables(int amt) {
		if(amt < 1) {
			throw new IllegalArgumentException("Amount of variables must be greater than 0!");
		}
		noOfVariables = amt;
	}
	
	public static int getNoOfObjectives(){
		return noOfObjectives;
	}
	
	public static int getNoOfVariables(){
		return noOfVariables;
	}
}
