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
package org.bigtester.ate.annotation;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.type.TypeMirror;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.annotation.RepeatStepRefreshable.RefreshDataType;
import org.bigtester.ate.model.data.IOnTheFlyData;
import org.eclipse.jdt.annotation.Nullable;
import org.aspectj.lang.Signature;
import org.aspectj.lang.SoftException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

// TODO: Auto-generated Javadoc
/**
 * This class RepeatStepDataRegistration defines ....
 * @author Peidong Hu
 *
 */
@Aspect
public class RepeatStepDataRegistration  {

	@After(value ="set(@RepeatStepRefreshable * *)")
	public boolean process(JoinPoint jPoint) {
		if (getDataType(jPoint) == RefreshDataType.ONTHEFLY) {
			Signature sig = jPoint.getSignature();
			Object targ = jPoint.getTarget();
			if (null == sig || null == targ) throw GlobalUtils.createInternalError("RepeatStepRefreshable pointcut error");
			IOnTheFlyData<?> data =  (IOnTheFlyData<?>) getFieldValue(sig, targ);
		}
		return true;
	}
	private @Nullable Object getFieldValue(Signature signature, Object target) {
        try {
            Field field = signature.getDeclaringType().getDeclaredField(signature.getName());
            field.setAccessible(true);
            return field.get(target);
        }
        catch (Exception e) { throw new SoftException(e); }
    } 

	private RefreshDataType getDataType(JoinPoint thisJoinPoint)
    {
        MethodSignature methodSignature = (MethodSignature) thisJoinPoint.getSignature();
        Method targetMethod = methodSignature.getMethod();

        return ((RepeatStepRefreshable) targetMethod.getAnnotation(RepeatStepRefreshable.class)).dataType();
    }
}
