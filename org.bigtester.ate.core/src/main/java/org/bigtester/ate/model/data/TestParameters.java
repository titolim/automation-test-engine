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
package org.bigtester.ate.model.data;

import java.util.List;

import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.model.project.TestProject;
import org.eclipse.jdt.annotation.Nullable;
import org.springframework.context.ApplicationContext;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

// TODO: Auto-generated Javadoc
/**
 * The Class TestParameters defines ....
 *
 * @author Peidong Hu
 */
public class TestParameters 
{
    
    /** The test name. */
    private String testName;
    
    /** The test file name. */
    private String testFileName;

    /** The step think time. */
    private int stepThinkTime;
    
    /** The global app ctx. */
    @Nullable
    @XStreamOmitField
    private ApplicationContext globalAppCtx;
    
    /** The test project. */
    @Nullable
    private TestProject testProject;
    
    
    
//    /** The parent app ctx. */
//    @Nullable
//    @XStreamOmitField
//    private ApplicationContext parentAppCtx;
//    
    /** The parent test case data holders. */
    @Nullable
    private List<AbstractRunTimeInputDataHolder> parentTestCaseDataHolders;
    /**
	 * @return the stepThinkTime
	 */
	public int getStepThinkTime() {
		return stepThinkTime;
	}

	/**
	 * @param stepThinkTime the stepThinkTime to set
	 */
	public void setStepThinkTime(int stepThinkTime) {
		this.stepThinkTime = stepThinkTime;
	}

	/**
     * Instantiates a new test parameters.
     *
     * @param name the name
     * @param filename the filename
     */
    public TestParameters( String name, String filename ) {
        this.testName = name;
        this.testFileName = filename;
    }
    public TestParameters( String name, String filename, int stepThinkTime, ApplicationContext globalCtx, TestProject testProj ) {
        this.testName = name;
        this.testFileName = filename;
        this.stepThinkTime = stepThinkTime;
        this.globalAppCtx = globalCtx;
        this.testProject = testProj;
    }

    /**
     * Gets the test name.
     *
     * @return the test name
     */
    public String getTestName() {
        return testName;
    }
    
    /**
     * Gets the test file name.
     *
     * @return the testFileName
     */
	public String getTestFileName() {
		return testFileName;
	}

	/**
	 * Sets the test file name.
	 *
	 * @param testFileName the testFileName to set
	 */
	public void setTestFileName(String testFileName) {
		this.testFileName = testFileName;
	}

	/**
	 * Sets the test name.
	 *
	 * @param testName the testName to set
	 */
	public void setTestName(String testName) {
		this.testName = testName;
	}

	/**
	 * Gets the test filename.
	 *
	 * @return the test filename
	 */
	public String getTestFilename() {
        return testFileName;
    }
	
	/**
	 * Checks if is empty.
	 *
	 * @return true, if is empty
	 */
	public boolean isEmpty(){
		return testName.isEmpty() && testFileName.isEmpty();
	}

	/**
	 * @return the globalAppCtx
	 */
	public ApplicationContext getGlobalAppCtx() {
		final ApplicationContext globalAppCtx2 = globalAppCtx;
		if (null == globalAppCtx2 ) {
			throw new IllegalStateException("global application context is not correctly populated.");
		} else {
			return globalAppCtx2;
		}
	}

	/**
	 * @param globalAppCtx the globalAppCtx to set
	 */
	public void setGlobalAppCtx(ApplicationContext globalAppCtx) {
		this.globalAppCtx = globalAppCtx;
	}

//	/**
//	 * @return the parentAppCtx
//	 */
//	@Nullable
//	public ApplicationContext getParentAppCtx() {
//		return parentAppCtx;
//	}
//
//	/**
//	 * @param parentAppCtx the parentAppCtx to set
//	 */
//	public void setParentAppCtx(ApplicationContext parentAppCtx) {
//		this.parentAppCtx = parentAppCtx;
//	}

	/**
	 * @return the parentTestCaseDataHolders
	 */
	@Nullable
	public List<AbstractRunTimeInputDataHolder> getParentTestCaseDataHolders() {
		return parentTestCaseDataHolders;
	}

	/**
	 * @param parentTestCaseDataHolders the parentTestCaseDataHolders to set
	 */
	public void setParentTestCaseDataHolders(
			List<AbstractRunTimeInputDataHolder> parentTestCaseDataHolders) {
		this.parentTestCaseDataHolders = parentTestCaseDataHolders;
	}

	/**
	 * @return the testProject
	 */
	public TestProject getTestProject() {
		final TestProject testProject2 = testProject;
		if (testProject2 == null) {
			throw GlobalUtils.createNotInitializedException("testproject parameter");
		} else {
			return testProject2;
		}
	}

	/**
	 * @param testProject the testProject to set
	 */
	public void setTestProject(TestProject testProject) {
		this.testProject = testProject;
	}


}
