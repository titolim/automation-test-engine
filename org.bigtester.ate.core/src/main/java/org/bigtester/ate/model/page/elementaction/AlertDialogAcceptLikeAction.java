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
package org.bigtester.ate.model.page.elementaction;

import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.model.page.PageModelBase;
import org.bigtester.ate.model.page.atewebdriver.AbstractAlertDialog;
import org.bigtester.ate.model.page.atewebdriver.AlertDialogAcceptedEvent;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.eclipse.jdt.annotation.Nullable;

// TODO: Auto-generated Javadoc
/**
 * The Class ClickAction defines ....
 * 
 * @author Peidong Hu
 */
public class AlertDialogAcceptLikeAction extends PageModelBase implements
		IAlertDialogAction, ITestObjectActionImpl {

	/**
	 * @param myWd
	 */
	public AlertDialogAcceptLikeAction(IMyWebDriver myWd) {
		super(myWd);
	}

	
	/**
	 * {@inheritDoc}
	 */
	public @Nullable <T> T getCapability(Class<T> type) {
		if (this instanceof IAlertDialogAction) {
			return (T) this; //NOPMD
		} else {
			return null;
		}
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doAction(AbstractAlertDialog alertD) {
		alertD.accept();
		GlobalUtils.getApx().publishEvent(
				new AlertDialogAcceptedEvent(alertD));
		
	}

}
