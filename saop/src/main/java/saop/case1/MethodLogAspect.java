package saop.case1;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MethodLogAspect {

    private Logger logger = LoggerFactory.getLogger(MethodLogAspect.class);

    @Pointcut(value = "@annotation(saop.case1.MethodLog)")
    private void pointcut() {
    }

    @Before(value = "pointcut()")
    public Object doBefore(JoinPoint joinPoint) throws Throwable{
        logger.info("do something before method execute");
        return 0;
    }
}
