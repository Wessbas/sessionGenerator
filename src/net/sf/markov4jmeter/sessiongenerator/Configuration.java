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

public class Configuration {

	public static final String directory = "workspace/net.sf.markov4jmeter.sessionGenerator/output";
	public static final int nrOfSessions = 20000;
	
	// min offSet in milliseconds.
    public static final int timeOffSetMin = 1000;
    
	// max offSet in milliseconds.
    public static final int timeOffSetMax = 2000;
		
}
