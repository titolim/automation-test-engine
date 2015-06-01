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
package com.bigtester.casestep;

import java.io.File;
import java.io.IOException;
import java.util.HashSet; 
import java.util.Set;

import org.apache.commons.io.FileUtils; 
import org.bigtester.ate.constant.ExceptionErrorCode;
import org.bigtester.ate.constant.ExceptionMessage;
import org.bigtester.ate.model.casestep.AbstractBaseJavaCodedStep;
import org.bigtester.ate.model.casestep.IJavaCodedStep;
import org.bigtester.ate.model.data.exception.RuntimeDataException; 
import org.bigtester.ate.model.page.exception.PageValidationException2;
import org.bigtester.ate.model.page.exception.StepExecutionException2; 
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

// TODO: Auto-generated Javadoc
/**
 * This class SaveAppliedJobReference defines ....
 * @author Peidong Hu
 *
 */
public class SaveAppliedJobReference extends AbstractBaseJavaCodedStep
		implements IJavaCodedStep {

	/** The Constant JOBREFERENCESSAVEFILE. */
	final public static String JOBREFERENCESSAVEFILE = "temp_data/jobReferencesSaveFile"; 
	
	/**
	 * {@inheritDoc}
	 */
	public void doStep() throws StepExecutionException2,
			PageValidationException2, RuntimeDataException {
		WebElement applyButtonLink = getMyWebDriver().getWebDriverInstance().findElement(By.xpath("(//span[@class='indeed-apply-widget indeed-apply-button-container indeed-apply-status-not-applied'])[1]"));
		String jobApplyID = applyButtonLink.getAttribute("data-indeed-apply-jobid");
		try {
			File yourFile = new File(JOBREFERENCESSAVEFILE);
			if(!yourFile.exists()) {
			    yourFile.createNewFile();
			}
			
			Set<String> lines = new HashSet<String>(FileUtils.readLines(new File(JOBREFERENCESSAVEFILE), "utf-8"));
			if (lines.contains(jobApplyID)) {
				throw new RuntimeDataException(ExceptionMessage.MSG_TESTDATA_DUPLICATED, ExceptionErrorCode.REPEATTESTDATA_DUPLICATED);
			}
			else {
				lines.add(jobApplyID);
				FileUtils.writeLines(new File(JOBREFERENCESSAVEFILE), lines);
			}
		} catch (IOException e) {
			//TODO change runtimedataexception to include originating error
			throw new RuntimeDataException(ExceptionMessage.MSG_RUNTIMEDATA_NOTFOUND, ExceptionErrorCode.RUNTIMEDATA_NOTFOUND, e);
		}

	}

	 

}
