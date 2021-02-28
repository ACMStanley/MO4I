package UI;

import java.util.Arrays;
import algorithms.AllAlgorithms;
import java.util.Scanner;

import algorithms.NSGAII;
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
		for(int i = 0; i < AllAlgorithms.values().length - 1; i++) {
			System.out.println("[" + i + "] " + AllAlgorithms.values()[i]);
		}
		
		//MISSING CODE
		
	}
	
	private void setActive(boolean active) {
		this.active = active;
	}
	
}
