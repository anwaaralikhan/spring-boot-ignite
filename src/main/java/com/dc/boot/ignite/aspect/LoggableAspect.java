package com.dc.boot.ignite.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class LoggableAspect {

	private static Logger LOG = LoggerFactory.getLogger(LoggableAspect.class);
	
	@Around("@annotation(com.dc.boot.ignite.annotation.Loggable)")
	private Object time(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		LOG.info("###########################################################################################");
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		Object value = proceedingJoinPoint.proceed();
		stopWatch.stop();
		final String methodName = proceedingJoinPoint.getSignature().getName();
		final String className = proceedingJoinPoint.getSignature().getClass().getSimpleName();
		final long totalTimeInMillis = stopWatch.getTotalTimeMillis();
		LOG.info("########### Class :: {} Method :: {} execution time :: {} ########### " , className, methodName, totalTimeInMillis);
		LOG.info("###########################################################################################");
		return value;
	}
}
