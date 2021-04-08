package mo4i.main;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.uma.jmetal.util.JMetalException;

import Actions.Action;
import UI.UIHandler;
import UI.UIUtils;
import algorithms.AlgorithmVariant;
import link.Link;
import problem.ProblemSettings;
import util.ConfigWindow;
import util.DirectorySettings;
import util.SettingsWindow;

public class Client{
	private static boolean active;
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
		
		else if(args.length == 0){
			active = true;
		}
		
		else {
			
		}
	}
	
	public static RunHandler getRunHandler() {
		return runHandler;
	}

	public static void printHeader() {
		System.out.println("=======================================");
		UIUtils.printLogo();
		System.out.println("Multi-objective Optimisation 4 INTOCPS");
		System.out.println("\n=======================================");
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
	
	public static void quit() {
		active = false;
	}
}
