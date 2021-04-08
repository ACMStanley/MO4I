package link;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import util.DirectorySettings;

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
		if(checkParamExists(param)) {
			((JSONObject) MMJson.get("parameters")).put(param,value);
			
			try (FileWriter file = new FileWriter(MMJsonOutFile)) {
	            file.write(MMJson.toJSONString());
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
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
	
	public double[] getSimResults(double timeLength,LinkedHashMap<String,Double> inputParams,List<String> outputParams){
		for(String k : inputParams.keySet()) {
			setParameter(k,inputParams.get(k));
		}
		RunOneShotSim(0,timeLength);
		return getParamResults(outputParams);
	}
	
	private void RunOneShotSim(double startTime, double endTime) {
		Process p;
		try {
			
			p = r.exec(new String[] {"java", "-jar", "coe.jar", 
		
					"-o", 
					"-s", Double.toString(startTime), 
					"-e", Double.toString(endTime),
					"-c", runJSONLocation, 
					"-r", resultsPath},
			
					null,COEDirectory);
			
			p.waitFor();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private double[] getParamResults(List<String> params){
		Scanner s;
		try {
			s = new Scanner(new File(resultsPath));
		} catch (FileNotFoundException e) {
			throw new UncheckedIOException(e);
		}
		
		
		double[] out = new double[params.size()];

		String headerLine =  s.nextLine();
		String[] headers = headerLine.split(",");
		List<String> headersAsList = Arrays.asList(headers);
		
		List<Integer> indexes = new ArrayList<Integer>();
		for(String p : params) {
			indexes.add(headersAsList.indexOf(p));
		}
		
		String lastLine = "";
		while(s.hasNext()) {
			lastLine = s.nextLine();
		}
		String[] results = lastLine.split(",");
		
		for(int i = 0; i < indexes.size(); i++) {
			out[i] = Double.parseDouble(results[indexes.get(i)]);
		}
		
		return out;
	}
}
