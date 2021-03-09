package mo4i.main;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.uma.jmetal.util.JMetalException;

import Actions.Action;
import UI.UIHandler;
import UI.UIUtils;
import algorithms.AllAlgorithms;
import problem.ProblemSettings;
import util.DirectorySettings;

public class Client {
	private static boolean active;
	private static RunHandler runHandler;
	private static ProblemSettings problemSettings;
	

	public static ProblemSettings getProblemSettings() {
		return problemSettings;
	}

	public static void main(String[] args) throws JMetalException, FileNotFoundException {
		
		active = true;
		runHandler = new RunHandler();
		problemSettings = new ProblemSettings();
		
		runHandler.setAlgorithm(AllAlgorithms.NSGAII);
		
		List<Double> lowerLimits = new ArrayList<Double>();
		List<Double> upperLimits = new ArrayList<Double>();
		
		lowerLimits.add(0.0);
		lowerLimits.add(0.0);
		lowerLimits.add(0.0);
		
		upperLimits.add(1.0);
		upperLimits.add(1.0);
		upperLimits.add(1.0);
		
		problemSettings.setNumberOfVars(3)
						.setNumberOfObjectives(2)
						.setLowerVarLimits(lowerLimits)
						.setUpperVarLimits(upperLimits)
						.setToMaximiseObjective(0, true)
						.setToMaximiseObjective(1, true);
		
		
		printHeader();
		UIHandler ui = new UIHandler();
		try {
			while(active) {
				Action a = ui.getAction();
				a.execute();
			}
		} catch(Exception e) {
			
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
	
	public static void quit() {
		active = false;
	}
	
	private static void makeOutputDirectory() {
		try {

		    Path path = Paths.get(DirectorySettings.dataOutPath);

		    //java.nio.file.Files;
		    Files.createDirectories(path);

		    System.out.println("Directory is created!");

		  } catch (IOException e) {

		    System.err.println("Failed to create directory!" + e.getMessage());

		  }
	}
}
