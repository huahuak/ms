package top.moflowerlkh.web.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import top.moflowerlkh.msg.dao.repo.GoodsRepository;
import top.moflowerlkh.msg.service.RedisService;
import top.moflowerlkh.msg.util.genericCache;
import top.moflowerlkh.msg.util.genericUtil;
import top.moflowerlkh.web.dto.RequestDTO;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static top.moflowerlkh.msg.util.genericUtil.prefixKey;
import static top.moflowerlkh.web.common.Const.*;

@Slf4j
@Aspect
@Component
public class GoodsOrderAOP implements Ordered {
    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private genericCache cacheInterface;

    @Autowired
    private RedisService redisService;

    @Value("${custom.switch.all}")
    private boolean allSwitch;

    @Value("${custom.switch.authCheck}")
    private boolean authCheck;

    @Value("${custom.switch.startTimeCheck}")
    private boolean startTimeCheck;

    @Value("${custom.switch.randomCheck}")
    private boolean randomCheck;

    @Pointcut("execution(* top.moflowerlkh.web.controller.*.GoodsOrder(..))")
    public void controller() {
    }


    @Before(value = "controller() && args(req, requestDTO, md5)", argNames = "req,requestDTO,md5")
    public void addRequestID(HttpServletRequest req, RequestDTO requestDTO, String md5) {
        String requestID = genericUtil.generateRequestID(
                Optional.of(requestDTO.getBankAccountID()).orElse(""),
                Optional.of(requestDTO.getGoodsID()).orElse(""));
        requestDTO.setRequestID(requestID);
        MDC.put(TRACE_ID, requestID);
    }


    // -- around -- //

    @Around(value = "controller() && args(req, requestDTO, randomPath)", argNames = "pjp,req,requestDTO,randomPath")
    public Object PreProcess(ProceedingJoinPoint pjp,
                             HttpServletRequest req, RequestDTO requestDTO, String randomPath) throws Throwable {
        if (allSwitch) {
            if (startTimeCheck) {
                // start time check
                // FIXME: 2022/4/15 here can't user goodsCache !
                long startTime = Timestamp.valueOf("2008-12-12 23:12:01.000000").getTime();
                if (System.currentTimeMillis() < startTime) {
                    throw new RuntimeException("please wait activity start, start time is " + new Date(startTime));
                }
            }

            if (randomCheck) {
                // check activity random url path
                randomKeyCheck(randomPath, requestDTO.getGoodsID());
            }

            if (authCheck) {
                // auth check and limit request
                authCheck(
                        Optional.ofNullable(req.getHeader(AUTH_SIGN)).orElse("NONE"),
                        requestDTO.getUserID(),
                        requestDTO.getGoodsID(),
                        requestDTO.getBankAccountID());
            }

        }
        // execute task
        return pjp.proceed();
    }

    // -- auth check -- //

    @Value("${custom.auth.salt}")
    private String AUTH_SALT;

    private void authCheck(String sign, String userID, String goodsID, String accountID) {
        //  sign md5 check
        String key = prefixKey(prefixKey(USER_SEND_REQUEST_TIME_KEY, userID), goodsID);
        String timeStamp = Optional.ofNullable(redisService.get(key)).orElse("NONE");

        log.warn("redis sign {}", genericUtil.md5(Arrays.asList(AUTH_SALT, userID, goodsID, accountID, timeStamp)));
        log.warn("sign {}", sign);
        if (!genericUtil.md5(Arrays.asList(AUTH_SALT, userID, goodsID, accountID, timeStamp)).equals(sign)) {
            throw new RuntimeException("auth check failed");
        }
        log.warn(key);
        redisService.del(key);
    }

    // -- random check -- //

    private void randomKeyCheck(String randomPath, String goodsID) {
        if (!Optional.ofNullable(redisService.get(prefixKey(MS_GOODS_RANDOM_KEY, goodsID))).orElse("NONE")
                .equals(randomPath)) {
            log.warn("random url path -> " + randomPath);
            throw new RuntimeException("url path random number check failed");
        }
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE - 1;
    }
}
