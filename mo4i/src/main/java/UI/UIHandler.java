package UI;

import java.util.Arrays;
import algorithms.AlgorithmEnum;
import java.util.Scanner;

import algorithms.NSGAII;
import mo4i.main.RunSettings;
import problem.INTOCPSProblem;

public class UIHandler {
	
	private Scanner input;
	
	private boolean active;
	
	private static final String[] COMMANDS = {"exit","run","select"};
	
	public void start() {
		active = true;
		String command;
		while(active) {
			command = getCommand();
		}
	}
	
	private String getCommand() {
		String command;
		do {
			command = getInput();
			if(!isValidCommand(command)) {
				System.out.println("\'" + command + "\' is not a recognised command.");
			}
		} while(!isValidCommand(command));
		return command;
	}
	
	private static boolean isValidCommand(String command) {
		return Arrays.asList(COMMANDS).contains(command);
	}
	
	private String getInput() {
		input = new Scanner(System.in);
		System.out.print(UIUtils.INPUTPOINTER);
		return input.next();
	}
	
	private void executeCommand(String command) {
		switch(command) {
			case "exit":
				exit();
				break;
				
			case "select":
				select();
		}
	}
	
	private void exit() {
		setActive(false);
	}
	
	private void select() {
		for(int i = 0; i < AlgorithmEnum.values().length - 1; i++) {
			System.out.println("[" + i + "] " + AlgorithmEnum.values()[i]);
		}
		
		boolean validChoice = false;
		int choice = 0;
		
		while(!validChoice) {
			try {
				choice = Integer.parseInt(getInput());
				validChoice = true;
			}
			catch(Exception e) {
				System.out.println("Invalid choice!");
			}
		}
		
		
		
	}
	
	private void setActive(boolean active) {
		this.active = active;
	}
	
}
