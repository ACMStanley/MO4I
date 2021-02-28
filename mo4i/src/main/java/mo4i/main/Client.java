package mo4i.main;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.uma.jmetal.util.JMetalException;

import Actions.Action;
import UI.UIHandler;
import UI.UIUtils;
import algorithms.AllAlgorithms;
import problem.ProblemSettings;

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
		while(active) {
			Action a = ui.getAction();
			a.execute();
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
}
