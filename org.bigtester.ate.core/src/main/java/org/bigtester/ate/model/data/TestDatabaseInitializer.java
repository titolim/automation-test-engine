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
package org.bigtester.ate.model.data; //NOPMD

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.bigtester.ate.GlobalUtils;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.eclipse.jdt.annotation.Nullable;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

// TODO: Auto-generated Javadoc
/**
 * This class DatabaseInitializer defines ....
 * 
 * @author Peidong Hu
 *
 */

public class TestDatabaseInitializer {

	/** The init xml file. */
	@Nullable
	final private List<InputStream> initXmlFiles = new ArrayList<InputStream>();

	/** The datasets. */
	@Nullable
	transient private IDataSet[] datasets;// NOPMD

	/** The single init xml file. */
	@Nullable
	private InputStream singleInitXmlFile;

	/**
	 * Gets the inits the xml file.
	 *
	 * @return the initXmlFile
	 */
	public List<InputStream> getInitXmlFiles() {
		final List<InputStream> initXmlFiles2 = initXmlFiles;
		if (null == initXmlFiles2) {
			throw new IllegalStateException(
					"initxml files are not correctly populated.");
		} else {
			return initXmlFiles2;
		}
	}

	/**
	 * Sets the inits the xml file.
	 *
	 * @param initXmlFile
	 *            the initXmlFile to set
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void setInitXmlFiles(List<Resource> initXmlFile) throws IOException {
		getInitXmlFiles().clear();
		for (int i = 0; i < initXmlFile.size(); i++) {

			this.getInitXmlFiles().add(initXmlFile.get(i).getInputStream());

		}
	}

	/**
	 * Initialize.
	 *
	 * @param context
	 *            the context
	 * @param dataSource
	 *            the data source
	 * @throws DatabaseUnitException
	 *             the database unit exception
	 * @throws SQLException
	 *             the SQL exception
	 * @throws MalformedURLException
	 *             the malformed url exception
	 */
	public void initializeGlobalDataFile(ApplicationContext context)
			throws DatabaseUnitException, SQLException, MalformedURLException {
		DataSource datas = GlobalUtils.findDataSourceBean(context);
		IDatabaseConnection con = new DatabaseConnection(datas.getConnection()); // Create
																					// DBUnit
																					// Database
																					// connection
		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
		builder.setColumnSensing(true);

		datasets = new IDataSet[] { builder.build(singleInitXmlFile) };
		DatabaseOperation.REFRESH.execute(con, new CompositeDataSet(datasets)); // Import
																				// your
																				// data

		// TODO handle the empty data file case.
		con.close();

	}

	/**
	 * Initialize.
	 *
	 * @param context
	 *            the context
	 * @throws DatabaseUnitException
	 *             the database unit exception
	 * @throws SQLException
	 *             the SQL exception
	 * @throws MalformedURLException
	 *             the malformed url exception
	 */
	public void initialize(ApplicationContext context)
			throws DatabaseUnitException, SQLException, MalformedURLException {
		if (!getInitXmlFiles().isEmpty()) {
			DataSource datas = GlobalUtils.findDataSourceBean(context);
			IDatabaseConnection con = new DatabaseConnection(
					datas.getConnection()); // Create
											// DBUnit
											// Database
											// connection
			FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
			builder.setColumnSensing(true);

			DatabaseOperation.CLEAN_INSERT.execute(con,
					builder.build(combineInitXmlFiles())); // Import your data

			//
			//
			// if (!getInitXmlFiles().isEmpty()) {
			// datasets = new IDataSet[getInitXmlFiles().size()];
			// for (int i = 0; i < getInitXmlFiles().size(); i++) {
			// datasets[i] = builder.build(getInitXmlFiles().get(i));
			// }
			// DatabaseOperation.CLEAN_INSERT.execute(con, new CompositeDataSet(
			// datasets)); // Import your data
			// }
			// TODO handle the empty data file case.
			con.close();
		}

	}

