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
