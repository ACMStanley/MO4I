package mo4i.main;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import algorithms.AlgorithmVariant;
import link.Link;
import problem.ProblemSettings;


public class Client{
	private static RunHandler runHandler;
	private static ProblemSettings problemSettings;
	private static Link link;
	static JSONParser parser;
	

	public static ProblemSettings getProblemSettings() {
		return problemSettings;
	}

	public static void main(String[] args){
		
		parser = new JSONParser();
		runHandler = new RunHandler();
		
		if(args.length == 1) {
			parseRunSettings(args[0]);
		}
		else {
			System.out.println("Usage: mo4i {mo4irun.json_PATH}");
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
		
		link = new Link((String) runSettings.get("coe-path"), (String) runSettings.get("mm-json-path"));
		problemSettings = new ProblemSettings(link);
		
		runHandler.setAlgorithm(AlgorithmVariant.valueOf((String) runSettings.get("algorithm")));
		
		JSONArray vars = (JSONArray) runSettings.get("vars");
		for(Object object:vars) {
			JSONObject var = (JSONObject) object;
			
			problemSettings.addVar((String) var.get("var"));
			lowerLimits.add(((Number) var.get("lower-limit")).doubleValue());
			upperLimits.add(((Number) var.get("upper-limit")).doubleValue());
		}
		
		JSONArray objectives = (JSONArray) runSettings.get("objectives");
		for(int i = 0; i < objectives.size(); i++) {
			JSONObject objective = (JSONObject) objectives.get(i);
			
			problemSettings.addObjective((String) objective.get("objective"));
			problemSettings.setToMaximiseObjective(i, (boolean) objective.get("maximise"));
		}
		
		problemSettings.setSimTime(((Number) runSettings.get("sim-length")).doubleValue());
	}
}
