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
package org.bigtester.ate.model.casestep;//NOPMD

import java.util.ArrayList;

import java.util.List;

import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.annotation.StepLoggable;
import org.bigtester.ate.constant.StepResultStatus;
import org.bigtester.ate.model.AbstractATEException;
import org.bigtester.ate.model.casestep.RepeatStepInOutEvent.RepeatStepInOut;
import org.bigtester.ate.model.data.IOnTheFlyData;
import org.bigtester.ate.model.data.IRepeatIncrementalIndex;
import org.bigtester.ate.model.data.IStepERValue;
import org.bigtester.ate.model.data.IStepInputData;
import org.bigtester.ate.model.data.exception.RuntimeDataException;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.bigtester.ate.model.page.exception.PageValidationException;
import org.bigtester.ate.model.page.exception.StepExecutionException;
import org.bigtester.ate.model.utils.ThinkTime;
import org.bigtester.ate.systemlogger.IATEProblemCreator;
import org.bigtester.ate.systemlogger.LogbackWriter;
import org.bigtester.ate.systemlogger.problems.IATEProblem;
import org.eclipse.jdt.annotation.Nullable;
import org.springframework.aop.support.AopUtils;

// TODO: Auto-generated Javadoc
/**
 * This class RepeatStep defines ....
 * 
 * @author Peidong Hu
 *
 */
public class RepeatStep extends BaseTestStep implements ITestStep, Cloneable {

	/** The start step id. */
	private String startStepName;

	/** The end step id. */
	private String endStepName;

	/** The continue on failure. */
	private boolean continueOnFailure;

	/** The asserter value remain same. */
	private boolean asserterValuesRemainSame;
	/** The repeat times. */
	private int numberOfIterations;

	/** The step i ds. */
	final private List<Integer> repeatingStepIndexesInTestCase = new ArrayList<Integer>();

	/** The repeating steps. */
	final private List<ITestStep> repeatingSteps = new ArrayList<ITestStep>();

	/** The refresh data values. */
	final private List<IStepInputData> dataValuesNeedRefresh = new ArrayList<IStepInputData>();

	/** The refresh er values. */
	final private List<IStepERValue> erValuesNeedRefresh = new ArrayList<IStepERValue>();

	/** The refresh on the fly values. */
	final private List<IRepeatIncrementalIndex> repeatIndexValuesNeedRefresh = new ArrayList<IRepeatIncrementalIndex>();

	/** The external repeat node of this step. */
	private transient @Nullable RepeatStepExecutionLoggerNode externalRepeatNodeOfThisStep;

	/** The current repeat node of this step. */
	private transient @Nullable RepeatStepExecutionLoggerNode currentRepeatNodeOfThisStep;

	// /** The input data holders. */
	// final private List<IStepInputData> inputDataHolders;
	//
	// /** The data parsers. */
	// final private List<IDataParser> dataParsers;
	//
	// final private List<IExpectedResultAsserter> expectedResultAsserters;

	/**
	 * Instantiates a new repeat step.
	 *
	 * @param startStepName
	 *            the start step name
	 * @param endStepName
	 *            the end step name
	 * @param testCase
	 *            the test case
	 */
	public RepeatStep(String startStepName, String endStepName) {
		super();
		this.startStepName = startStepName;
		this.endStepName = endStepName;
		this.continueOnFailure = false;
		this.numberOfIterations = 1;
		// this.testCase = testCase;
		this.asserterValuesRemainSame = true;

	}

