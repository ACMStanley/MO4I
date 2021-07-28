package uk.ac.ncl.astanley.mo4i.main;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ncl.astanley.mo4i.algorithms.AlgorithmVariant;
import uk.ac.ncl.astanley.mo4i.algorithms.MO4IAlgorithm;
import uk.ac.ncl.astanley.mo4i.util.EnumToAlgorithm;
/*
Author: Aiden Stanley
Purpose: Class that resolves an algorithm from and AlgorithmVariant enum, and then runs that algorithm
*/
public class RunHandler {
	private AlgorithmVariant algorithm;
	
	public RunHandler setAlgorithm(AlgorithmVariant algorithm) {
		this.algorithm = algorithm;
		return this;
	}
	
	public void run() {
		EnumToAlgorithm.enumToAlgorithm(algorithm).run();
	}
	
}