	/**
	 * Combine init xml files.
	 *
	 * @return the input stream
	 */
	private InputStream combineInitXmlFiles() {
		if (getInitXmlFiles().isEmpty()) {
			throw GlobalUtils
					.createNotInitializedException("xml data files are not populated");
		} else {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setValidating(false);
			DocumentBuilder dbuilder;
			try {
				dbuilder = dbf.newDocumentBuilder();

				Document retDoc = dbuilder.newDocument();
				Document doc0;

				doc0 = dbuilder.parse(getInitXmlFiles().get(0));

				Node firstDataset = retDoc.importNode(doc0.getFirstChild(),
						true);
				retDoc.appendChild(firstDataset);
				for (int i = 1; i < getInitXmlFiles().size(); i++) {
					Document doc2 = dbuilder.parse(getInitXmlFiles().get(i));
					Node root = doc2.getFirstChild();
					NodeList list = root.getChildNodes();
					for (int index = 0; index < list.getLength(); index++) {
						Node copiedNode = retDoc.importNode(list.item(index),
								true);
						retDoc.getDocumentElement().appendChild(copiedNode);
					}
				}

				DOMSource source = new DOMSource(retDoc);
				StringWriter xmlAsWriter = new StringWriter();

				StreamResult result = new StreamResult(xmlAsWriter);

				TransformerFactory.newInstance().newTransformer()
						.transform(source, result);

				// write changes
				return new ByteArrayInputStream(xmlAsWriter.toString()
						.getBytes("UTF-8"));

			} catch (SAXException | IOException | ParserConfigurationException e) {
				throw GlobalUtils.createNotInitializedException(
						"xml data files are not correctly populated", e);
			} catch (TransformerException
					| TransformerFactoryConfigurationError transE) {
				throw GlobalUtils.createInternalError("xml transformer error!",
						transE);
			}

		}
	}

	/**
	 * Initialize.
	 *
	 * @param beanFac
	 *            the bean factory
	 * @throws DatabaseUnitException
	 *             the database unit exception
	 * @throws SQLException
	 *             the SQL exception
	 * @throws MalformedURLException
	 *             the malformed url exception
	 */
	public void initialize(BeanFactory beanFac) throws DatabaseUnitException,
			SQLException, MalformedURLException {
		if (!getInitXmlFiles().isEmpty()) {
			DataSource datas = GlobalUtils.findDataSourceBean(beanFac);
			IDatabaseConnection con = new DatabaseConnection(
					datas.getConnection()); // Create
											// DBUnit
											// Database
											// connection
			FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
			builder.setColumnSensing(true);

			DatabaseOperation.CLEAN_INSERT.execute(con,
					builder.build(combineInitXmlFiles())); // Import your data
			// datasets = new IDataSet[getInitXmlFiles().size()];
			// for (int i = 0; i < getInitXmlFiles().size(); i++) {
			// datasets[i] = builder.build(getInitXmlFiles().get(i));
			// }
			// DatabaseOperation.CLEAN_INSERT.execute(con, new CompositeDataSet(
			// datasets, false)); // Import your data

			// TODO handle the empty data file case.
			con.close();
		}

	}

	/**
	 * @return the singleInitXmlFile
	 */
	@Nullable
	public InputStream getSingleInitXmlFile() {
		return singleInitXmlFile;
	}

	/**
	 * @param singleInitXmlFile
	 *            the singleInitXmlFile to set
	 * @throws IOException
	 */
	public void setSingleInitXmlFile(Resource singleInitXmlFile)
			throws IOException {
		this.singleInitXmlFile = singleInitXmlFile.getInputStream();
	}

	/**
	 * @return the datasets
	 */
	public IDataSet[] getDatasets() {
		return datasets;
	}

	/**
	 * @param datasets the datasets to set
	 */
	public void setDatasets(IDataSet[] datasets) {
		this.datasets = datasets;
	}

	/**
	 * @param singleInitXmlFile the singleInitXmlFile to set
	 */
	public void setSingleInitXmlFile(InputStream singleInitXmlFile) {
		this.singleInitXmlFile = singleInitXmlFile;
	}

}
