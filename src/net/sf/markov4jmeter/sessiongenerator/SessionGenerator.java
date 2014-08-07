package net.sf.markov4jmeter.sessiongenerator;

import java.io.IOException;
import java.util.Random;
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
						
			double random = randInt(0, 999);	
			
			if (random < 900) {
				createSession(generatedSession, entry.getEndTime() + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax), 0);	
			} else if (random >= 900 && random <= 1000) {
				createSession(generatedSession, entry.getEndTime() + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax), 1);
			}  else  {
				createSession(generatedSession, entry.getEndTime() + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax), 2);
			}
					
		}		
		
		System.out.println("Sessions Created: " + nbrOfSessions);
		
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
		
		double random = randInt(0, 999);	
		
		UseCase newUseCase = null;
		
		if (useCaseName.equals("entry")) {			
			if (random < 500) {
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
			if (random < 300) {
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
		
		double random = randInt(0, 999);	
		
		UseCase newUseCase = null;
				
		if (useCaseName.equals("entry")) {			
			if (random < 500) {
				newUseCase = new UseCase("browse", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else {
				newUseCase = new UseCase("search", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			}				
		} else if (useCaseName.equals("browse")) {			
			if (random < 400) {
				newUseCase = new UseCase("browse", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 400 && random < 600 ) {
				newUseCase = new UseCase("select", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 600 && random < 950) {
				newUseCase = new UseCase("search", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else {
				newUseCase = new UseCase("$", 0, 0);
			}								
		} else if (useCaseName.equals("search")) {			
			if (random < 400) {
				newUseCase = new UseCase("search", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 400 && random < 600) {
				newUseCase = new UseCase("select", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 600 && random < 950) {
				newUseCase = new UseCase("browse", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else {
				newUseCase = new UseCase("$", 0, 0);
			}			
		} else if (useCaseName.equals("addToCart")) {			
			if (random < 200) {
				newUseCase = new UseCase("browse", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 200 && random < 250) {
				newUseCase = new UseCase("addToCart", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 250 && random < 450) {
				newUseCase = new UseCase("search", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 450 && random < 650) {
				newUseCase = new UseCase("select", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 650 && random < 950) {
				newUseCase = new UseCase("pay", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else {
				newUseCase = new UseCase("$", 0, 0);
			}			
		} else if (useCaseName.equals("pay")) {			
			newUseCase = new UseCase("$", 0, 0);			
		} else if (useCaseName.equals("select")) {			
			if (random < 425) {
				newUseCase = new UseCase("browse", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 425 && random < 850) {
				newUseCase = new UseCase("search", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 850 && random < 900) {
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
		
		double random = randInt(0, 999);	
		UseCase newUseCase = null;
		
		if (useCaseName.equals("entry")) {			
			if (random < 800) {
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
			if (random < 800) {
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
		
		double random = randInt(0, 999);	
		UseCase newUseCase = null;
		
		if (useCaseName.equals("entry")) {			
			if (random <= 500) {
				newUseCase = new UseCase("browse", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else {
				newUseCase = new UseCase("search", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			}				
		} else if (useCaseName.equals("browse")) {			
			if (random <= 350) {
				newUseCase = new UseCase("browse", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 350 && random < 550) {
				newUseCase = new UseCase("select", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 550 && random < 850) {
				newUseCase = new UseCase("search", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else {
				newUseCase = new UseCase("$", 0, 0);
			}			
		} else if (useCaseName.equals("search")) {			
			if (random <= 350) {
				newUseCase = new UseCase("search", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 350 && random < 550) {
				newUseCase = new UseCase("select", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 550 && random < 850) {
				newUseCase = new UseCase("browse", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else {
				newUseCase = new UseCase("$", 0, 0);
			}				
		} else if (useCaseName.equals("addToCart")) {			
			if (random <= 220) {
				newUseCase = new UseCase("browse", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 220 && random < 325) {
				newUseCase = new UseCase("addToCart", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 325 && random < 550) {
				newUseCase = new UseCase("search", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 550 && random < 650) {
				newUseCase = new UseCase("select", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 650 && random < 950) {
				newUseCase = new UseCase("pay", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else {
				newUseCase = new UseCase("$", 0, 0);
			}			
		} else if (useCaseName.equals("pay")) {			
			newUseCase = new UseCase("$", 0, 0);			
		} else if (useCaseName.equals("select")) {			
			if (random <= 325) {
				newUseCase = new UseCase("browse", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 325 && random < 650) {
				newUseCase = new UseCase("search", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 650 && random < 950) {
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
		
		double random = randInt(0, 999);	
		UseCase newUseCase = null;
		
		if (useCaseName.equals("entry")) {			
			if (random <= 600) {
				newUseCase = new UseCase("browse", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else {
				newUseCase = new UseCase("search", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			}				
		} else if (useCaseName.equals("browse")) {			
			if (random <= 600) {
				newUseCase = new UseCase("browse", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 600 && random < 800) {
				newUseCase = new UseCase("select", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 800 && random < 900) {
				newUseCase = new UseCase("search", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else {
				newUseCase = new UseCase("$", 0, 0);
			}			
		} else if (useCaseName.equals("search")) {			
			if (random <= 600) {
				newUseCase = new UseCase("search", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 600 && random < 800) {
				newUseCase = new UseCase("select", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 800 && random < 900) {
				newUseCase = new UseCase("browse", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else {
				newUseCase = new UseCase("$", 0, 0);
			}				
		} else if (useCaseName.equals("select")) {			
			if (random <= 425) {
				newUseCase = new UseCase("browse", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
			} else if (random >= 425 && random < 850) {
				newUseCase = new UseCase("search", time, time + randLong(Configuration.timeOffSetMin, Configuration.timeOffSetMax));
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
	public static long randLong(int min, int max) {

	    // NOTE: Usually this should be a field rather than a method
	    // variable so that it is not re-seeded every call.
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return TimeUnit.NANOSECONDS.convert(randomNum, TimeUnit.MILLISECONDS);
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
	public static int randInt(int min, int max) {

	    // NOTE: Usually this should be a field rather than a method
	    // variable so that it is not re-seeded every call.
	    Random rand = new Random();

	    return rand.nextInt((max - min) + 1) + min;
	}
	
}
