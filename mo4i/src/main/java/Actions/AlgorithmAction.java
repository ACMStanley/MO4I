package Actions;

import algorithms.AllAlgorithms;
import algorithms.MO4IAlgorithm;
import algorithms.NSGAII;
import mo4i.main.INTOCPSProblem;
import mo4i.main.MO4IAlgorithmRunner;

public class AlgorithmAction implements Action{
	
	@Override
	public void execute() {
		MO4IAlgorithmRunner runner = new MO4IAlgorithmRunner(getAlg(ActionSettings.selectedAlg));
		runner.run();
	}
	
	private MO4IAlgorithm getAlg(AllAlgorithms alg) {
		MO4IAlgorithm out;
		if(alg == null) {
			System.out.println("Defaulting to NSGAII");
			alg = AllAlgorithms.NSGAII;
		}
		switch(alg) {
			case NSGAII:
				out = new NSGAII(new INTOCPSProblem());
				break;
			default:
				System.out.println("Defaulting to NSGAII");
				out = new NSGAII(new INTOCPSProblem());
		}
		return out;
	}
}
