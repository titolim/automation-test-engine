package org.bigtester.ate.xmlschema;


import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.constant.XsdElementConstants;
import org.bigtester.ate.model.page.elementfind.ElementFindByName;
import org.eclipse.jdt.annotation.Nullable;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;


// TODO: Auto-generated Javadoc
/**
 * This class SimpleDateFormatBeanDefinitionParser defines ....
 * 
 * @author Peidong Hu
 *
 */
public class FindByNameBeanDefinitionParser extends
		AbstractBeanDefinitionParser {

	

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected @Nullable AbstractBeanDefinition parseInternal(@Nullable Element element,
			@Nullable ParserContext parserContext) {
		// Here we parse the Spring elements such as < property>
		if (parserContext==null || element == null ) throw GlobalUtils.createNotInitializedException("element and parserContext");
		// this will never be null since the schema explicitly requires that a value be supplied
        String findbyValue = element.getAttribute(XsdElementConstants.ATTR_ELEMENTFINDBYID_FINDBYVALUE);
        BeanDefinitionBuilder factory = BeanDefinitionBuilder.rootBeanDefinition(ElementFindByName.class);
        if (StringUtils.hasText(findbyValue))
        	factory.addConstructorArgValue(findbyValue);
        
        return factory.getBeanDefinition();
	}
	
	
}
