/***************************************************************************
 * Copyright (c) 2016 the WESSBAS project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ***************************************************************************/


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
