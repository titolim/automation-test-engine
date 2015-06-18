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
package org.bigtester.ate.resultmaker;


import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.model.casestep.BaseTestStep;
import org.bigtester.ate.model.testresult.TestStepResult;

import org.testng.ITestResult;
import org.testng.Reporter;

import com.thoughtworks.xstream.XStream;
//import com.rits.cloning.Cloner;

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
@Aspect
public class StepResultMaker {

	/** The Step result list constant. */
	public static final String STEPRESULTLIST = "StepResultList";
//
//	private class SuperclassExclusionStrategy implements ExclusionStrategy {
//		public boolean shouldSkipClass(@Nullable Class<?> arg0) {
//			return false;
//		}
//
//		/**
//		 * {@inheritDoc}
//		 */
//		@Override
//		public boolean shouldSkipField(@Nullable FieldAttributes fieldAttributes) {
//			if (null == fieldAttributes) {
//				throw GlobalUtils.createNotInitializedException("fieldAttri");
//				
//			} else {
//				String fieldName = fieldAttributes.getName();
//				Class<?> theClass = fieldAttributes.getDeclaringClass();
//
//				return isFieldInSuperclass(theClass, fieldName);
//			}
//
//		}
//
//		private boolean isFieldInSuperclass(@Nullable Class<?> subclass,
//				@Nullable String fieldName) {
//			if (null == subclass) {
//				throw GlobalUtils.createNotInitializedException("subclass");
//				
//			} 
//			Class<?> superclass = subclass.getSuperclass();
//			Field field;
//
//			while (superclass != null) {
//				field = getField(superclass, fieldName);
//
//				if (field != null)
//					return true;
//
//				superclass = superclass.getSuperclass();
//			}
//
//			return false;
//		}
//
//		private @Nullable Field getField(@Nullable Class<?> theClass,
//				@Nullable String fieldName) {
//			if (null == theClass) {
//				throw GlobalUtils.createNotInitializedException("theClass");
//				
//			} 
//			try {
//				Field retVal = theClass.getDeclaredField(fieldName);
//				if (retVal == null) throw GlobalUtils.createInternalError("getDeclaredField");
//				return retVal;
//			} catch (Exception e) {
//				return null;
//			}
//		}
//
//	}
//
//	@SuppressWarnings("unchecked")
//	private BaseTestStep cloneThroughJson(@Nullable BaseTestStep t) {
//		if (null == t)
//			throw GlobalUtils
//					.createInternalError("Gson conversion input parameter error");
//		//Gson gson = new Gson();
//		GsonBuilder gBuilder = new GsonBuilder();
//		 
//		 gBuilder.addDeserializationExclusionStrategy(new SuperclassExclusionStrategy());
//		 gBuilder.addSerializationExclusionStrategy(new SuperclassExclusionStrategy());
//		 Gson gson = gBuilder
//			        .serializeNulls()
//			        .create();
//		String json = gson.toJson(t);
//		BaseTestStep retval = (BaseTestStep) gson.fromJson(json, t.getClass());
//		if (null == retval)
//			throw GlobalUtils.createInternalError("Gson conversion error");
//		return retval;
//	}

	/**
	 * Log.
	 * 
	 * @param joinPoint_p
	 *            the join point_p
	 */

	@SuppressWarnings("unchecked")
	@After("@annotation(org.bigtester.ate.annotation.StepLoggable)")
	public void reportStepResult(final JoinPoint joinPoint_p) {

		// Cloner cloner=new Cloner();
		//
		// BaseTestStep bts =cloner.deepClone((BaseTestStep)
		// joinPoint_p.getTarget());
//		BaseTestStep bts = cloneThroughJson((BaseTestStep) joinPoint_p
//				.getTarget());
		
		XStream xstream = new XStream();
		xstream.autodetectAnnotations(true);
		String xml = xstream.toXML((BaseTestStep) joinPoint_p
				.getTarget());
		BaseTestStep bts = (BaseTestStep)xstream.fromXML(xml);
		 if (bts == null) throw
				 GlobalUtils.createInternalError("stepresultmaker log function.");
		TestStepResult tsr = new TestStepResult(
				((BaseTestStep) joinPoint_p.getTarget()).getStepName(), bts);

		ITestResult testResult = Reporter.getCurrentTestResult();
		List<TestStepResult> stepResultList;
		try {
			if (testResult.getAttribute(TestStepResult.STEPRESULTLIST) instanceof List<?>) {
				stepResultList = (List<TestStepResult>) testResult // NOPMD
						.getAttribute(TestStepResult.STEPRESULTLIST);
			} else {
				stepResultList = new ArrayList<TestStepResult>();// NOPMD
			}
		} catch (final ClassCastException e) {

			stepResultList = new ArrayList<TestStepResult>();
		}

		stepResultList.add(tsr);
		testResult.setAttribute(TestStepResult.STEPRESULTLIST, stepResultList);
		
	}

}