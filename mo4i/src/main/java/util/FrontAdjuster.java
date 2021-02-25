package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FrontAdjuster {
	public static double[][] flipFront(boolean[] maximiseObjective) {
		File FUNFile = new File(DirectorySettings.getFrontPath());
		Scanner FUNScanner = null;
		List<List<Double>> points = new ArrayList<List<Double>>();
		try {
			FUNScanner = new Scanner(FUNFile);
		} catch (FileNotFoundException e) {
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
			e.printStackTrace();
		}
		
		double[][] front = ListsToMatrix.listsToMatrix(points);
		
		for(double[] d : front) {
			d[0] = (maximiseObjective[0] ? -1 : 1)* d[0];
			double firstValue = d[0];
			String lineToWrite = Double.toString(firstValue);
			for(int i = 1; i < d.length; i++) {
				d[i] = (maximiseObjective[0] ? -1 : 1)* d[i];
				lineToWrite = new String(lineToWrite + "," + d[i]);
			}
			writer.println(lineToWrite);
		}
		writer.close();
		return front;
	}
}
