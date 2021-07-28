package uk.ac.ncl.astanley.mo4i.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
Author: Aiden Stanley
Purpose: Static utility class that provides the functionality of 'flipping' the Pareto front in the output file 'FUN.csv'.
			Flipping the Pareto front after processing is a shortcut to switching between maximise and minimise optimisation
*/

public class FrontAdjuster {
	public static double[][] flipFront(List<Boolean> maximiseObjective) {
		File FUNFile = new File(DirectorySettings.getFrontPath());
		Scanner FUNScanner = null;
		List<List<Double>> points = new ArrayList<List<Double>>();
		try {
			FUNScanner = new Scanner(FUNFile);
		} catch (FileNotFoundException e) {
			System.out.println("Could not read output to flip pareto front as the output file is missing!");
			e.printStackTrace();
		}
		
		while(FUNScanner.hasNextLine()) {
			Scanner s = new Scanner(FUNScanner.nextLine());
			s.useDelimiter(",");
			List<Double> point = new ArrayList<>();
			while(s.hasNext()) {
				point.add(s.nextDouble());
			}
			points.add(point);
		}
		
		FUNFile.delete();
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(FUNFile);
		} catch (FileNotFoundException e) {
			System.out.println("Problem occured when trying to write flipped Pareto front!");
			e.printStackTrace();
		}
		
		double[][] front = ListsToMatrix.listsToMatrix(points);
		
		for(double[] d : front) {
			d[0] = (maximiseObjective.get(0) ? -1 : 1)* d[0];
			double firstValue = d[0];
			String lineToWrite = Double.toString(firstValue);
			for(int i = 1; i < d.length; i++) {
				d[i] = (maximiseObjective.get(i) ? -1 : 1)* d[i];
				lineToWrite = new String(lineToWrite + "," + d[i]);
			}
			writer.println(lineToWrite);
		}
		writer.close();
		return front;
	}
}
