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
package org.bigtester.ate.model.page.atewebdriver;
import java.io.File;
import java.io.PrintStream;
import java.util.Iterator;

import org.bigtester.ate.GlobalUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.eclipse.jdt.annotation.Nullable;

// TODO: Auto-generated Javadoc
/**
 * This class ReadXmlFile defines ....
 * @author Jun Yang
 *
 */
public final class ReadXmlFile {
	
    /** The Constant REPOFILENAME. */
    @Nullable   
	/** The Constant REPOFILENAME. */
	public static final String REPOFILENAME = "browserdriver/repo.xml";
	/** The Constant REPOFILENAME. */
	public static final String VERSION = "version";
	
	/** The Constant ONE. */
	public final static int ONE = 1;
	
	private ReadXmlFile() {};
	
    /**
    * {@inheritDoc}
    */
	public static @Nullable String parserXml(String...args) {
        SAXReader saxReader = new SAXReader();
        File inputXml = new File(args[0]);
        Element nextLevelNode;
        String nodeValue = null;   //NOPMD
        
		try {
            Document document = saxReader.read(inputXml);
            Element root = document.getRootElement();
            int argsNum = 1;
            Element currentLevelNode = root;
            while (argsNum < args.length) {
                if (currentLevelNode == null) throw GlobalUtils.createNotInitializedException("repo.xml is not correct,cannot find node");
                else {
                	nodeValue = args[argsNum];
                	if (argsNum == ONE) nextLevelNode = getNodeByName(currentLevelNode,nodeValue);
                    else nextLevelNode = getNodeByValue(currentLevelNode,nodeValue);
                    argsNum++;
                    currentLevelNode = nextLevelNode;
                }
            }    
            if (null == currentLevelNode) throw GlobalUtils.createNotInitializedException("repo.xml is not correct ");
            else nodeValue = getNodeValue(currentLevelNode);
        } 
        catch (DocumentException e) {
            PrintStream out=System.out;
            out.println(e.getMessage());
        }
		return nodeValue;
    }   
     
    /**
     * Gets the node by value.
     *
     * @param fatherNode the father node
     * @param value the value
     * @return the node by value
     */
    public static @Nullable Element getNodeByValue(Element fatherNode, @Nullable String value) {
    	Element node = null;  //NOPMD
        String nodeValue;
        
   	    for (Iterator<?> i = fatherNode.elementIterator(); i.hasNext();) {
             node = (Element) i.next();
             if (null == node) throw GlobalUtils.createNotInitializedException("repo.xml is not correct, cannot get node by value ");
             else nodeValue = getNodeValue(node);
             if (nodeValue.equals(value)) break;
   	    }    
   	    return node;
    }
    
    /**
     * Gets the node by name.
     *
     * @param fatherNode the father node
     * @param value the value
     * @return the node by name
     */
    public static @Nullable Element getNodeByName(Element fatherNode, @Nullable String value) {
   	    Element node = null;  //NOPMD
   	    
   	    for (Iterator<?> i = fatherNode.elementIterator(); i.hasNext();) {
   	    	node = (Element) i.next();
   	        if (node == null) throw GlobalUtils.createNotInitializedException("repo.xml is not correct, cannot get node by name");
   	        else if (node.getName().equals(value)) break;
   	    }
   	    return node;
    }
    
    /**
     * Gets the node value.
     *
     * @param node the node
     * @return the node value
     */
    public static String getNodeValue(Element node) {
    	 
   	    Attribute attri = node.attribute("id");
        String nodeValue = attri.getValue();
        if (null == nodeValue) throw GlobalUtils.createNotInitializedException("repo.xml is not correct,cannot get node value"); 
    	return nodeValue;
    }
}


