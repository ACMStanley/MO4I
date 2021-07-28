package uk.ac.ncl.astanley.mo4i.problem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import uk.ac.ncl.astanley.mo4i.util.DirectorySettings;

/*
Author: Aiden Stanley
Purpose: An class to represent an objective in a MO4I Multi-Objective optimisation problem.
			In a parameter objective, the value of a variable in the multi-model's state after 
			a simulation is considered the objective value.
*/

public class ParameterObjective extends Objective{

	private String paramName;
	
	public ParameterObjective(String paramName, boolean maximise) {
		super(maximise);
		this.paramName = paramName;
	}

	@Override
	public double evaluate(String resultsPath, String resultsId){
		String outputPath = "/" + Integer.toString((int) Thread.currentThread().getId()) + ".csv";
		Scanner s;
		try {
			s = new Scanner(new File(new File(DirectorySettings.oneShotResultsPath).getAbsolutePath() + outputPath));
		} catch (FileNotFoundException e) {
			throw new UncheckedIOException(e);
		}
		
		String headerLine =  s.nextLine();
		String[] headers = headerLine.split(",");
		List<String> headersAsList = Arrays.asList(headers);
		
		int index = headersAsList.indexOf(paramName);
		
		String lastLine = "";
		while(s.hasNext()) {
			lastLine = s.nextLine();
		}
		String[] results = lastLine.split(",");
		
		double out = Double.parseDouble(results[index]);
		
		return out;
	}

}
