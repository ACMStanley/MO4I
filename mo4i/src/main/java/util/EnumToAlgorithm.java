package util;

import algorithms.AlgorithmEnum;
import algorithms.MO4IAlgorithm;
import algorithms.NSGAII;

public class EnumToAlgorithm {
	public static MO4IAlgorithm enumToAlg(AlgorithmEnum e) {
		switch(e) {
			case NSGAII:
				return new NSGAII();
			default:
				return new NSGAII();
		}
	}
}
