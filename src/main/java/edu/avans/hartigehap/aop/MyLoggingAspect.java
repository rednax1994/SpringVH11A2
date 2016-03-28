package edu.avans.hartigehap.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import edu.avans.hartigehap.domain.exception.MyRuntimeException;

@Component
@Aspect
public class MyLoggingAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyLoggingAspect.class);
    
    @Pointcut("execution(* edu.avans.hartigehap..*(..))")
    public void anyHartigeHapMethod() {
        // Comment just for fun.
    }
    
    @Before("anyHartigeHapMethod()")
    public void loggingBeforeAdvice(JoinPoint joinPoint) {
        LOGGER.info("(AOP-myLogger) Executing: " + joinPoint.getSignature().getDeclaringTypeName() + "."
                + joinPoint.getSignature().getName());
    }
    
    @Around("anyHartigeHapMethod()")
    public Object loggingAroundAdvice(ProceedingJoinPoint pjp) throws MyRuntimeException {
        LOGGER.info("(AOP-myLogger) Before execution: " + pjp.getSignature().getDeclaringTypeName() + "."
                + pjp.getSignature().getName());
        Object retVal = null;
        try {
            retVal = pjp.proceed();
            LOGGER.info("(AOP-myLogger) After execution: " + pjp.getSignature().getDeclaringTypeName() + "."
                    + pjp.getSignature().getName());
        } catch (Throwable e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return retVal;
    }
}
