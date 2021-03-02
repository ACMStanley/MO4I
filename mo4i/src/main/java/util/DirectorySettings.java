package util;

public class DirectorySettings {
	public static String dataOutPath = "../output/";
	
	public static final String FRONT_FILE_NAME = "FUN.CSV";
	
	public static final String VARIABLES_FILE_NAME = "VAR.CSV";
	
	public static String getFrontPath() {
		return dataOutPath + FRONT_FILE_NAME;
	}
	
	public static String getVariablesPath() {
		return dataOutPath + VARIABLES_FILE_NAME;
	}
}