	private void buildRepeatStepContext() {
		repeatingStepIndexesInTestCase.clear();
		repeatingSteps.clear();
		erValuesNeedRefresh.clear();
		dataValuesNeedRefresh.clear();

		int startIndex = -1; // NOPMD
		int endIndex = -1; // NOPMD

		for (int i = 0; i < getTestCase().getTestStepList().size(); i++) {
			if (getTestCase().getTestStepList().get(i).getStepName()
					.equals(this.startStepName)) {
				startIndex = i;// NOPMD
			}
			if (getTestCase().getTestStepList().get(i).getStepName()
					.equals(this.endStepName)) {
				endIndex = i;// NOPMD
			}
		}
		if (startIndex == -1 || endIndex == -1)
			throw GlobalUtils
					.createNotInitializedException("startStepName or endStepName");
		for (int i = 0; i < getTestCase().getTestStepList().size(); i++) {
			if (i >= startIndex && i <= endIndex) {
				repeatingStepIndexesInTestCase.add(i);
				ITestStep thisStep = getTestCase().getTestStepList().get(i);
				repeatingSteps.add(thisStep);
				//TODO in future version, We need to use the pubishevent to build these erValuesNeedRefresh list.
				for (int asserterIndex = 0; asserterIndex < thisStep
						.getExpectedResultAsserter().size(); asserterIndex++) {
					erValuesNeedRefresh.add((IStepERValue) GlobalUtils
							.getTargetObject(thisStep
									.getExpectedResultAsserter()
									.get(asserterIndex).getStepERValue()));
				}
				
				
//				//TODO in future version, We need to use the pubishevent to build these dataValuesNeedRefresh list. 
//				if (thisStep instanceof IElementStep) {
//					MyWebElement<?> webE = ((IElementStep) thisStep)
//							.getMyWebElement();
//					if (webE.getTestObjectAction() instanceof IElementAction) {
//						ITestObjectAction<?> iTOA = webE.getTestObjectAction();
//						if (null != iTOA
//								&& ((IElementAction) iTOA).getDataValue() != null) {
//							dataValuesNeedRefresh.add((IStepInputData) GlobalUtils
//									.getTargetObject(((IElementAction) iTOA)
//											.getDataValue()));
//						}
//					}
//					//TODO implement recursive IStepJumpingEnclosedContainer which handle recursive steptypeservice
//				} else if (thisStep instanceof IStepJumpingEnclosedContainer) {
//					for (int j=0; j<((IStepJumpingEnclosedContainer)thisStep).getContainerStepList().size(); j++) {
//						ITestStep tmpStep = ((IStepJumpingEnclosedContainer)thisStep).getContainerStepList().get(j);
//						if (tmpStep instanceof IElementStep) {
//							MyWebElement<?> webE = ((IElementStep) tmpStep)
//									.getMyWebElement();
//							if (webE.getTestObjectAction() instanceof IElementAction) {
//								ITestObjectAction<?> iTOA = webE.getTestObjectAction();
//								if (null != iTOA
//										&& ((IElementAction) iTOA).getDataValue() != null) {
//									dataValuesNeedRefresh.add((IStepInputData) GlobalUtils
//											.getTargetObject(((IElementAction) iTOA)
//													.getDataValue()));
//								}
//							}
//						}
//					}
//				}

			}
		}

	}

