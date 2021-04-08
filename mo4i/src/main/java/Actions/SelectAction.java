package Actions;

import java.util.Arrays;
import java.util.List;

import UI.UIHandler;
import algorithms.AlgorithmVariant;
import mo4i.main.Client;

public class SelectAction implements Action{
	
	private UIHandler ui;
	List<AlgorithmVariant> algs;
	
	public SelectAction(UIHandler ui){
		this.ui = ui;
		algs = Arrays.asList(AlgorithmVariant.values());
	}
	
	@Override
	public void execute() {
		
		displayChoices();
		
		boolean inputValid = false;
		int choice = -1;
		do {
			String input = ui.getInput();
			try {
				choice = Integer.parseInt(input);
			}
			catch(Exception e){
				System.out.println("Choice not valid.");
				continue;
			}
			
			if( choice == -1 || choice >  (algs.size() - 1)) {
				System.out.println("Choice not valid.");
			}
			else {
				inputValid = true;
			}
		}while(!inputValid);
		
		Client.getRunHandler().setAlgorithm(AlgorithmVariant.values()[choice]);
	}
	
	private void displayChoices() {
		System.out.println();
		for(int i = 0; i < algs.size(); i++) {
			System.out.println("  [" + i + "] " + algs.get(i).name());
		}
		System.out.println();
	}

}
