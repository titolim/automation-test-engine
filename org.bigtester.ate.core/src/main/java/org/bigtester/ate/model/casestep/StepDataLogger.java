/*******************************************************************************
 * ATE, Automation Test Engine
 *
 * Copyright 2015, Montreal PROT, or individual contributors as
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
package org.bigtester.ate.model.casestep;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.annotation.RepeatStepRefreshable;
import org.bigtester.ate.annotation.RepeatStepRefreshable.RefreshDataType;
import org.bigtester.ate.model.data.IOnTheFlyData;
import org.bigtester.ate.model.testresult.TestStepResult;
import org.eclipse.jdt.annotation.Nullable;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.thoughtworks.xstream.XStream;

// TODO: Auto-generated Javadoc
/**
 * This class StepDataLogger defines ....
 * @author Peidong Hu
 *
 */
@Aspect
public class StepDataLogger {
	final private Map<ITestStep, List<IOnTheFlyData<?>>> onTheFlies = new ConcurrentHashMap<ITestStep, List<IOnTheFlyData<?>>>();
	
	@Nullable
	private transient ITestStep currentExecutionStep;
	
	public void logData(IOnTheFlyData<?> data) {
		if (null == onTheFlies.get(currentExecutionStep)) {
			onTheFlies.put(currentExecutionStep, new ArrayList<IOnTheFlyData<?>>());
			onTheFlies.get(currentExecutionStep).add(data);
		} else if (!onTheFlies.get(currentExecutionStep).contains(data)) {
			onTheFlies.get(currentExecutionStep).add(data);
		}
	}

	/**
	 * @return the onTheFlies
	 */
	public Map<ITestStep, List<IOnTheFlyData<?>>> getOnTheFlies() {
		return onTheFlies;
	}

	

	/**
	 * @return the currentExecutionStep
	 */
	public ITestStep getCurrentExecutionStep() {
		final ITestStep currentExecutionStep2 = currentExecutionStep;
		if (currentExecutionStep2 == null) {
			throw GlobalUtils.createNotInitializedException("currentExecutionStep");
		} else {
			return currentExecutionStep2;
		}
	}

	/**
	 * @param currentExecutionStep the currentExecutionStep to set
	 */
	public void setCurrentExecutionStep(ITestStep currentExecutionStep) {
		this.currentExecutionStep = currentExecutionStep;
	}
	
	@SuppressWarnings("unchecked")
	@Before("@annotation(org.bigtester.ate.annotation.StepLoggable)")
	public void log(final JoinPoint joinPoint_p) {
		
		ITestStep bts = (ITestStep)joinPoint_p
				.getTarget();
		if (bts == null) throw
				 GlobalUtils.createInternalError("stepresultmaker log function.");
		currentExecutionStep = bts;
	}
	//@After("@annotation(org.bigtester.ate.annotation.RepeatStepRefreshable)")
	@After("execution(public * org.bigtester.ate.annotation.RepeatStepRefreshable+.*(..))")
	public boolean processDataLog(JoinPoint jPoint) {
		if (getDataType(jPoint) == RefreshDataType.ONTHEFLY) {
			Object targ = jPoint.getTarget();
			if (null == targ) throw GlobalUtils.createInternalError("RepeatStepRefreshable pointcut error");
			if ( targ instanceof IOnTheFlyData<?>) {
				logData((IOnTheFlyData<?>)targ);
			}
		}
		return true;
	}

	private RefreshDataType getDataType(JoinPoint thisJoinPoint)
    {
        MethodSignature methodSignature = (MethodSignature) thisJoinPoint.getSignature();
        Method targetMethod = methodSignature.getMethod();

        return ((RepeatStepRefreshable) targetMethod.getAnnotation(RepeatStepRefreshable.class)).dataType();
    }
}
