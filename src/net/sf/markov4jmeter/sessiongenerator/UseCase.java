package net.sf.markov4jmeter.sessiongenerator;

public class UseCase {

	private String useCaseName;
	private long startTime;
	private long endTime;
	
	public UseCase (String useCaseName) {
		this.useCaseName = useCaseName;
	}
	public UseCase (String useCaseName, long startTime, long endTime) {
		this.useCaseName = useCaseName;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	public String getUseCaseName() {
		return useCaseName;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	} 
	
}
