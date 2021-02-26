package mo4i.main;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.uma.jmetal.util.JMetalException;

import Actions.Action;
import UI.UIHandler;
import UI.UIUtils;

public class Main {
	private static Scanner s = new Scanner(System.in);
	private static boolean active;
	

	public static void main(String[] args) throws JMetalException, FileNotFoundException {
		active = true;
		printHeader();
		UIHandler ui = new UIHandler();
		ui.start();
	}
	
	public static void printHeader() {
		System.out.println("=======================================");
		UIUtils.printLogo();
		System.out.println("Multi-objective Optimisation 4 INTOCPS");
		System.out.println("\n=======================================");
	}
	
	public static void quit() {
		active = false;
	}
}
