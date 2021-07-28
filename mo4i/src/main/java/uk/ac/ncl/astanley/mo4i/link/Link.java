package uk.ac.ncl.astanley.mo4i.link;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import uk.ac.ncl.astanley.mo4i.problem.Objective;
import uk.ac.ncl.astanley.mo4i.util.DirectorySettings;

/*
Author: Aiden Stanley
Purpose: A class to represent an object that acts as an interface between the mo4i application and the COE
*/

public class Link {
	private File COEDirectory;	//points to the folder where the COE is installed
	private File MMJsonInFile;	//points to the '.mm.json' file of the multi-model to be optimised
	private File MMJsonOutFile; //points to where the converted '.mm.json' file should be stored after conversion
	private JSONObject MMJson;	
	private JSONParser parser;
	private Runtime r;
	
	
	String runJSONLocation;		//the path where the start-up configuration file for MO4I can be found
	String resultsPath;			//the path where simulation results produced by the COE should be stored
	
	
	public Link(String COEPath,String MMPath) {
		r = Runtime.getRuntime();
		COEDirectory = new File(COEPath);												
		resultsPath = new File(DirectorySettings.oneShotResultsPath).getAbsolutePath();
		MMJsonInFile = new File(MMPath);
		reformatMMJSON();
	}
	
	public void setCOEPath(String path) {
		COEDirectory = new File(path);
	}
	
	//sets where the COE should output simulation results
	public void setResultsPath(String path) {
		resultsPath = new File(path).getAbsolutePath();
	}
	
	//sets the path that points towards the multi-model to be optimised.
	//The path should be that of the multi-model's ".mm.json" file
	public void setMMPath(String path) {
		MMJsonInFile = new File(path);
		reformatMMJSON();
	}
	
	@SuppressWarnings("unchecked")
	//Set the value of a parameter, 'param', in the multi-model's descriptive JSON
	public void setParameter(String param, double value) {
		JSONObject threadCopy = new JSONObject(MMJson);
		((JSONObject) threadCopy.get("parameters")).put(param,value);
		FileWriter f;
		try {
			f = new FileWriter(new File(runJSONLocation.replace(".json", Thread.currentThread().getId() + ".json")));
            f.write(threadCopy.toJSONString());
            f.close();
        } catch (IOException e) {
        	throw new UncheckedIOException(e);
        }
	}
	
	//returns true if the multi-model's descriptive JSON contains the parameter 'param' 
	public boolean checkParamExists(String param) {
		return ((JSONObject) MMJson.get("parameters")).get(param) != null;
	}
	
	@SuppressWarnings("unchecked")
	//Converts the format of the multi-model's JSON file to be compatible with the
	//COE's 'oneshot' mode
	private void reformatMMJSON(){
		MMJsonOutFile = new File("../temp/mo4iMMjson.json");
		
		parser = new JSONParser();
		try {
			MMJson = (JSONObject) parser.parse(new FileReader(MMJsonInFile));
		} catch (IOException | ParseException e) {
			System.out.println("Problem occured when trying read/parse the Multi-Model's '.mm.json' file");
			e.printStackTrace();
		}
		
		//get the 'FMUs' entry from the JSON
		JSONObject fmuObject = (JSONObject) MMJson.get("fmus");
		
		Iterator<String> fmus = fmuObject.keySet().iterator();
		
		//locate the multi-model's FMUs in the file-system
		String fmuDir = MMJsonInFile.getParentFile().getParentFile().getParent() + "/FMUs";
		
		//iterate through each FMU name in the 'FMUs' section of the JSON 
		//and replace them with their corresponding URIs
		while(fmus.hasNext()) {
			String thisFMU = fmus.next();
			String path = (String) fmuObject.get(thisFMU);
			fmuObject.put(thisFMU, new File(fmuDir + "\\" + path).toURI().toString());
			path = (String) fmuObject.get(thisFMU);
		}
		
		//write the modified JSON object to file
		MMJson.put("fmus", fmuObject);
		FileWriter file;
		try {
			file = new FileWriter(MMJsonOutFile);
			file.write(MMJson.toJSONString());
	        file.close(); 
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Problem occured when trying to write Oneshot modified '.mm.json' file");
		}
		   
		runJSONLocation = new File(MMJsonOutFile.getPath()).getAbsolutePath().toString();
	}
	
	//runs a simulation of the multi-model using specified starting parameters for a specific amount of time.
	//returns an array that contains the results of the simulation with respect to a list of defined objectives 
	public double[] getSimResults(double timeLength,LinkedHashMap<String,Double> inputParams,List<Objective> objectives){
		
		//specify the output file's name to be the ID of this thread
		String threadId = Integer.toString((int) Thread.currentThread().getId());
		String resultsFilePath = resultsPath + "/" + threadId + ".csv";
		
		//set the starting parameter's of the multi-model to those specified
		for(String k : inputParams.keySet()) {
			setParameter(k,inputParams.get(k));
		}
		
		//run the simulation
		RunOneShotSim(timeLength,"../temp/" + threadId + ".csv",threadId);
		double[] out = new double[objectives.size()];
		
		//fetch the results
		for(int i = 0; i < objectives.size(); i++) {
			out[i] = objectives.get(i).evaluate(resultsFilePath, threadId);
		}
		
		return out;
	}
	
	//runs single simulation of the multi-model for a specified time
	private void RunOneShotSim(double simTime, String resultsFilePath,String threadId) {
		Process p;
		boolean simCompleted = false;
		int timeoutCount = 0;
		
		String out = new File(resultsFilePath).getAbsoluteFile().toString();
		
		File COECopyDirectory = new File("../temp/" + threadId + "coe/");
		
		//check if a copy of the COE for this thread exists
		//if it doesn't, construct one.
		if(!COECopyDirectory.exists()) {
			setupCOECopy(threadId);
		}
		
		//loop until a simulation is completed successfully
		while(!simCompleted) {
			try {
				
				//start the COE in one shot mode as a subprocess
				p = r.exec(new String[] {"java", "-jar", "coe.jar", 
			
						"-o", 
						"-s", Double.toString(0), 
						"-e", Double.toString(simTime),
						"-c", runJSONLocation.replace(".json", Thread.currentThread().getId() + ".json"), 
						"-r", out},
				
						null,COECopyDirectory);
				
				//wait for the process to complete; Timeout after a arbitrary amount of time passes
				long timeout = (long) simTime * 2;
				p.waitFor(timeout,TimeUnit.SECONDS);
				
				//if the simulation process is still active after waiting, kill and restart the simulation
				if(p.isAlive()) {
					System.out.println("Simulation on thread " + Thread.currentThread().getId() + "not responding. Restarting");
					p.destroyForcibly();
					timeoutCount++;
					//if the simulation fails to complete after 3 restarts, assume something has gone wrong
					if(timeoutCount > 3) {
						throw new TimeoutException();
					}
				}
				else {
					simCompleted = true;
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (TimeoutException e) {
				System.out.println("Thread (id:" + Thread.currentThread().getId() + ") failed to respond " + timeoutCount + " times! Exiting.");
				e.printStackTrace();
				System.exit(-1);
			}
		}
	}
	
	//sets up a copy of the COE in a directory unique to the thread specified
	private void setupCOECopy(String threadId) {
		File dest = new File("../temp/" + threadId + "coe/");
		dest.getParentFile().mkdirs();
		File source = new File(COEDirectory + "/coe.jar");
		try {
			System.out.println("Building a COE directory for thread " + threadId);
		    FileUtils.copyFileToDirectory(source, dest);
		} catch (IOException e) {
		    e.printStackTrace();
		}

	}
}
