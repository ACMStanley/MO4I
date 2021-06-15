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

public class Link {
	private File COEDirectory;
	private File MMJsonInFile;
	private File MMJsonOutFile;
	private JSONObject MMJson;
	private JSONParser parser;
	private Runtime r;
	
	
	String runJSONLocation;
	String resultsPath;
	
	
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
	
	public void setResultsPath(String path) {
		resultsPath = new File(path).getAbsolutePath();
	}
	
	public void setMMPath(String path) {
		MMJsonInFile = new File(path);
		reformatMMJSON();
	}
	
	@SuppressWarnings("unchecked")
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
	
	public boolean checkParamExists(String param) {
		return ((JSONObject) MMJson.get("parameters")).get(param) != null;
	}
	
	@SuppressWarnings("unchecked")
	private void reformatMMJSON() {
		MMJsonOutFile = new File("mo4iMMjson.json");
		parser = new JSONParser();
		try {
			MMJson = (JSONObject) parser.parse(new FileReader(MMJsonInFile));
		}catch(ParseException e) {
			e.printStackTrace();
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		JSONObject fmuObject = (JSONObject) MMJson.get("fmus");
		
		Iterator<String> fmus = fmuObject.keySet().iterator();
		
		String fmuDir = MMJsonInFile.getParentFile().getParentFile().getParent() + "/FMUs";

		while(fmus.hasNext()) {
			String thisFMU = fmus.next();
			String path = (String) fmuObject.get(thisFMU);
			fmuObject.put(thisFMU, new File(fmuDir + "\\" + path).toURI().toString());
			path = (String) fmuObject.get(thisFMU);
		}
		
		MMJson.put("fmus", fmuObject);

		try (FileWriter file = new FileWriter(MMJsonOutFile)) {
            file.write(MMJson.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		runJSONLocation = new File(MMJsonOutFile.getPath()).getAbsolutePath().toString();
	}
	
	public double[] getSimResults(double timeLength,LinkedHashMap<String,Double> inputParams,List<Objective> objectives){
		String threadId = Integer.toString((int) Thread.currentThread().getId());
		String resultsFilePath = resultsPath + "/" + threadId + ".csv";
		
		for(String k : inputParams.keySet()) {
			setParameter(k,inputParams.get(k));
		}
		RunOneShotSim(timeLength,"../temp/" + threadId + ".csv",threadId);
		double[] out = new double[objectives.size()];
		
		for(int i = 0; i < objectives.size(); i++) {
			out[i] = objectives.get(i).evaluate(resultsFilePath, threadId);
		}
		
		return out;
	}
	
	private void RunOneShotSim(double simTime, String resultsFilePath,String threadId) {
		Process p;
		boolean simCompleted = false;
		int timeoutCount = 0;
		String out = new File(resultsFilePath).getAbsoluteFile().toString();
		File COECopyDirectory = new File("../temp/" + threadId + "coe/");
		if(!COECopyDirectory.exists()) {
			setupCOECopy(threadId);
		}
		while(!simCompleted) {
			try {
				p = r.exec(new String[] {"C:\\Program Files\\Java\\jre1.8.0_281\\bin\\java", "-jar", "coe.jar", 
			
						"-o", 
						"-s", Double.toString(0), 
						"-e", Double.toString(simTime),
						"-c", runJSONLocation.replace(".json", Thread.currentThread().getId() + ".json"), 
						"-r", out},
				
						null,COECopyDirectory);
				
				long timeout = (long) simTime * 2;
				p.waitFor(timeout,TimeUnit.SECONDS);
				if(p.isAlive()) {
					System.out.println("Simulation on thread " + Thread.currentThread().getId() + "not responding. Restarting");
					p.destroyForcibly();
					timeoutCount++;
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
				System.exit(1);
			}
		}
	}
	
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
