package top.moflowerlkh.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.moflowerlkh.msg.common.properties.Consts;
import top.moflowerlkh.msg.service.RedisService;

@RestController
public class StatusController {
    @Autowired
    private RedisService redisService;

    @GetMapping("/status")
    public String status() {
        return "ok";
    }

    @GetMapping("/response")
    public String keys() {
        return String.valueOf(redisService.keys(Consts.RESPONSE_KEY + ".*").size());
    }
}

