package util;

import algorithms.AllAlgorithms;
import algorithms.MO4IAlgorithm;
import algorithms.NSGAII;
import algorithms.SPEA2;

public class EnumToAlgorithm {
	public static MO4IAlgorithm enumToAlgorithm(AllAlgorithms algorithm) {
		switch(algorithm) {
			case NSGAII:
				return new NSGAII();
			case SPEA2:
					return new SPEA2();
			default:
				System.out.println("Could not resolve enum to an algorithm. Defaulting to NSGAII.");
				return new NSGAII();
		}
	}
}
