package uk.ac.ncl.astanley.mo4i.util;

public class DirectorySettings {
	public static final String dataOutPath = "../output/";
	
	public static String tempDirectoryPath = "../temp/";	
	public static final String FRONT_FILE_NAME = "FUN.CSV";
	
	public static final String VARIABLES_FILE_NAME = "VAR.CSV";
	
	public static final String oneShotOutputPath = "OneShotOut.CSV";
	
	public static String COEPath = "C:\\Users\\aiden\\OneDrive\\Documents\\into-cps-projects\\install_downloads";
	
	public static String oneShotResultsPath = "../temp/";
	
	public static String getFrontPath() {
		return dataOutPath + FRONT_FILE_NAME;
	}
	
	public static String getVariablesPath() {
		return dataOutPath + VARIABLES_FILE_NAME;
	}
	
	public static String getOneShotOutPath() {
		return dataOutPath + VARIABLES_FILE_NAME;
	}
}
