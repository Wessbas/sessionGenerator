package net.sf.markov4jmeter.sessiongenerator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FilePrinter {
	
	/**
	 * Print generated sessions to file. 
	 * 
	 * @param sessionOne
	 * @throws IOException
	 */
	public static void printSessionToFile (GeneratedSession sessionOne) throws IOException {
		
		// create the output directory if it does not exists
		File dir = new File(Configuration.directory);
		if (!dir.exists()) {
			dir.mkdir();
		}
		
		FileWriter fwRT = new FileWriter(dir
				+ "/generated_session_logs.dat", true);
		
		BufferedWriter bw = new BufferedWriter(fwRT);
				
		bw.append(sessionOne.getSessionID());		
		
		for (int i = 0; i< sessionOne.getUseCaseList().size(); i++) {
			bw.append(";");
			bw.append("\"" + sessionOne.getUseCaseList().get(i).getUseCaseName() + "\"");
			bw.append(":");
			bw.append(Long.toString(sessionOne.getUseCaseList().get(i).getStartTime()));
			bw.append(":");
			bw.append(Long.toString(sessionOne.getUseCaseList().get(i).getEndTime()));
		}		
		
		bw.append("\n");
        bw.flush();
        bw.close();
				
	}	
	
	/**
	 * Delete the output file if it exists.
	 */
	public static void deleteFileIfExists() {
		// delete sessionFile when it exists
		File dir = new File(Configuration.directory);
		if (dir.exists()) {
			deleteDirectory(dir);
			System.out.println("Files deleted!");
		}
	}
	
	/**
	 * Delete directory recursive.
	 * 
	 * @param directory
	 * @return
	 */
	private static boolean deleteDirectory(File directory) {
	    if(directory.exists()){
	        File[] files = directory.listFiles();
	        if(null!=files){
	            for(int i=0; i<files.length; i++) {
	                if(files[i].isDirectory()) {
	                    deleteDirectory(files[i]);
	                }
	                else {
	                    files[i].delete();
	                }
	            }
	        }
	    }
	    return(directory.delete());
	}
	
}
