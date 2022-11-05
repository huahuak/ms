package top.moflowerlkh.web.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import top.moflowerlkh.web.dto.RequestDTO;

@Slf4j
@Aspect
@Component
public class ControllerLoggerAOP implements Ordered {
    @Pointcut("execution(* top.moflowerlkh.web.controller.*.*(..))")
    public void controller() {
    }

    @Before("controller() && args(requestDTO)")
    public void logReq(RequestDTO requestDTO) {
//        log.info("\n" +
//                "requestID is -> " + requestDTO.getRequestID() + "\n" +
//                "req is -> " + requestDTO.toString());
    }



    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
