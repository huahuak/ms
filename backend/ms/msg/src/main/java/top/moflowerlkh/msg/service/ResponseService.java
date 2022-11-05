package top.moflowerlkh.msg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.moflowerlkh.msg.common.properties.Consts;
import top.moflowerlkh.msg.dao.po.goods.enumbean.OrderResultEnum;
import top.moflowerlkh.msg.dto.ResponseMsgDTO;
import top.moflowerlkh.msg.dto.enums.ProcessStatusEnum;

import static top.moflowerlkh.msg.util.genericUtil.prefixKey;

@Service
public class ResponseService {
    @Autowired
    private RedisService redisService;

    public void store(String requestID, ProcessStatusEnum msg, OrderResultEnum reason) {
        redisService.set(
                getResponseKey(requestID),
                toJson(msg, reason.getDescription())
        );
    }

    public String get(String requestID) {
        return redisService.get(getResponseKey(requestID));
    }

    private String getResponseKey(String requestID) {
        return prefixKey(Consts.RESPONSE_KEY, requestID);
    }

    public static String toJson(ProcessStatusEnum status, Object reason) {
        return ResponseMsgDTO.builder()
                .status(status)
                .reason(reason.toString())
                .build().toJson();
    }
}