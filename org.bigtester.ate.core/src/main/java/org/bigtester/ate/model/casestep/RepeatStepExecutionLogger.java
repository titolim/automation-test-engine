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
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.model.casestep.RepeatStepInOutEvent.RepeatStepInOut;
import org.eclipse.jdt.annotation.Nullable;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationListener;

// TODO: Auto-generated Javadoc
/**
 * This class RepeatStepExecutionLogger defines ....
 * 
 * @author Peidong Hu
 *
 */
public class RepeatStepExecutionLogger implements IRepeatStepExecutionLogger {

	/** The repeat step trees. */
	final private Map<String, DefaultTreeModel> repeatStepTrees = new ConcurrentHashMap<String, DefaultTreeModel>();// NOPMD

	/** The current execution tree. */
	private @Nullable DefaultTreeModel currentExecutionTree;

	/** The current execution node. */
	private @Nullable RepeatStepExecutionLoggerNode repeatStepExternalNode;

	/** The current repeat step node. */
	private @Nullable RepeatStepExecutionLoggerNode currentRepeatStepNode;

	public void stepInProcessing(RepeatStepInOutEvent event) {
		//preserve this step initial value for future nested repeatStep case
		RepeatStep rStep2 = (RepeatStep) event.getSource();
		RepeatStep rStep;
		if (null == rStep2)
			throw GlobalUtils
					.createInternalError("event of spring for repeatstep");
		try {
			rStep = (RepeatStep) rStep2.clone();
		} catch (CloneNotSupportedException cne) {
			throw GlobalUtils.createInternalError("preserve repeatstep", cne);
		}
		
		RepeatStepExecutionLoggerNode newNode = new RepeatStepExecutionLoggerNode(
				event.getRepeatStepName(), rStep, rStep2);

		final RepeatStepExecutionLoggerNode currentRepeatStepNode2 = currentRepeatStepNode;
		if (currentRepeatStepNode2 == null) {
			DefaultTreeModel repeatStepTree = new DefaultTreeModel(newNode);
			repeatStepTrees.put(event.getRepeatStepName(), repeatStepTree);
			currentExecutionTree = repeatStepTrees.get(event
					.getRepeatStepName());
		} else {
			repeatStepExternalNode = currentRepeatStepNode2;
			currentRepeatStepNode2.add(newNode);
		}
		currentRepeatStepNode = newNode;

	}

	public void stepOutProcessing(RepeatStepInOutEvent event) {
		//recover the initial values of the currently finished repeatStep for future repeatStep nested case.
		final RepeatStepExecutionLoggerNode currentRepeatStepNode2 = currentRepeatStepNode;
		if (currentRepeatStepNode2 == null) throw GlobalUtils.createInternalError("repeat Internal error.");
		RepeatStep rStep = (RepeatStep) event.getSource();
		BeanUtils.copyProperties(currentRepeatStepNode2.getRepeatStepWithInitialValues(), rStep);
		
		final RepeatStepExecutionLoggerNode repeatStepExternalNode2 = repeatStepExternalNode;
		if (repeatStepExternalNode2 == null) {
			currentRepeatStepNode = null;// NOPMD
		} else {
			
			currentRepeatStepNode = repeatStepExternalNode2;
			repeatStepExternalNode = (RepeatStepExecutionLoggerNode) repeatStepExternalNode2
					.getParent();
			
		}
		

	}

	/**
	 * Gets the current repeat step path.
	 *
	 * @return the current repeat step path
	 */
	public TreeNode[] getCurrentRepeatStepPathNodes() {

		final RepeatStepExecutionLoggerNode currentRepeatStepNode2 = currentRepeatStepNode;
		if (null == currentRepeatStepNode2) {
			throw GlobalUtils
					.createNotInitializedException("currentRepeatStepNode");

		} else {
			TreeNode[] retVal = currentRepeatStepNode2.getPath();
			if (null == retVal) {
				throw GlobalUtils
						.createNotInitializedException("currentRepeatStepNode tree");
			} else {
				return retVal;
			}

		}
	}

	/**
	 * Gets the current repeat step full path string.
	 *
	 * @return the current repeat step full path string
	 */
	public String getCurrentRepeatStepFullPathString() {
		TreeNode[] tNodes = getCurrentRepeatStepPathNodes();
		StringBuilder builder = new StringBuilder("");
		for (int index = 0; index <= tNodes.length - 1; index++) {
			RepeatStepExecutionLoggerNode tempNode = (RepeatStepExecutionLoggerNode) tNodes[index];
			builder.append((String) tempNode.getUserObject());
			if (index < tNodes.length - 1)
				builder.append("->");
		}
		String retVal = builder.toString();
		if (null == retVal)
			throw GlobalUtils.createInternalError("get repeat step loop path.");
		return retVal;
	}

	/**
	 * @return the repeatStepTrees
	 */
	public Map<String, DefaultTreeModel> getRepeatStepTrees() {
		return repeatStepTrees;
	}

