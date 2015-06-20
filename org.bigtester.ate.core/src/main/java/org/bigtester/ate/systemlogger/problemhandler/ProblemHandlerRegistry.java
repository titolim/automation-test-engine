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
package org.bigtester.ate.systemlogger.problemhandler;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.bigtester.problomatic2.Problem;
import org.bigtester.problomatic2.ProblemHandler;

// TODO: Auto-generated Javadoc
/**
 * This class XsdNameSpaceHandlerRegistry defines ....
 * 
 * @author Peidong Hu
 *
 */
final public class ProblemHandlerRegistry {

	/** The throwable attached problem handler registry. */
	private static Map<Class<?>, Set<ProblemHandler>> throwableAttachedProblemHandlerRegistry = new ConcurrentHashMap<Class<?>, Set<ProblemHandler>>();// NOPMD

	/** The problem attached problem handler registry. */
	private static Map<Class<?>, Set<ProblemHandler>> problemAttachedProblemHandlerRegistry = new ConcurrentHashMap<Class<?>, Set<ProblemHandler>>();// NOPMD

	/** The generic problem handler registry. */
	private static Set<ProblemHandler> genericProblemHandlerRegistry = new HashSet<ProblemHandler>();

	private ProblemHandlerRegistry() {

	}

	/**
	 * Gets the all problem handlers.
	 *
	 * @return the all problem handlers
	 */
	public static Set<ProblemHandler> getAllProblemHandlers() {
		Set<ProblemHandler> retVal = new HashSet<ProblemHandler>();
		for (Map.Entry<Class<?>, Set<ProblemHandler>> entry : throwableAttachedProblemHandlerRegistry
				.entrySet()) {
			if (entry.getValue() != null) {
				retVal.addAll(entry.getValue());
			}
		}
		for (Map.Entry<Class<?>, Set<ProblemHandler>> entry : problemAttachedProblemHandlerRegistry
				.entrySet()) {
			if (entry.getValue() != null) {
				retVal.addAll(entry.getValue());
			}
		}
		retVal.addAll(genericProblemHandlerRegistry);
		return retVal;

	}

	/**
	 * Gets the problem attached problem handlers.
	 *
	 * @param attachedProb
	 *            the attached prob
	 * @return the problem attached problem handlers
	 */
	public static Set<ProblemHandler> getProblemAttachedProblemHandlers(
			Problem attachedProb) {
		Set<ProblemHandler> retVal = new HashSet<ProblemHandler>();
		if (problemAttachedProblemHandlerRegistry.get(attachedProb.getClass()) != null) {
			retVal.addAll(problemAttachedProblemHandlerRegistry
					.get(attachedProb.getClass()));
		}
		return retVal;
	}

	/**
	 * Gets the problem attached problem handlers.
	 *
	 * @param attachedProb
	 *            the attached prob
	 * @return the problem attached problem handlers
	 */
	public static Set<ProblemHandler> getGenericProblemHandlers() {
		return genericProblemHandlerRegistry;
	}
	/**
	 * Gets the throwable attached problem handler.
	 *
	 * @param attachedThr
	 *            the attached thr
	 * @return the throwable attached problem handler
	 */
	public static Set<ProblemHandler> getThrowableAttachedProblemHandler(
			Throwable attachedThr) {
		Set<ProblemHandler> retVal = new HashSet<ProblemHandler>();
		if (throwableAttachedProblemHandlerRegistry.get(attachedThr.getClass()) != null) {
			retVal.addAll(throwableAttachedProblemHandlerRegistry
					.get(attachedThr.getClass()));
		}
		return retVal;
	}

	/**
	 * Register throwable attached problem handler.
	 *
	 * @param thr
	 *            the thr
	 * @param hlr
	 *            the hlr
	 */
	public static void registerAttachedProblemHandler(Class<?> cls,
			ProblemHandler hlr) {
		Class<?> superCls = cls.getSuperclass();
		boolean throwableCls = false;//NOPMD
		boolean problemCls = false;//NOPMD
		while (superCls != null) {
			if (superCls.equals(Throwable.class)) {
				throwableCls = true;
				break;
			}
			if (superCls.equals(Problem.class)) {
				problemCls = true;
				break;
			}
			superCls = superCls.getSuperclass();
		}
		if (throwableCls) {
			Set<ProblemHandler> probHs = throwableAttachedProblemHandlerRegistry.get(cls); 
			if ( probHs  == null) {
				Set<ProblemHandler> addit = new HashSet<ProblemHandler>();
				addUniqueHandler(addit, hlr);
				throwableAttachedProblemHandlerRegistry.put(cls, addit);
			} else {
				addUniqueHandler(probHs, hlr);
			}
		}
		if (problemCls) {
			Set<ProblemHandler> probHs = problemAttachedProblemHandlerRegistry.get(cls);
			if (probHs == null) {
				Set<ProblemHandler> addit = new HashSet<ProblemHandler>();
				addUniqueHandler(addit, hlr);
				problemAttachedProblemHandlerRegistry.put(cls, addit);
			} else {
				addUniqueHandler(probHs, hlr);
			}
		}
	}

	/**
	 * Register generic problem handler.
	 *
	 * @param hlr
	 *            the hlr
	 */
	public static void registerGenericProblemHandler(ProblemHandler hlr) {
		addUniqueHandler(genericProblemHandlerRegistry, hlr);
	}
	
	private static void addUniqueHandler(Set<ProblemHandler> probHlrs, ProblemHandler hlr ) {
		if (probHlrs.isEmpty()) probHlrs.add(hlr);
		else {
			boolean haveIt = false;//NOPMD
			for (ProblemHandler pHlr : probHlrs) {
				if (!pHlr.equals(hlr)) {
					haveIt = true;
					break;
				}
			}
			if (!haveIt)
				probHlrs.add(hlr);
		}
	}

}
