/*******************************************************************************
 * ATE, Automation Test Engine
 *
 * Copyright 2017, Montreal PROT, or individual contributors as
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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.bigtester.ate.model.data.dao.ElementInputDataDaoImpl;
import org.bigtester.ate.model.data.dbtable.RepeatStepElementInputData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

// TODO: Auto-generated Javadoc
/**
 * This class CucumberFeatureDataInjector defines ....
 * 
 * @author Peidong Hu
 *
 */
@Component
public class CucumberFeatureDataInjector {

	/** The element input data dao. */
	@Autowired
	private ElementInputDataDaoImpl elementInputDataDao;

	/**
	 * Mapping feature data indexed by dataName to db dataId indexed data.
	 *
	 * @param featureDataTable
	 *            the feature data table
	 * @return the list of data indexed by dataId
	 */
	private List<RepeatStepElementInputData> mappingFeatureDataToDBIndexedData(
			List<Map<String, String>> featureDataTable, String repeatStepName) {
		
		List<RepeatStepElementInputData> eids = elementInputDataDao
				.getAllRepeatStepElementInputData(repeatStepName);
		Set<String> eidDataNames = eids.stream().map(eid -> eid.getDataName())
				.collect(Collectors.toSet());
		List<Map<String, String>> tmpFeatureDataTable = new ArrayList<Map<String, String>>();
		for (int featureDataIndex = 0; featureDataIndex < featureDataTable
				.size(); featureDataIndex++) {
			tmpFeatureDataTable.add(
					featureDataIndex,
					featureDataTable
							.get(featureDataIndex)
							.entrySet()
							.stream()
							.filter(row -> {
								return eidDataNames.contains(row.getKey());
							})
							.collect(
									Collectors.toMap(Entry::getKey,
											Entry::getValue)));
		}
		tmpFeatureDataTable.removeIf(row -> row.size() == 0);
		Map<String, List<String>> featureTableValueSets = new ConcurrentHashMap<String, List<String>>();
		tmpFeatureDataTable.forEach(dataMap -> {
			dataMap.entrySet().forEach(
					entry -> {
						if (featureTableValueSets.keySet().contains(
								entry.getKey())) {
							featureTableValueSets.get(entry.getKey()).add(
									entry.getValue());
						} else {
							List<String> values = new ArrayList<String>();
							values.add(entry.getValue());
							featureTableValueSets.put(entry.getKey(), values);
						}
					});
		});

		Map<String, List<RepeatStepElementInputData>> eidsMap = eids
				.stream()
				.collect(
						Collectors
								.groupingBy(RepeatStepElementInputData::getDataName));
		featureTableValueSets
				.entrySet()
				.forEach(
						entry -> {
							RepeatStepElementInputData newData;
							try {
								newData = (RepeatStepElementInputData) eidsMap
										.get(entry.getKey()).get(0).clone();
								newData.setIdColumn(0);

								eidsMap.get(entry.getKey()).clear();
								for (int ind = 0; ind < entry.getValue().size(); ind++) {
									RepeatStepElementInputData tmpData;

									tmpData = (RepeatStepElementInputData) newData
											.clone();
									tmpData.setDataValue(entry.getValue().get(
											ind));
									tmpData.setIteration(ind);
									eidsMap.get(entry.getKey()).add(tmpData);

								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						});

		return eidsMap.entrySet().stream().map(Entry::getValue)
				.flatMap(List::stream).collect(Collectors.toList());
	}

	/**
	 * Inject the table data
	 *
	 * @param featureDataTable
	 *            the feature data table
	 * @return the int
	 */
	
	public int inject(List<Map<String, String>> featureDataTable,
			String repeatStepName) {
		return this.elementInputDataDao.refreshRepeatStepData(
				mappingFeatureDataToDBIndexedData(featureDataTable,
						repeatStepName), repeatStepName).size();
	}

	public int inject(String stepName, String... featureValues) {
		System.out.println("test injector");
		return featureValues.length;
	}
}