	/**
	 * Adds the repeat step name.
	 *
	 * @param repeatStepName
	 *            the repeat step name
	 */
//	public void addRepeatStepName(String repeatStepName) {
//		RepeatStepExecutionLoggerNode newNode = new RepeatStepExecutionLoggerNode(
//				repeatStepName);
//		final RepeatStepExecutionLoggerNode repeatStepExternalNode2 = repeatStepExternalNode;
//		if (repeatStepExternalNode2 == null || null == currentExecutionTree) {
//			DefaultTreeModel repeatStepTree = new DefaultTreeModel(newNode);
//			repeatStepTrees.put(repeatStepName, repeatStepTree);
//			currentExecutionTree = repeatStepTrees.get(repeatStepName);
//		} else {
//			repeatStepExternalNode2.add(newNode);
//		}
//		currentRepeatStepNode = newNode;
//	}

	//
	// private @Nullable RepeatStepExecutionLoggerNode searchStepNode(
	// RepeatStepExecutionLoggerNode rootNode, String stepName) {
	//
	// RepeatStepExecutionLoggerNode theNode = null;
	// for (Enumeration enumer = rootNode.depthFirstEnumeration(); enumer
	// .hasMoreElements() && theNode == null;) {
	// RepeatStepExecutionLoggerNode node = (RepeatStepExecutionLoggerNode)
	// enumer
	// .nextElement();
	// if (((String) node.getUserObject()).equals(stepName)) {
	// theNode = node;
	// }
	// }
	// return theNode;
	// }
	//
	// public void addChildRepeatStepName(String childStepName,
	// String parentStepName) {
	// DefaultTreeModel childRootedStepTree = repeatStepTrees
	// .get(childStepName);
	// if (null == childRootedStepTree) {
	// // for (Map.Entry<String, DefaultTreeModel> entry : repeatStepTrees
	// // .entrySet()) {
	// // String rootStepName = entry.getKey();
	// // RepeatStepExecutionLoggerNode stepTreeRoot =
	// // (RepeatStepExecutionLoggerNode) entry
	// // .getValue().getRoot();
	// // if (rootStepName != null && stepTreeRoot != null) {
	// // RepeatStepExecutionLoggerNode childNode = searchStepNode(
	// // stepTreeRoot, rootStepName);
	// // if (null != childNode)
	// // break;
	// // }
	// // }
	// } else {
	// DefaultTreeModel parentStepTree = repeatStepTrees
	// .get(parentStepName);
	// RepeatStepExecutionLoggerNode newRootNode;
	// if (null == parentStepTree) {
	// newRootNode = new RepeatStepExecutionLoggerNode(
	// parentStepName);
	// } else {
	// newRootNode = (RepeatStepExecutionLoggerNode) parentStepTree
	// .getRoot();
	// }
	// newRootNode.add((RepeatStepExecutionLoggerNode) childRootedStepTree
	// .getRoot());
	// repeatStepTrees.put(parentStepName, new DefaultTreeModel(
	// newRootNode));
	// repeatStepTrees.remove(childStepName);
	//
	// }
	// DefaultTreeModel parentStepNode = repeatStepTrees.get(childStepName);
	// RepeatStepExecutionLoggerNode rootNode = new
	// RepeatStepExecutionLoggerNode(
	// childStepName);
	//
	// }

	/**
	 * @return the currentExecutionTree
	 */
	public @Nullable DefaultTreeModel getCurrentExecutionTree() {
		return currentExecutionTree;
	}

	/**
	 * @param currentExecutionTree
	 *            the currentExecutionTree to set
	 */
	public void setCurrentExecutionTree(
			@Nullable DefaultTreeModel currentExecutionTree) {
		this.currentExecutionTree = currentExecutionTree;
	}

	/**
	 * @return the currentExecutionNode
	 */
	public @Nullable RepeatStepExecutionLoggerNode getRepeatStepExternalNode() {
		return repeatStepExternalNode;
	}

	/**
	 * @param currentExecutionNode
	 *            the currentExecutionNode to set
	 */
	public void setRepeatStepExternalNode(
			@Nullable RepeatStepExecutionLoggerNode repeatStepExternalNode) {
		this.repeatStepExternalNode = repeatStepExternalNode;
	}

	/**
	 * @return the currentRepeatStepNode
	 */
	@Nullable
	public RepeatStepExecutionLoggerNode getCurrentRepeatStepNode() {
		return currentRepeatStepNode;
	}

	/**
	 * @param currentRepeatStepNode
	 *            the currentRepeatStepNode to set
	 */
	public void setCurrentRepeatStepNode(
			@Nullable RepeatStepExecutionLoggerNode currentRepeatStepNode) {
		this.currentRepeatStepNode = currentRepeatStepNode;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onApplicationEvent(@Nullable RepeatStepInOutEvent event) {
		if (null == event)
			GlobalUtils.createInternalError("spring event error");
		else {
			if (event.getInOutFlag() == RepeatStepInOut.IN) {
				stepInProcessing(event);
			} else {
				stepOutProcessing(event);
			}
		}

	}

}
