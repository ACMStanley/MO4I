package Actions;

import java.util.Arrays;
import java.util.List;

import UI.UIHandler;
import algorithms.AllAlgorithms;

public class SelectAction implements Action{
	
	private UIHandler ui;
	List<AllAlgorithms> algs;
	
	public SelectAction(UIHandler ui){
		this.ui = ui;
		algs = Arrays.asList(AllAlgorithms.values());
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
		
		ActionSettings.selectedAlg = algs.get(choice);
	}
	
	private void displayChoices() {
		for(int i = 0; i < algs.size(); i++) {
			System.out.println("[" + i + "] " + algs.get(i).name());
		}
	}

}
