package mo4i.main;

import java.util.ArrayList;
import java.util.List;

import algorithms.AllAlgorithms;
import algorithms.MO4IAlgorithm;
import util.EnumToAlgorithm;

public class RunHandler {
	private AllAlgorithms algorithm;
	
	public RunHandler setAlgorithm(AllAlgorithms algorithm) {
		this.algorithm = algorithm;
		return this;
	}
	
	public void run() {
		EnumToAlgorithm.enumToAlgorithm(algorithm).run();
	}
	
}
