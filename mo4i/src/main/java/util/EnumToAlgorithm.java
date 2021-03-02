package util;

import algorithms.AllAlgorithms;
import algorithms.IBEA;
import algorithms.ABYSS;
import algorithms.MO4IAlgorithm;
import algorithms.MOCell;
import algorithms.MOEAD;
import algorithms.NSGAII;
import algorithms.SMPSO;
import algorithms.SPEA2;

public class EnumToAlgorithm {
	public static MO4IAlgorithm enumToAlgorithm(AllAlgorithms algorithm) {
		switch(algorithm) {
			case NSGAII:
				return new NSGAII();
			case SPEA2:
					return new SPEA2();
			case SMPSO:
				return new SMPSO();
			case MOEAD_WIP:
				return new MOEAD();
			case MOCell:
				return new MOCell();
			case ABYSS_WIP:
				return new ABYSS();
			case IBEA:
				return new IBEA();
			default:
				System.out.println("Could not resolve enum to an algorithm. Defaulting to NSGAII.");
				return new NSGAII();
		}
	}
}
