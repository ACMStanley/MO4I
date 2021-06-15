package uk.ac.ncl.astanley.mo4i.util;

import uk.ac.ncl.astanley.mo4i.algorithms.ABYSS;
import uk.ac.ncl.astanley.mo4i.algorithms.AlgorithmVariant;
import uk.ac.ncl.astanley.mo4i.algorithms.IBEA;
import uk.ac.ncl.astanley.mo4i.algorithms.MO4IAlgorithm;
import uk.ac.ncl.astanley.mo4i.algorithms.MOCell;
import uk.ac.ncl.astanley.mo4i.algorithms.MOEAD;
import uk.ac.ncl.astanley.mo4i.algorithms.NSGAII;
import uk.ac.ncl.astanley.mo4i.algorithms.SMPSO;
import uk.ac.ncl.astanley.mo4i.algorithms.SPEA2;

public class EnumToAlgorithm {
	public static MO4IAlgorithm enumToAlgorithm(AlgorithmVariant algorithm) {
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
