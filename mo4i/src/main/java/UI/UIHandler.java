package UI;

import java.util.Arrays;
import java.util.Scanner;
import Actions.Action;
import Actions.ExitAction;
import Actions.RunAction;
import Actions.SelectAction;
import algorithms.NSGAII;

public class UIHandler {
	
	private Scanner input;
	
	private static final String[] commands = {"exit","run","select"};
	
	public Action getAction() {
		return getCommand();
	}
	
	private Action getCommand() {
		String command;
		boolean commandValid;
		do {
			input = new Scanner(System.in);
			System.out.print(UIUtils.INPUTPOINTER);
			command = input.next();
			commandValid = isValidCommand(command);
			if(!commandValid) {
				System.out.println("Command \'" + command + "\' not recognised.");
			}
		}while(!commandValid);
		
		return resolveCommand(command);
	}
	
	private static boolean isValidCommand(String command) {
		return Arrays.asList(commands).contains(command);
	}
	
	public String getInput() {
		input = new Scanner(System.in);
		System.out.print(UIUtils.INPUTPOINTER);
		return input.next();
	}
	
	private Action resolveCommand(String command) {
		Action a;
		switch(command) {
			case "exit":
				a =  new ExitAction();
				break;
				
			case "run":
				a = new RunAction();
				break;
				
			case "select":
				a = new SelectAction(this);
				break;
				
			default:
				throw new IllegalArgumentException("Invalid Command!");
		}
		return a;
	}
}
