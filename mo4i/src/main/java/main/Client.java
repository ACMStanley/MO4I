package main;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import uk.ac.ncl.astanley.mo4i.algorithms.AlgorithmVariant;
import uk.ac.ncl.astanley.mo4i.link.Link;
import uk.ac.ncl.astanley.mo4i.problem.Objective;
import uk.ac.ncl.astanley.mo4i.problem.ParameterObjective;
import uk.ac.ncl.astanley.mo4i.problem.ProblemSettings;
import uk.ac.ncl.astanley.mo4i.problem.ScriptObjective;

public class Client{
	private static RunHandler runHandler;
	private static ProblemSettings problemSettings;
	private static Link link;
	private static JSONParser parser;
	private static String COEPath;
	public static FileWriter outputRun;
	
	public static ProblemSettings getProblemSettings() {
		return problemSettings;
	}
	
	public static String getCOEPath() {
		return COEPath;
	}

	public static void main(String[] args){
		
		parser = new JSONParser();
		runHandler = new RunHandler();
		try {
			outputRun = new FileWriter("AllSolution.csv");
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
		
		if(args.length == 1) {
			parseRunSettings(args[0]);
			runHandler.run();
		}
		else {
			System.out.println("Usage: mo4i {JSON_PATH}");
		}
		
		try {
			outputRun.close();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	public static RunHandler getRunHandler() {
		return runHandler;
	}
	
	private static void parseRunSettings(String path) {
		List<Double> lowerLimits = new ArrayList<Double>();
		List<Double> upperLimits = new ArrayList<Double>();
		JSONObject runSettings = null;
		
		try {
			runSettings = (JSONObject) parser.parse(new FileReader(new File(path)));
		} catch (IOException | ParseException e) {
			System.out.println("mo4i.json not found");
			System.exit(1);
		}
		
		COEPath = (String) runSettings.get("coe-path");
		link = new Link(COEPath, (String) runSettings.get("mm-json-path"));
		problemSettings = new ProblemSettings(link);
		
		runHandler.setAlgorithm(AlgorithmVariant.valueOf((String) runSettings.get("algorithm")));
		
		JSONArray vars = (JSONArray) runSettings.get("vars");
		for(Object object:vars) {
			JSONObject var = (JSONObject) object;
			problemSettings.addVar((String) var.get("var"));
			lowerLimits.add(((Number) var.get("lower-limit")).doubleValue());
			upperLimits.add(((Number) var.get("upper-limit")).doubleValue());
		}
		
		problemSettings.setLowerVarLimits(lowerLimits);
		problemSettings.setUpperVarLimits(upperLimits);
		
		JSONArray objectives = (JSONArray) runSettings.get("objectives");
		for(int i = 0; i < objectives.size(); i++) {
			JSONObject objective = (JSONObject) objectives.get(i);
			
			Objective out;
			
			if((((String) objective.get("type")).toUpperCase()).equals("PARAMETER")) {
				out = new ParameterObjective((String) objective.get("objective"),(boolean) objective.get("maximise"));
			}
			else if((((String) objective.get("type")).toUpperCase()).equals("SCRIPT")) {
				out = new ScriptObjective((String) objective.get("objective"),(boolean) objective.get("maximise"));
			}
			else {
				throw new IllegalArgumentException("\"" + (String) objective.get("type") + "\" is not a valid objective type");
			}
			
			problemSettings.addObjective(out);
		}
		
		problemSettings.setThreadCount(((Number) runSettings.get("threads")).intValue());
		problemSettings.setSimTime(((Number) runSettings.get("sim-length")).doubleValue());
		problemSettings.setMaxEvals(((Number) runSettings.get("maxEvals")).intValue());
	}
}
