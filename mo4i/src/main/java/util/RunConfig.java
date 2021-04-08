package util;

import algorithms.AlgorithmVariant;

public class RunConfig {
	private String MMJsonPath;
	private AlgorithmVariant algorithm;
	
	public String getMMJsonPath() {
		return MMJsonPath;
	}
	public void setMMJsonPath(String mMJsonPath) {
		MMJsonPath = mMJsonPath;
	}
	public AlgorithmVariant getAlgorithm() {
		return algorithm;
	}
	public void setAlgorithm(AlgorithmVariant algorithm) {
		this.algorithm = algorithm;
	}
}
