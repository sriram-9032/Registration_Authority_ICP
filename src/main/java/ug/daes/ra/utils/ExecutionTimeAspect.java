package ug.daes.ra.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeAspect {
	
	private static Logger logger = LoggerFactory.getLogger(ExecutionTimeAspect.class);

	/** The Constant CLASS. */
    private static final String CLASS = "ExecutionTimeAspect";

    @Around("execution(* ug.daes.ra..*(..))")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    	
    	String methodName = joinPoint.getSignature().toShortString();
    	
        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        logger.info("Executing method: {} Execution time: {}ms", methodName, executionTime);

        return result;
    }

}
