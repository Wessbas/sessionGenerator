package net.sf.markov4jmeter.sessiongenerator;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class SessionGenerator {
	
	/**
	 * @param nbrOfSessions
	 * @throws IOException
	 */
	public void createSession(int nbrOfSessions) throws IOException {		
			
		for (int i = 0; i < nbrOfSessions; i++) {
			
			GeneratedSession generatedSession = new GeneratedSession();
			
			// currentTime
			long time = System.currentTimeMillis();
			
			// converts milliseconds to nanoseconds
			time = TimeUnit.NANOSECONDS.convert(time, TimeUnit.MILLISECONDS);
			
			// generate session ID
			UUID sessionID = UUID.randomUUID();
			generatedSession.setSessionID(sessionID.toString());	
					
			// First UseCase
			UseCase entry = new UseCase("entry", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));			
			generatedSession.addToUseCaseList(entry);
						
			double random = Math.random();		
			
			if (random < 0.6) {
				createSession(generatedSession, entry.getEndTime() + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax), 0);	
			} else if (random >= 0.6 && random <= 1.0) {
				createSession(generatedSession, entry.getEndTime() + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax), 1);
			} 
//			else {
//				createSession(generatedSession, entry.getEndTime() + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax), 2);
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
			nextUseCase = getNextUseCaseDummyBuyer(currentUseCase.getUseCaseName() , time);	
		} else if (sessionType == 1) {
			nextUseCase = getNextUseCaseDumpBuyer(currentUseCase.getUseCaseName() , time);	
		} else if (sessionType == 2) {
			nextUseCase = getNextUseCaseNeverBuyer(currentUseCase.getUseCaseName() , time);	
		} 	
		
		// end condition
		if (!nextUseCase.getUseCaseName().equals("$")) {				
			generatedSession.addToUseCaseList(nextUseCase);
			createSession(generatedSession, nextUseCase.getEndTime() + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax), sessionType);
		}	else {	
			FilePrinter.printSessionToFile(generatedSession);
		}
	}

	/**
	 * Get next useCase of sessionType one.
	 * 
	 * @param useCaseName
	 * @param time
	 * @return
	 */
	private UseCase getNextUseCaseDummyBuyer(String useCaseName, final long time) {
		
		double random = Math.random();
		
		UseCase newUseCase = null;
		
		if (useCaseName.equals("entry")) {			
			if (random < 0.5) {
				newUseCase = new UseCase("browse", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else {
				newUseCase = new UseCase("search", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			}				
		} else if (useCaseName.equals("browse")) {			
			newUseCase = new UseCase("addToCart", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));								
		} else if (useCaseName.equals("search")) {			
			newUseCase = new UseCase("addToCart", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));		
		} else if (useCaseName.equals("addToCart")) {			
			newUseCase = new UseCase("pay", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));		
		} else if (useCaseName.equals("pay")) {	
			if (random < 0.3) {
				newUseCase = new UseCase("pay", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));	
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
	private UseCase getNextUseCaseOccationalBuyer(String useCaseName, final long time) {
		
		double random = Math.random();
		
		UseCase newUseCase = null;
				
		if (useCaseName.equals("entry")) {			
			if (random < 0.5) {
				newUseCase = new UseCase("browse", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else {
				newUseCase = new UseCase("search", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			}				
		} else if (useCaseName.equals("browse")) {			
			if (random < 0.4) {
				newUseCase = new UseCase("browse", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 0.4 && random < 0.6) {
				newUseCase = new UseCase("select", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 0.6 && random < 0.95) {
				newUseCase = new UseCase("search", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else {
				newUseCase = new UseCase("$", 0, 0);
			}								
		} else if (useCaseName.equals("search")) {			
			if (random < 0.4) {
				newUseCase = new UseCase("search", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 0.4 && random < 0.6) {
				newUseCase = new UseCase("select", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 0.6 && random < 0.95) {
				newUseCase = new UseCase("browse", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else {
				newUseCase = new UseCase("$", 0, 0);
			}			
		} else if (useCaseName.equals("addToCart")) {			
			if (random < 0.2) {
				newUseCase = new UseCase("browse", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 0.2 && random < 0.25) {
				newUseCase = new UseCase("addToCart", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 0.25 && random < 0.45) {
				newUseCase = new UseCase("search", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 0.45 && random < 0.65) {
				newUseCase = new UseCase("select", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 0.65 && random < 0.95) {
				newUseCase = new UseCase("pay", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else {
				newUseCase = new UseCase("$", 0, 0);
			}			
		} else if (useCaseName.equals("pay")) {			
			newUseCase = new UseCase("$", 0, 0);			
		} else if (useCaseName.equals("select")) {			
			if (random < 0.425) {
				newUseCase = new UseCase("browse", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 0.425 && random < 0.85) {
				newUseCase = new UseCase("search", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 0.85 && random < 0.9) {
				newUseCase = new UseCase("addToCart", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
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
	private UseCase getNextUseCaseDumpBuyer(String useCaseName, final long time) {
		
		double random = Math.random();
		UseCase newUseCase = null;
		
		if (useCaseName.equals("entry")) {			
			if (random < 0.8) {
				newUseCase = new UseCase("browse", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else {
				newUseCase = new UseCase("search", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			}				
		} else if (useCaseName.equals("browse")) {			
			newUseCase = new UseCase("select", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));								
		} else if (useCaseName.equals("search")) {			
			newUseCase = new UseCase("select", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));		
		} else if (useCaseName.equals("select")) {			
			newUseCase = new UseCase("addToCart", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));		
		} else if (useCaseName.equals("addToCart")) {	
			if (random < 0.2) {
				newUseCase = new UseCase("addToCart", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));	
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
		
		if (useCaseName.equals("entry")) {			
			if (random <= 0.5) {
				newUseCase = new UseCase("browse", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else {
				newUseCase = new UseCase("search", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			}				
		} else if (useCaseName.equals("browse")) {			
			if (random <= 0.35) {
				newUseCase = new UseCase("browse", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 0.35 && random < 0.55) {
				newUseCase = new UseCase("select", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 0.55 && random < 0.85) {
				newUseCase = new UseCase("search", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else {
				newUseCase = new UseCase("$", 0, 0);
			}			
		} else if (useCaseName.equals("search")) {			
			if (random <= 0.35) {
				newUseCase = new UseCase("search", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 0.35 && random < 0.55) {
				newUseCase = new UseCase("select", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 0.55 && random < 0.85) {
				newUseCase = new UseCase("browse", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else {
				newUseCase = new UseCase("$", 0, 0);
			}				
		} else if (useCaseName.equals("addToCart")) {			
			if (random <= 0.225) {
				newUseCase = new UseCase("browse", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 0.225 && random < 0.325) {
				newUseCase = new UseCase("addToCart", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 0.325 && random < 0.55) {
				newUseCase = new UseCase("search", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 0.55 && random < 0.65) {
				newUseCase = new UseCase("select", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 0.65 && random < 0.95) {
				newUseCase = new UseCase("pay", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else {
				newUseCase = new UseCase("$", 0, 0);
			}			
		} else if (useCaseName.equals("pay")) {			
			newUseCase = new UseCase("$", 0, 0);			
		} else if (useCaseName.equals("select")) {			
			if (random <= 0.325) {
				newUseCase = new UseCase("browse", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 0.325 && random < 0.65) {
				newUseCase = new UseCase("search", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 0.65 && random < 0.95) {
				newUseCase = new UseCase("addToCart", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
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
		
		if (useCaseName.equals("entry")) {			
			if (random <= 0.6) {
				newUseCase = new UseCase("browse", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else {
				newUseCase = new UseCase("search", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			}				
		} else if (useCaseName.equals("browse")) {			
			if (random <= 0.6) {
				newUseCase = new UseCase("browse", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 0.6 && random < 0.8) {
				newUseCase = new UseCase("select", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 0.8 && random < 0.9) {
				newUseCase = new UseCase("search", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else {
				newUseCase = new UseCase("$", 0, 0);
			}			
		} else if (useCaseName.equals("search")) {			
			if (random <= 0.6) {
				newUseCase = new UseCase("search", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 0.6 && random < 0.8) {
				newUseCase = new UseCase("select", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 0.8 && random < 0.9) {
				newUseCase = new UseCase("browse", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else {
				newUseCase = new UseCase("$", 0, 0);
			}				
		} else if (useCaseName.equals("select")) {			
			if (random <= 0.425) {
				newUseCase = new UseCase("browse", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 0.425 && random < 0.85) {
				newUseCase = new UseCase("search", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			}  else {
				newUseCase = new UseCase("$", 0, 0);
			}			
		}
		
		return newUseCase;
		
	}


	/**
	 * @param min
	 * @param max
	 * @return
	 */
	public long randLong(long min, long max) {
	    return (new java.util.Random().nextLong() % (max - min)) + min;
	}
	
}
