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
package org.bigtester.ate.model.data.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.bigtester.ate.constant.ExceptionErrorCode;
import org.bigtester.ate.constant.ExceptionMessage;
import org.bigtester.ate.model.data.dbtable.ElementInputData;
import org.bigtester.ate.model.data.dbtable.RepeatStepElementInputData;
import org.bigtester.ate.model.data.exception.RepeatTestDataException;
import org.bigtester.ate.model.data.exception.TestDataException;
import org.eclipse.jdt.annotation.Nullable;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

// TODO: Auto-generated Javadoc
/**
 * This class ElementInputDataDaoImpl defines ....
 * 
 * @author Peidong Hu
 *
 */
@Transactional
public class ElementInputDataDaoImpl extends BaseDaoImpl {

	/**
	 * Save.
	 *
	 * @param eid
	 *            the eid
	 * @return the long
	 */
	public String save(ElementInputData eid) {
		String retVal;
		getDbEM().persist(eid);
		retVal = eid.getStepEIDsetID();
		return retVal;
	}
	
	public List<RepeatStepElementInputData> refreshRepeatStepData(List<RepeatStepElementInputData> eids, String repeatStepName) {
		this.getAllRepeatStepElementInputData(repeatStepName).forEach(eid->{
			getDbEM().remove(eid);	
		});
		eids.forEach(eid->{
			if (eids.indexOf(eid)==0) {
				try {
					ElementInputData existingEid = this.getEid(eid.getStepEIDsetID());
					getDbEM().remove(existingEid);
					ElementInputData newEid = new ElementInputData();
					BeanUtils.copyProperties(eid, newEid);
					getDbEM().persist(newEid);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				getDbEM().persist(eid);
			}
		});
		
		//System.out.println( this.getAll());
		return this.getAllRepeatStepElementInputData(repeatStepName);
	}

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	public @Nullable List<ElementInputData> getAll() {
		return getDbEM().createQuery("SELECT p FROM ElementInputData p",
				ElementInputData.class).getResultList();
	}
	
	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	public @Nullable List<RepeatStepElementInputData> getAllRepeatStepElementInputData(String repeatStepName) {
		return getDbEM().createQuery("SELECT p FROM RepeatStepElementInputData p where p.repeatStepName=:repeatStepName",
				RepeatStepElementInputData.class).setParameter("repeatStepName", repeatStepName).getResultList();
	}

	/**
	 * Gets the value.
	 *
	 * @param inputDataID
	 *            the input data id
	 * @return the value
	 */
	public String getValue(String inputDataID) throws TestDataException {

		List<ElementInputData> sERs = (List<ElementInputData>) getDbEM()
				.createQuery(
						"select p from ElementInputData p where firstTimeExecution= 'Yes' and p.stepEIDsetID = :stepEIDsetID",
						ElementInputData.class)
				.setParameter("stepEIDsetID", inputDataID)// NOPMD
				.getResultList();
		if (sERs.isEmpty()) {
			throw new TestDataException(ExceptionMessage.MSG_TESTDATA_NOTFOUND,
					ExceptionErrorCode.TESTDATA_NOTFOUND);
		} else if (sERs.size() > 1) { // NOPMD
			throw new TestDataException(
					ExceptionMessage.MSG_TESTDATA_DUPLICATED,
					ExceptionErrorCode.TESTDATA_NOTFOUND);
		} else {
			return sERs.get(0).getDataValue();
		}

	}
	
	public ElementInputData getEid(String inputDataID) throws TestDataException {

		List<ElementInputData> sERs = (List<ElementInputData>) getDbEM()
				.createQuery(
						"select p from ElementInputData p where firstTimeExecution= 'Yes' and p.stepEIDsetID = :stepEIDsetID",
						ElementInputData.class)
				.setParameter("stepEIDsetID", inputDataID)// NOPMD
				.getResultList();
		if (sERs.isEmpty()) {
			throw new TestDataException(ExceptionMessage.MSG_TESTDATA_NOTFOUND,
					ExceptionErrorCode.TESTDATA_NOTFOUND);
		} else if (sERs.size() > 1) { // NOPMD
			throw new TestDataException(
					ExceptionMessage.MSG_TESTDATA_DUPLICATED,
					ExceptionErrorCode.TESTDATA_NOTFOUND);
		} else {
			return sERs.get(0);
		}

	}

	/**
	 * Gets the value.
	 *
	 * @param inputDataID
	 *            the input data id
	 * @return the value
	 */
	public String getValue(String inputDataID, String repeatStepName,
			String repeatStepExternalLoopPath, int iteration)
			throws RepeatTestDataException {
		
		if ("".equals(repeatStepExternalLoopPath))
			return getValue(inputDataID, repeatStepName, iteration);//NOPMD
		List<ElementInputData> retVal;
		String sql = "select p from ElementInputData p where repeatStepExternalLoopPath=:repeatStepExternalLoopPath and firstTimeExecution= 'No' and p.stepEIDsetID = :stepEIDsetID and p.repeatStepName=:repeatStepName and p.iteration=:iteration";
		TypedQuery<ElementInputData> query = getDbEM().createQuery(sql,
				ElementInputData.class);
		query.setParameter("repeatStepExternalLoopPath",
				repeatStepExternalLoopPath);
		query.setParameter("stepEIDsetID", inputDataID);
		query.setParameter("repeatStepName", repeatStepName);
		query.setParameter("iteration", iteration);
		retVal = (List<ElementInputData>) query.getResultList();
		if (retVal.isEmpty()) {
			throw new RepeatTestDataException(ExceptionMessage.MSG_TESTDATA_NOTFOUND,
					ExceptionErrorCode.REPEATTESTDATA_NOTFOUND, repeatStepName, repeatStepExternalLoopPath, iteration);
		} else if (retVal.size() > 1) { // NOPMD
			throw new RepeatTestDataException(
					ExceptionMessage.MSG_TESTDATA_DUPLICATED,
					ExceptionErrorCode.REPEATTESTDATA_DUPLICATED, repeatStepName, repeatStepExternalLoopPath, iteration);
		} else {
			return retVal.get(0).getDataValue();
		}

	}
	
	/**
	 * Gets the value.
	 *
	 * @param inputDataID
	 *            the input data id
	 * @return the value
	 */
	public String getValue(String inputDataID, String repeatStepName,
			int iteration)
			throws RepeatTestDataException {
		List<ElementInputData> retVal;
		String sql = "select p from ElementInputData p where repeatStepExternalLoopPath is null and FirstTimeExecution= 'No' and p.stepEIDsetID = :stepEIDsetID and p.repeatStepName=:repeatStepName and p.iteration=:iteration";
		TypedQuery<ElementInputData> query = getDbEM().createQuery(sql,
				ElementInputData.class);
		query.setParameter("stepEIDsetID", inputDataID);
		query.setParameter("repeatStepName", repeatStepName);
		query.setParameter("iteration", iteration);
		retVal = (List<ElementInputData>) query.getResultList();
		if (retVal.isEmpty()) {
			throw new RepeatTestDataException(ExceptionMessage.MSG_TESTDATA_NOTFOUND,
					ExceptionErrorCode.REPEATTESTDATA_NOTFOUND, repeatStepName, "", iteration);
		} else if (retVal.size() > 1) { // NOPMD
			throw new RepeatTestDataException(
					ExceptionMessage.MSG_TESTDATA_DUPLICATED,
					ExceptionErrorCode.REPEATTESTDATA_DUPLICATED,  repeatStepName, "", iteration);
		} else {
			return retVal.get(0).getDataValue();
		}

	}
	
}
