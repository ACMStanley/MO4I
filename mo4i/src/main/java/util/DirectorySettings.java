package util;

public class DirectorySettings {
	public static String dataOutPath = "../output/";
	
	public static final String FRONT_FILE_NAME = "FUN.CSV";
	
	public static final String VARIABLES_FILE_NAME = "VAR.CSV";
	
	public static final String oneShotOutputPath = "OneShotOut.CSV";
	
	public static String COEPath = "C:\\Users\\aiden\\OneDrive\\Documents\\into-cps-projects\\install_downloads";
	
	public static String MMJsonPath =
			"C:\\Users\\aiden\\OneDrive\\Documents\\University\\CPS\\Excercises\\CSC3322Exercise4-3\\CSC3322Exercise4\\linefollower\\Multi-Models\\lfr-non3d\\lfr-non3d-rep.mm.json";
	
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
