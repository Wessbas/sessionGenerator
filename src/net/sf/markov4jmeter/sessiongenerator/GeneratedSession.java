package net.sf.markov4jmeter.sessiongenerator;

import java.util.LinkedList;

public class GeneratedSession {

	private String sessionID;
	LinkedList<UseCase> useCaseList = new LinkedList<UseCase>();
	
	public String getSessionID() {
		return sessionID;
	}
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}
	public LinkedList<UseCase> getUseCaseList() {
		return useCaseList;
	}
	public void setUseCaseList(LinkedList<UseCase> useCaseList) {
		this.useCaseList = useCaseList;
	}
	public void addToUseCaseList(UseCase useCase) {
		this.useCaseList.add(useCase);
	}
	
}
