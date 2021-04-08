package mo4i.main;

import java.util.ArrayList;
import java.util.List;

import algorithms.AlgorithmVariant;
import algorithms.MO4IAlgorithm;
import util.EnumToAlgorithm;

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
