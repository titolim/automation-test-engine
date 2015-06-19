/*******************************************************************************
 * ATE, Automation Test Engine
 *
 * Copyright 2014, Montreal PROT, or individual contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Montreal PROT.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package org.bigtester.ate.model.testresult;

import java.util.Date;


import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.model.casestep.ITestStep;
import org.eclipse.jdt.annotation.Nullable;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;

// TODO: Auto-generated Javadoc
/*
 * @Aspect tells the Spring framework that this class contains advice that should
 * be applied to one or more specified Pointcuts at runtime
 */
/**
 * The Class TestStepResult defines ....
 * 
 * @author Peidong Hu
 */

public class TestStepResult {
	/** The monitor. */
	private transient final static String MONITOR = "PERFORMANCE_MONITOR";

	/** The Constant STEPRESULTLIST. */
	public static final String STEPRESULTLIST = "StepResultList";
	/** The monitor_i. */
	@Nullable
	private Monitor monitor;
	/** The step name. */
	
	private String stepName;

	/** The this step. */
	private ITestStep thisStep;

	/**
	 * Instantiates a new test step result.
	 *
	 * @param stepName the step name
	 * @param thisStep the this step
	 */
	public TestStepResult(String stepName, ITestStep thisStep) {
		this.stepName = stepName;
		this.thisStep = thisStep;
	}
	
	/**
	 * Gets the monitor.
	 * 
	 * @return the monitor
	 */
	@Nullable
	public Monitor getMonitor() {
		return monitor;
	}

	/**
	 * @param monitor
	 *            the monitor to set
	 */
	public void setMonitor(Monitor monitor) {
		this.monitor = monitor;
	}

	/**
	 * Start monitor.
	 */

	public void startMonitor() {
		monitor = MonitorFactory.start(MONITOR);
	}

	/**
	 * Stop monitor.
	 */

	public void stopMonitor() {
		final Monitor monitor2 = monitor;
		if (null == monitor2 ) {
			throw GlobalUtils.createNotInitializedException("monitor");//NOPMD
		} else {
			monitor2.stop();
		}
	}

	/**
	 * get last access.
	 * 
	 * @return Date
	 */
	public Date getLastAccess() {
		final Monitor monitor2 = monitor;
		if (null == monitor2 ) {
			throw GlobalUtils.createNotInitializedException("monitor");
		} else {
			Date date = monitor2.getLastAccess();
			if (null == date) throw GlobalUtils.createInternalError("getLastAccess");
			else return date;
		}
	}

	/**
	 * get call count.
	 * 
	 * @return int
	 */
	public int getCallCount() {
		final Monitor retVal = monitor;
		if (null == retVal) {
			throw GlobalUtils.createNotInitializedException("monitor");
		} else {
			return (int) retVal.getHits();
		}
	}

	/**
	 * get average call time.
	 * 
	 * @return double
	 */
	public double getAverageCallTime() {
		final Monitor retVal = monitor;
		if (null == retVal) {
			throw GlobalUtils.createNotInitializedException("monitor");
		} else {
			return retVal.getAvg() / 1000;
		}
	
	}

	/**
	 * get last call time.
	 * 
	 * @return double
	 */
	public double getLastCallTime() {
		final Monitor retVal = monitor;
		if (null == retVal) {
			throw GlobalUtils.createNotInitializedException("monitor");
		} else {
			return retVal.getLastValue() / 1000;
		}
		
	}

	/**
	 * get maximum call time.
	 * 
	 * @return double
	 */
	public double getMaximumCallTime() {
		final Monitor retVal = monitor;
		if (null == retVal) {
			throw GlobalUtils.createNotInitializedException("monitor");
		} else {
			return retVal.getMax() / 1000;
		}
	
	}

	/**
	 * get minimum call time.
	 * 
	 * @return double
	 */
	public double getMinimumCallTime() {
		final Monitor retVal = monitor;
		if (null == retVal) {
			throw GlobalUtils.createNotInitializedException("monitor");
		} else {
			return retVal.getMin() / 1000;
		}
		
	}

	/**
	 * get total call time.
	 * 
	 * @return double
	 */
	public double getTotalCallTime() {
		final Monitor retVal = monitor;
		if (null == retVal) {
			throw GlobalUtils.createNotInitializedException("monitor");
		} else {
			return retVal.getTotal() / 1000;
		}
		
	}

	/**
	 * Gets the this step.
	 * 
	 * @return the thisStep
	 */
	public ITestStep getThisStep() {
		return thisStep;
	}

	/**
	 * Sets the this step.
	 * 
	 * @param thisStep
	 *            the thisStep to set
	 */
	public void setThisStep(ITestStep thisStep) {
		this.thisStep = thisStep;
	}

	/**
	 * Gets the step name.
	 * 
	 * @return the stepName
	 */
	public String getStepName() {
		return stepName;
	}

	/**
	 * Sets the step name.
	 * 
	 * @param stepName
	 *            the stepName to set
	 */
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}
}