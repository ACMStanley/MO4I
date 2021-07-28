package uk.ac.ncl.astanley.mo4i.util;

import java.util.List;
/*
Author: Aiden Stanley
Purpose: Static utility class that provides a single method that converts a list of lists of doubles into a 2D array of doubles
*/
public class ListsToMatrix {

	public static double[][] listsToMatrix(List<List<Double>> lists){
		double[][] matrix = new double[lists.size()][];
		
		for(int i = 0; i < lists.size(); i++) {
			List<Double> list = lists.get(i);
			matrix[i] = new double[list.size()];
			for(int j = 0; j < list.size(); j++) {
				matrix[i][j] = list.get(j);
			}
		}
		
		return matrix;
	}
}