	private void buildStepInputDatasNeedRefresh() {
		dataValuesNeedRefresh.clear();
		StepDataLogger sdl = GlobalUtils
				.findStepDataLoggerBean(getApplicationContext());
		if (null != sdl.getRepeatStepDataRegistry().get(
				GlobalUtils.getTargetObject(this))
				&& !sdl.getRepeatStepDataRegistry()
						.get(GlobalUtils.getTargetObject(this)).isEmpty()) {
			for (Object data : sdl.getRepeatStepDataRegistry().get(
					GlobalUtils.getTargetObject(this))) {
				if (data instanceof IStepInputData) {
					dataValuesNeedRefresh.add((IStepInputData) data);
				}
			}
		}
	}

	
	private void buildRepeatIndexes() {
		repeatIndexValuesNeedRefresh.clear();
		StepDataLogger sdl = GlobalUtils
				.findStepDataLoggerBean(getApplicationContext());
		if (null != sdl.getRepeatStepDataRegistry().get(
				GlobalUtils.getTargetObject(this))
				&& !sdl.getRepeatStepDataRegistry()
						.get(GlobalUtils.getTargetObject(this)).isEmpty()) {
			for (Object data : sdl.getRepeatStepDataRegistry().get(
					GlobalUtils.getTargetObject(this))) {
				if (data instanceof IOnTheFlyData<?> && data instanceof IRepeatIncrementalIndex) {
					repeatIndexValuesNeedRefresh.add((IRepeatIncrementalIndex) data);
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@StepLoggable(level = org.bigtester.ate.annotation.ATELogLevel.INFO)
	@Override
	public void doStep(@Nullable IStepJumpingEnclosedContainer jumpingContainer) throws StepExecutionException,
			PageValidationException, RuntimeDataException {
		if (null == jumpingContainer) jumpingContainer = (IStepJumpingEnclosedContainer) GlobalUtils.getTargetObject(getTestCase());

		repeatSteps(jumpingContainer);

	}

	/**
	 * run steps.
	 * 
	 * @throws RuntimeDataException
	 * @throws StepExecutionException
	 * @throws PageValidationException
	 */
	private void repeatSteps(IStepJumpingEnclosedContainer jumpingContainer) throws StepExecutionException,
			PageValidationException, RuntimeDataException {
		LogbackWriter.writeDebugInfo("entering repeatSteps:" + this.getStepName(), this.getClass());
		buildRepeatStepContext();
		
		getApplicationContext().publishEvent(
				new RepeatStepInOutEvent(this, RepeatStepInOut.IN));
		buildStepInputDatasNeedRefresh();
		buildRepeatIndexes();
		Exception thr = null;// NOPMD
		for (int iteration = 1; iteration <= getNumberOfIterations(); iteration++) {
			LogbackWriter.writeDebugInfo("entering step iteration:" + this.getStepName() + ":" + iteration, this.getClass());
			setCurrentIteration(iteration);
			
			getApplicationContext().publishEvent(
					new RepeatDataRefreshEvent(this, getRepeatStepLogger()
							.getCurrentRepeatStepPathNodes(), iteration));
			LogbackWriter.writeDebugInfo("finish first data refressh in step iteration:" + this.getStepName() + ":" + iteration, this.getClass());
			
			for (int i = 0; i < repeatingStepIndexesInTestCase.size(); i++) {
				LogbackWriter.writeDebugInfo("run step (index:" + i + "), in iteration:" + iteration +" of step:" + this.getStepName(), this.getClass());//NOPMD
				ITestStep currentTestStepTmp = getTestCase().getTestStepList()
						.get(repeatingStepIndexesInTestCase.get(i));
				if (null == currentTestStepTmp) {
					throw new IllegalStateException(
							"Test Step List was not successfully initialized by ApplicationContext at list index"
									+ i);
				} else {
					getTestCase().setCurrentTestStep(currentTestStepTmp);
				}

				if (AopUtils.getTargetClass(currentTestStepTmp) == RepeatStep.class) {
					((RepeatStep) GlobalUtils.getTargetObject(currentTestStepTmp))
							.setAsserterValuesRemainSame(this
									.isAsserterValuesRemainSame());
					((RepeatStep) GlobalUtils.getTargetObject(currentTestStepTmp))
							.setContinueOnFailure(this.continueOnFailure);
					LogbackWriter.writeDebugInfo("before entering repeat step, current test step in testcase is:" + getTestCase()
							.getCurrentTestStep().getStepName() + ";" +" current test step optional value is:" + getTestCase()
							.getCurrentTestStep().isOptionalStep() + " , in iteration:" + iteration +" of step:" + this.getStepName(), this.getClass());
				} else {
					currentTestStepTmp.setStepDescription(currentTestStepTmp
							.getStepDescription()
							+ " | "
							+ getRepeatStepLogger()
									.getCurrentRepeatStepFullPathString());
				}
				String tmpStepDesc = currentTestStepTmp.getStepDescription();// NOPMD
				try {
					currentTestStepTmp.doStep(jumpingContainer);// NOPMD
					currentTestStepTmp.setStepResultStatus(
							StepResultStatus.PASS);
					LogbackWriter.writeDebugInfo("current test step in testcase is:" + getTestCase()
							.getCurrentTestStep().getStepName() + ", in iteration:" + iteration +" of step:" + this.getStepName(), this.getClass());
				} catch (Exception e) { // NOPMD
					IATEProblem prob;
					if (e instanceof IATEProblemCreator) {// NOPMD
						prob = ((IATEProblemCreator) e).getAteProblem();
						ITestStep exceptionRaisingStep = ((AbstractATEException) e).getOriginalStepRaisingException();
						if (prob == null) {
							if (null == exceptionRaisingStep)
								prob = ((IATEProblemCreator) e)
									.initAteProblemInstance(currentTestStepTmp);
							else
								prob = ((IATEProblemCreator) e)
								.initAteProblemInstance(exceptionRaisingStep);
						} 
						
						boolean optionalStepRaisingException = false;//NOPMD
						if (exceptionRaisingStep != null)
							optionalStepRaisingException = exceptionRaisingStep.isOptionalStep(); //NOPMD
						if (!prob.isFatalProblem()
								&& prob.getStepIndexSkipTo() > -1) { // NOPMD
							i = repeatingStepIndexesInTestCase
									.indexOf(prob.getStepIndexSkipTo()); // NOPMD
							if (-1 == i)
								thr = e;
							if (AopUtils.getTargetClass(currentTestStepTmp) == RepeatStep.class)
								currentTestStepTmp
								.setStepResultStatus(StepResultStatus.NEUTRAL);
							else 
								currentTestStepTmp
								.setStepResultStatus(StepResultStatus.SKIP);
						} else if (!prob.isFatalProblem()
								&& optionalStepRaisingException) {
							int correlatedOptionalStepsUtilInclusiveIndex = -1;//NOPMD
							if (exceptionRaisingStep != null)
								correlatedOptionalStepsUtilInclusiveIndex = exceptionRaisingStep.getCorrelatedOptionalStepsUtilInclusiveIndex((IStepJumpingEnclosedContainer) GlobalUtils.getTargetObject(getTestCase())); //NOPMD
							if (correlatedOptionalStepsUtilInclusiveIndex > repeatingStepIndexesInTestCase
									.get(i)) {
								i = repeatingStepIndexesInTestCase
										.indexOf(
												correlatedOptionalStepsUtilInclusiveIndex);// NOPMD
								if (-1 == i) {
									prob.setStepIndexSkipTo(correlatedOptionalStepsUtilInclusiveIndex);
									thr = e;// NOPMD
								}
							}
							if (AopUtils.getTargetClass(currentTestStepTmp) == RepeatStep.class)
								currentTestStepTmp
										.setStepResultStatus(StepResultStatus.NEUTRAL);
							else
								currentTestStepTmp
										.setStepResultStatus(StepResultStatus.SKIP);
						} else {
							if (!this.continueOnFailure)
								thr = e;// NOPMD
						}
					} else {
						thr = e;// If exception was not handled, we don't know
								// what the exception/throwable could cause in
								// the ate, so we just stop the testcase.
					}

				}

				if (AopUtils.getTargetClass(currentTestStepTmp) == RepeatStep.class) {
					getApplicationContext().publishEvent(
							new RepeatDataRefreshEvent(this,
									getRepeatStepLogger()
											.getCurrentRepeatStepPathNodes(),
									iteration));
					LogbackWriter.writeDebugInfo("finish 2nd data refressh in step iteration:" + this.getStepName() + ":" + iteration + ", triggered by:"+ currentTestStepTmp.getStepName(), this.getClass());
					LogbackWriter.writeDebugInfo("before entering repeat step, current test step in testcase is:" + getTestCase()
							.getCurrentTestStep().getStepName() + ";" +" current test step optional value is:" + getTestCase()
							.getCurrentTestStep().isOptionalStep() + " , in iteration:" + iteration +" of step:" + this.getStepName(), this.getClass());
				}
				getTestCase().setCurrentTestStep(currentTestStepTmp);
				if (null == tmpStepDesc)
					tmpStepDesc = ""; // NOPMD
				else
					currentTestStepTmp.setStepDescription(tmpStepDesc);

				if (getTestCase().getStepThinkTime() > 0) {
					ThinkTime thinkTimer = new ThinkTime(getTestCase()
							.getStepThinkTime());
					thinkTimer.setTimer();
				}
				if (null != thr) {
					LogbackWriter.writeDebugInfo("exit repeat steps due to exception, step (index:" + i + "), in iteration:" + iteration +" of step:" + this.getStepName(), this.getClass());
					break;
				}
				LogbackWriter.writeDebugInfo("finish repeatingsteps iteration normally, step (index:" + i + " stepname: " + getTestCase().getTestStepList().get(i).getStepName() + "), in iteration:" + iteration +" of step:" + this.getStepName(), this.getClass());
				LogbackWriter.writeDebugInfo("total repeating steps number is:" + repeatingStepIndexesInTestCase.size() + " and last repeating step is: " + getTestCase().getTestStepList().get(repeatingStepIndexesInTestCase.get(repeatingStepIndexesInTestCase.size()-1)).getStepName() + ", in iteration:" + iteration +" of step:" + this.getStepName(), this.getClass());
			}
			if (null != thr) {
				LogbackWriter.writeDebugInfo("exit repeat iteration due to exception, in iteration:" + iteration +" of step:" + this.getStepName(), this.getClass());
				break;
			}
			LogbackWriter.writeDebugInfo("exit repeat iteration normally, in iteration:" + iteration +" of step:" + this.getStepName(), this.getClass());
		}
		getApplicationContext().publishEvent(
				new RepeatStepInOutEvent(this, RepeatStepInOut.OUT));
		if (null != thr) {
			if (thr instanceof StepExecutionException)
				throw (StepExecutionException) thr;
			else if (thr instanceof PageValidationException)
				throw (PageValidationException) thr;
			else if (thr instanceof RuntimeDataException)
				throw (RuntimeDataException) thr;
			else
				throw GlobalUtils.createInternalError("uncaught throwable", thr);
		}
	}

	/**
	 * @return the startStepID
	 */
	public String getStartStepName() {
		return startStepName;
	}

	/**
	 * @param startStepName
	 *            the startStepID to set
	 */
	public void setStartStepName(String startStepName) {
		this.startStepName = startStepName;
	}

	/**
	 * @return the endStepID
	 */
	public String getEndStepName() {
		return endStepName;
	}

	/**
	 * @param endStepName
	 *            the endStepID to set
	 */
	public void setEndStepName(String endStepName) {
		this.endStepName = endStepName;
	}

	/**
	 * @return the continueOnFailure
	 */
	public boolean isContinueOnFailure() {
		return continueOnFailure;
	}

	/**
	 * @param continueOnFailure
	 *            the continueOnFailure to set
	 */
	public void setContinueOnFailure(boolean continueOnFailure) {
		this.continueOnFailure = continueOnFailure;
	}

	/**
	 * @return the repeatTimes
	 */
	public int getNumberOfIterations() {
		return numberOfIterations;
	}

	/**
	 * @param numberOfIterations
	 *            the repeatTimes to set
	 */
	public void setNumberOfIterations(int numberOfIterations) {
		this.numberOfIterations = numberOfIterations;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	@Nullable
	public IMyWebDriver getMyWebDriver() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public RepeatStep clone() throws CloneNotSupportedException {
		RepeatStep retVal = (RepeatStep) super.clone();
		if (null == retVal)
			throw GlobalUtils.createInternalError("jvm clone");
		return retVal;
	}

	/**
	 * @return the asserterValueRemainSame
	 */
	public boolean isAsserterValuesRemainSame() {
		return asserterValuesRemainSame;
	}

	/**
	 * @param asserterValueRemainSame
	 *            the asserterValueRemainSame to set
	 */
	public void setAsserterValuesRemainSame(boolean asserterValueRemainSame) {
		this.asserterValuesRemainSame = asserterValueRemainSame;
	}

	/**
	 * @return the externalRepeatNodeOfThisStep
	 */
	@Nullable
	public RepeatStepExecutionLoggerNode getExternalRepeatNodeOfThisStep() {
		return externalRepeatNodeOfThisStep;
	}

	/**
	 * @param externalRepeatNodeOfThisStep
	 *            the externalRepeatNodeOfThisStep to set
	 */
	public void setExternalRepeatNodeOfThisStep(
			RepeatStepExecutionLoggerNode externalRepeatNodeOfThisStep) {
		this.externalRepeatNodeOfThisStep = externalRepeatNodeOfThisStep;
	}

	/**
	 * @return the currentRepeatNodeOfThisStep
	 */
	@Nullable
	public RepeatStepExecutionLoggerNode getCurrentRepeatNodeOfThisStep() {
		return currentRepeatNodeOfThisStep;
	}

	/**
	 * @param currentRepeatNodeOfThisStep
	 *            the currentRepeatNodeOfThisStep to set
	 */

	public void setCurrentRepeatNodeOfThisStep(
			RepeatStepExecutionLoggerNode currentRepeatNodeOfThisStep) {
		this.currentRepeatNodeOfThisStep = currentRepeatNodeOfThisStep;
	}


	/**
	 * @return the repeatingStepIndexesInTestCase
	 */
	public final List<Integer> getRepeatingStepIndexesInTestCase() {
		return repeatingStepIndexesInTestCase;
	}

	/**
	 * @return the repeatingSteps
	 */
	public final List<ITestStep> getRepeatingSteps() {
		return repeatingSteps;
	}

	/**
	 * @return the dataValuesNeedRefresh
	 */
	public final List<IStepInputData> getDataValuesNeedRefresh() {
		return dataValuesNeedRefresh;
	}

	/**
	 * @return the erValuesNeedRefresh
	 */
	public final List<IStepERValue> getErValuesNeedRefresh() {
		return erValuesNeedRefresh;
	}

	/**
	 * @return the repeatIndexValuesNeedRefresh
	 */
	public final List<IRepeatIncrementalIndex> getRepeatIndexValuesNeedRefresh() {
		return repeatIndexValuesNeedRefresh;
	}
	
}
