package acs.aop;

import java.lang.reflect.Method;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PerformanceAspect {
	private Log logger;
	
	@PostConstruct
	public void init() {
		this.logger = LogFactory.getLog(PerformanceAspect.class);
	}
	
	@Around("@annotation(acs.aop.PerformanceElapsedMonitor)")
	public Object elapsedTimeAdvice(ProceedingJoinPoint joinPoint) throws Throwable{
		// pre processing - measure current time
		long initialTime = System.nanoTime();
				//System.currentTimeMillis(); // 1/1/1970 
				
		try {
			// invoke actual method
			return joinPoint.proceed();
		} finally {	
			// post processing - measure current time and subtract initial time
			long finalTime = System.nanoTime(); 
					//System.currentTimeMillis(); // 1/1/1970
			long elapsedTimeInNano = finalTime - initialTime;
			long elapsedTimeInMilli = elapsedTimeInNano / 1000000L;
			long elapsedTimeInSeconds = elapsedTimeInMilli / 1000L;

			// print elapsed time to log
			MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
			Method theMethod = methodSignature.getMethod(); // Java Reflection
			PerformanceElapsedMonitor monitor = theMethod.getAnnotation(PerformanceElapsedMonitor.class);
			PerformnaceUnit units = monitor.units();
			
			switch (units) {
			case ns:
				this.logger.debug("------------> elapsed time: " + elapsedTimeInNano + "ns");
				break;

			case s:
				this.logger.debug("------------> elapsed time: " + elapsedTimeInSeconds + "s");
				break;
				
			case ms:
			default:
				this.logger.debug("------------> elapsed time: " + elapsedTimeInMilli + "ms");
				break;
			}
		}
	}
}
