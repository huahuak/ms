package top.moflowerlkh.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import top.moflowerlkh.web.vo.UIResult;

import java.util.Arrays;

@Slf4j
@RestControllerAdvice
public class ErrController {
    @ExceptionHandler(Exception.class)
    public UIResult exceptionHandler(Exception e, WebRequest req) {
        log.warn(Arrays.toString(e.getStackTrace()));
        e.printStackTrace();
        log.warn("exception -> " + e.toString());
        return UIResult.failed("exception -> " + e.toString());
    }
}
