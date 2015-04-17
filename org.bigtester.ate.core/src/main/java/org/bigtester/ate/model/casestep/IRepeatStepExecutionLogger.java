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

import java.util.Map;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import org.eclipse.jdt.annotation.Nullable;
import org.springframework.context.ApplicationListener;

// TODO: Auto-generated Javadoc
/**
 * This class RepeatStepExecutionLogger defines ....
 * 
 * @author Peidong Hu
 *
 */
public interface IRepeatStepExecutionLogger extends
		ApplicationListener<RepeatStepInOutEvent> {


	/**
	 * Gets the current repeat step path.
	 *
	 * @return the current repeat step path
	 */
	TreeNode[] getCurrentRepeatStepPathNodes() ;

	/**
	 * Gets the current repeat step full path string.
	 *
	 * @return the current repeat step full path string
	 */
	String getCurrentRepeatStepFullPathString();

	/**
	 * @return the repeatStepTrees
	 */
	Map<String, DefaultTreeModel> getRepeatStepTrees();
	
	/**
	 * @return the currentExecutionTree
	 */
	@Nullable DefaultTreeModel getCurrentExecutionTree();
	

	/**
	 * @return the currentExecutionNode
	 */
	@Nullable RepeatStepExecutionLoggerNode getRepeatStepExternalNode() ;

	

	/**
	 * @return the currentRepeatStepNode
	 */
	@Nullable
	RepeatStepExecutionLoggerNode getCurrentRepeatStepNode() ;



}
