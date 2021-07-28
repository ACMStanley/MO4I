package uk.ac.ncl.astanley.mo4i.problem;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import uk.ac.ncl.astanley.mo4i.util.DirectorySettings;

/*
Author: Aiden Stanley
Purpose: An class to represent an objective in a MO4I Multi-Objective optimisation problem.
			In a script objective, the multi-model's state data after a simulation 
			is passed to an external script defined by the user. This script should calculate
			an objective value from this data and store it in a location known to MO4I.
*/

public class ScriptObjective extends Objective {
	private String scriptPath;

	public ScriptObjective(String scriptPath, boolean maximise) {
		super(maximise);
		this.scriptPath = scriptPath;
	}

	@Override
	public double evaluate(String resultsPath,String threadId){
		Runtime r = Runtime.getRuntime();
		
		//execute the Python objective script
		Process p;
		try {
			p = r.exec(new String[] { "python", new File(scriptPath).getName(),

					new File(resultsPath).getAbsolutePath().toString(),						//argv[1]
					new File("../temp/").getAbsolutePath().toString(), 						//argv[2]
				threadId																	//argv[3]

			}, null, new File(scriptPath).getParentFile());
			p.waitFor();
		} catch (IOException e) {
			System.out.println("Problem occured when tryng to run objective script!");
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//read value generated from script
		Scanner s = null;
		try {
			s = new Scanner(new File(DirectorySettings.tempDirectoryPath + "/" +threadId + ".txt"));
		} catch (IOException e) {
			System.out.println("Problem occured when tryng to read objective script output");
			e.printStackTrace();
		}
		
		String out =s.next().trim();
		s.close();
		return Double.parseDouble(out);
		
	}
}
