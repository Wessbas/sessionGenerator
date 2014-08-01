package net.sf.markov4jmeter.sessiongenerator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class SessionGenerator {
	
	private String directory;
	
	/**
	 * @param nbrOfSessions
	 * @throws IOException
	 */
	public void createSession(int nbrOfSessions, String directory) throws IOException {
		
		this.directory = directory;
				
		for (int i = 0; i < nbrOfSessions; i++) {
			
			// currentTime
			long time = System.currentTimeMillis();
			
			time = TimeUnit.NANOSECONDS.convert(time, TimeUnit.MILLISECONDS);
			
			GeneratedSession generatedSession = new GeneratedSession();
								
			// generate session ID
			UUID sessionID = UUID.randomUUID();
			generatedSession.setSessionID(sessionID.toString());		
			
			UseCase entry = new UseCase("entry", time, time + 1000);			
			generatedSession.addToUseCaseList(entry);
			
			long timeOffSet = randInt(800, 1200);
			timeOffSet = TimeUnit.NANOSECONDS.convert(timeOffSet, TimeUnit.MILLISECONDS);
			
			createSession(generatedSession, time + timeOffSet, 2);
			
//			double random = Math.random();		
//			
//			if (random < 0.5) {
//				createSession(generatedSession, time + timeOffSet, 0);	
//			} else if (random >= 0.5 && random <= 0.75) {
//				createSession(generatedSession, time + timeOffSet, 1);
//			} else {
//				createSession(generatedSession, time + timeOffSet, 2);
//			}				
					
		}		
		
		System.out.println("Sessions Created " + nbrOfSessions + " times");
		
	} 
	
	/**
	 * @param generatedSession
	 * @param time
	 * @throws IOException
	 */
	private void createSession(GeneratedSession generatedSession, long time, final int sessionType) throws IOException {		
		UseCase currentUseCase = generatedSession.getUseCaseList().getLast();	
		UseCase nextUseCase = null;
				
		if (sessionType == 0) {
			nextUseCase = getNextUseCaseOccationalBuyer(currentUseCase.getUseCaseName() , time);	
		} else if (sessionType == 1) {
			nextUseCase = getNextUseCaseHeavyBuyer(currentUseCase.getUseCaseName() , time);	
		} else if (sessionType == 2) {
			nextUseCase = getNextUseCaseNeverBuyer(currentUseCase.getUseCaseName() , time);	
		} 	
		
		// end condition
		if (!nextUseCase.getUseCaseName().equals("$")) {				
			generatedSession.addToUseCaseList(nextUseCase);
			long timeOffSet = randInt(800, 1200);
			timeOffSet = TimeUnit.NANOSECONDS.convert(timeOffSet, TimeUnit.MILLISECONDS);
			createSession(generatedSession, time + timeOffSet, sessionType);
		}	else {	
			printToFile(generatedSession);
		}
	}

	/**
	 * Get next useCase of sessionType one.
	 * 
	 * @param useCaseName
	 * @param time
	 * @return
	 */
	private UseCase getNextUseCaseOccationalBuyer(String useCaseName, final long time) {
		
		double random = Math.random();
		
		UseCase newUseCase = null;
		
		long timeOffSet = randInt(800, 1200);
		timeOffSet = TimeUnit.NANOSECONDS.convert(timeOffSet, TimeUnit.MILLISECONDS);
		
		if (useCaseName.equals("entry")) {			
			if (random < 0.5) {
				newUseCase = new UseCase("browse", time, time + timeOffSet);
			} else {
				newUseCase = new UseCase("search", time, time + timeOffSet);
			}				
		} else if (useCaseName.equals("browse")) {			
			if (random < 0.4) {
				newUseCase = new UseCase("browse", time, time + timeOffSet);
			} else if (random >= 0.4 && random < 0.6) {
				newUseCase = new UseCase("select", time, time + timeOffSet);
			} else if (random >= 0.6 && random < 0.95) {
				newUseCase = new UseCase("search", time, time + timeOffSet);
			} else {
				newUseCase = new UseCase("$", 0, 0);
			}								
		} else if (useCaseName.equals("search")) {			
			if (random < 0.4) {
				newUseCase = new UseCase("search", time, time + timeOffSet);
			} else if (random >= 0.4 && random < 0.6) {
				newUseCase = new UseCase("select", time, time + timeOffSet);
			} else if (random >= 0.6 && random < 0.95) {
				newUseCase = new UseCase("browse", time, time + timeOffSet);
			} else {
				newUseCase = new UseCase("$", 0, 0);
			}			
		} else if (useCaseName.equals("addToCart")) {			
			if (random < 0.2) {
				newUseCase = new UseCase("browse", time, time + timeOffSet);
			} else if (random >= 0.2 && random < 0.25) {
				newUseCase = new UseCase("addToCart", time, time + timeOffSet);
			} else if (random >= 0.25 && random < 0.45) {
				newUseCase = new UseCase("search", time, time + timeOffSet);
			} else if (random >= 0.45 && random < 0.65) {
				newUseCase = new UseCase("select", time, time + timeOffSet);
			} else if (random >= 0.65 && random < 0.95) {
				newUseCase = new UseCase("pay", time, time + timeOffSet);
			} else {
				newUseCase = new UseCase("$", 0, 0);
			}			
		} else if (useCaseName.equals("pay")) {			
			newUseCase = new UseCase("$", 0, 0);			
		} else if (useCaseName.equals("select")) {			
			if (random < 0.425) {
				newUseCase = new UseCase("browse", time, time + timeOffSet);
			} else if (random >= 0.425 && random < 0.85) {
				newUseCase = new UseCase("search", time, time + timeOffSet);
			} else if (random >= 0.85 && random < 0.9) {
				newUseCase = new UseCase("addToCart", time, time + timeOffSet);
			} else {
				newUseCase = new UseCase("$", 0, 0);
			}			
		}
		
		return newUseCase;
		
	}
	
	/**
	 * Get next useCase of sessionType one.
	 * 
	 * @param useCaseName
	 * @param time
	 * @return
	 */
	private UseCase getNextUseCaseHeavyBuyer(String useCaseName, final long time) {
		
		double random = Math.random();
		UseCase newUseCase = null;
		
		int timeOffSet = randInt(800, 1200);
		
		if (useCaseName.equals("entry")) {			
			if (random <= 0.5) {
				newUseCase = new UseCase("browse", time, time + timeOffSet);
			} else {
				newUseCase = new UseCase("search", time, time + timeOffSet);
			}				
		} else if (useCaseName.equals("browse")) {			
			if (random <= 0.35) {
				newUseCase = new UseCase("browse", time, time + timeOffSet);
			} else if (random >= 0.35 && random < 0.55) {
				newUseCase = new UseCase("select", time, time + timeOffSet);
			} else if (random >= 0.55 && random < 0.85) {
				newUseCase = new UseCase("search", time, time + timeOffSet);
			} else {
				newUseCase = new UseCase("$", 0, 0);
			}			
		} else if (useCaseName.equals("search")) {			
			if (random <= 0.35) {
				newUseCase = new UseCase("search", time, time + timeOffSet);
			} else if (random >= 0.35 && random < 0.55) {
				newUseCase = new UseCase("select", time, time + timeOffSet);
			} else if (random >= 0.55 && random < 0.85) {
				newUseCase = new UseCase("browse", time, time + timeOffSet);
			} else {
				newUseCase = new UseCase("$", 0, 0);
			}				
		} else if (useCaseName.equals("addToCart")) {			
			if (random <= 0.225) {
				newUseCase = new UseCase("browse", time, time + timeOffSet);
			} else if (random >= 0.225 && random < 0.325) {
				newUseCase = new UseCase("addToCart", time, time + timeOffSet);
			} else if (random >= 0.325 && random < 0.55) {
				newUseCase = new UseCase("search", time, time + timeOffSet);
			} else if (random >= 0.55 && random < 0.65) {
				newUseCase = new UseCase("select", time, time + timeOffSet);
			} else if (random >= 0.65 && random < 0.95) {
				newUseCase = new UseCase("pay", time, time + timeOffSet);
			} else {
				newUseCase = new UseCase("$", 0, 0);
			}			
		} else if (useCaseName.equals("pay")) {			
			newUseCase = new UseCase("$", 0, 0);			
		} else if (useCaseName.equals("select")) {			
			if (random <= 0.325) {
				newUseCase = new UseCase("browse", time, time + timeOffSet);
			} else if (random >= 0.325 && random < 0.65) {
				newUseCase = new UseCase("search", time, time + timeOffSet);
			} else if (random >= 0.65 && random < 0.95) {
				newUseCase = new UseCase("addToCart", time, time + timeOffSet);
			} else {
				newUseCase = new UseCase("$", 0, 0);
			}			
		}
		
		return newUseCase;
		
	}
	
	/**
	 * Get next useCase of sessionType one.
	 * 
	 * @param useCaseName
	 * @param time
	 * @return
	 */
	private UseCase getNextUseCaseNeverBuyer(String useCaseName, final long time) {
		
		double random = Math.random();
		UseCase newUseCase = null;
		
		int timeOffSet = randInt(800, 1200);
		
		if (useCaseName.equals("entry")) {			
			if (random <= 0.6) {
				newUseCase = new UseCase("browse", time, time + timeOffSet);
			} else {
				newUseCase = new UseCase("search", time, time + timeOffSet);
			}				
		} else if (useCaseName.equals("browse")) {			
			if (random <= 0.6) {
				newUseCase = new UseCase("browse", time, time + timeOffSet);
			} else if (random >= 0.6 && random < 0.8) {
				newUseCase = new UseCase("select", time, time + timeOffSet);
			} else if (random >= 0.8 && random < 0.9) {
				newUseCase = new UseCase("search", time, time + timeOffSet);
			} else {
				newUseCase = new UseCase("$", 0, 0);
			}			
		} else if (useCaseName.equals("search")) {			
			if (random <= 0.6) {
				newUseCase = new UseCase("search", time, time + timeOffSet);
			} else if (random >= 0.6 && random < 0.8) {
				newUseCase = new UseCase("select", time, time + timeOffSet);
			} else if (random >= 0.8 && random < 0.9) {
				newUseCase = new UseCase("browse", time, time + timeOffSet);
			} else {
				newUseCase = new UseCase("$", 0, 0);
			}				
		} else if (useCaseName.equals("select")) {			
			if (random <= 0.425) {
				newUseCase = new UseCase("browse", time, time + timeOffSet);
			} else if (random >= 0.425 && random < 0.85) {
				newUseCase = new UseCase("search", time, time + timeOffSet);
			}  else {
				newUseCase = new UseCase("$", 0, 0);
			}			
		}
		
		return newUseCase;
		
	}
	
	/**
	 * Returns a pseudo-random number between min and max, inclusive.
	 * The difference between min and max can be at most
	 * <code>Integer.MAX_VALUE - 1</code>.
	 *
	 * @param min Minimum value
	 * @param max Maximum value.  Must be greater than min.
	 * @return Integer between min and max, inclusive.
	 * @see java.util.Random#nextInt(int)
	 */
	private int randInt(int min, int max) {

	    // NOTE: Usually this should be a field rather than a method
	    // variable so that it is not re-seeded every call.
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	/**
	 * Print generated sessions to file. 
	 * 
	 * @param sessionOne
	 * @throws IOException
	 */
	private void printToFile (GeneratedSession sessionOne) throws IOException {
		
		// create the output directory if it does not exists
		File dir = new File(this.directory);
		if (!dir.exists()) {
			dir.mkdir();
		}
		
		FileWriter fwRT = new FileWriter(this.directory 
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
	
	
}
