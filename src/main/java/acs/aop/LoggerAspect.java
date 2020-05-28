package acs.aop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggerAspect {
	
	private Log logger = LogFactory.getLog(LoggerAspect.class); 
	
	@Before("@annotation(acs.aop.LogThisMethod)")
	public void printToLog (JoinPoint joinPoint){
		String targetClassName = joinPoint.getTarget().getClass()
				.getName(); // class name with package specification
		
		String methodName = joinPoint.getSignature().getName();
		this.logger.trace("------------> " + targetClassName + "." + methodName + "()" + " invoked");
	}
	
	@Around("@annotation(acs.aop.LogThisMethod)")
	public Object printToLog (ProceedingJoinPoint joinPoint) throws Throwable {
		// pre processing.....
		String targetClassName = joinPoint.getTarget().getClass()
				.getName(); // class name with package specification
		
		String methodName = joinPoint.getSignature().getName();
		this.logger.debug("------------> " + targetClassName + "." + methodName + "() - start");
		
		// invoke action and receive it return value
		try {
			Object rv = joinPoint.proceed();
			
			// successful post processing
			this.logger.debug("------------> " + targetClassName + "." + methodName + "() - successful");
			return rv;
		} catch (Throwable e) {
			// failure post processing
			this.logger.debug("------------> " + targetClassName + "." + methodName + "() - failure");
			throw e;
		}
	}
}



