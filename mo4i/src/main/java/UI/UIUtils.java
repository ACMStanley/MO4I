package UI;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class UIUtils {
	private static final String LOGOPATH = "mo4ilogo.txt";
	public static final String INPUTPOINTER = "mo4i>> ";
	
	
	
	public static void printLogo() {
		boolean logoLoaded;
		Scanner logoScanner = null;
		try {
			logoScanner = new Scanner(new File(LOGOPATH));
			logoLoaded = true;
		} catch (FileNotFoundException e) {
			logoLoaded = false;
		}
		
		if(logoLoaded) {
			while(logoScanner.hasNextLine()) {
				System.out.println(logoScanner.nextLine());
			}
			logoScanner.close();
		}
		else {
			System.out.print("\nMO4I\n");
		}
	}
}
