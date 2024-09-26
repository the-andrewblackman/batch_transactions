package com.demo.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("aopConfig")
@Aspect
public class AopConfig {

	private Logger LOGGER = LoggerFactory.getLogger("aLogger");

	@Pointcut(value = "within(com.tmo.controller..*)")
	public void targetControllerMethod() {
	};

	@Before(value = "targetControllerMethod()")
	public void logBeforeInvokeController(JoinPoint jp) {
		this.LOGGER.debug(jp.getSignature().getName() + "() in the " + jp.getSignature().getDeclaringType()
				+ " is going to be invoked");
	}

	@After(value = "targetControllerMethod()")
	public void logAfterInvokeController(JoinPoint jp) {
		this.LOGGER.debug(
				jp.getSignature().getName() + "() in the " + jp.getSignature().getDeclaringType() + " was invoked");
	}

	@AfterThrowing(value = "targetControllerMethod()", throwing = "exception")
	public void afterThrowing(Exception exception) throws Exception {
		LOGGER.error(exception.getMessage(), exception);
	}


}
