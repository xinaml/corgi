package corgi.sso.consumer.config;

import corgi.validation.GlobalValidation;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by huanghuanlai on 16/4/23.
 */
@Component
@Aspect
public class MyAOPValidation extends GlobalValidation{

    @Pointcut("execution(* corgi..boot..*.*(..))")
    public void pointCut() {
    }

}