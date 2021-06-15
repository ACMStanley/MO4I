package uk.ac.ncl.astanley.mo4i.problem;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import uk.ac.ncl.astanley.mo4i.util.DirectorySettings;

public class ScriptObjective extends Objective {
	private String scriptPath;

	public ScriptObjective(String scriptPath, boolean maximise) {
		super(maximise);
		this.scriptPath = scriptPath;
	}

	@Override
	public double evaluate(String resultsPath,String threadId){
		Runtime r = Runtime.getRuntime();
		
		Process p;
		try {
			p = r.exec(new String[] { "python", new File(scriptPath).getName(),

					new File(resultsPath).getAbsolutePath().toString(),						//argv[1]
					new File("../temp/").getAbsolutePath().toString(), 						//argv[2]
				threadId																	//argv[3]

			}, null, new File(scriptPath).getParentFile());

			p.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Scanner s = null;
		try {
			s = new Scanner(new File(DirectorySettings.tempDirectoryPath + "/" +threadId + ".txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String out =s.next().trim();
		s.close();
		return Double.parseDouble(out);
		
	}
}
