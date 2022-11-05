package top.moflowerlkh.web.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.moflowerlkh.msg.client.Producer;
import top.moflowerlkh.msg.dto.RequestMsgDTO;
import top.moflowerlkh.msg.dto.ResponseMsgDTO;
import top.moflowerlkh.msg.service.ResponseService;
import top.moflowerlkh.web.dto.RequestDTO;

@Service
@Slf4j
public class OrderCommandService {
    @Autowired
    private Producer producer;

    @Autowired
    private ResponseService responseService;

    @Trace
    public String goodsOrderCommand(RequestDTO req) {
        RequestMsgDTO requestMsgDTO = req.requestMsgDTO();
        ResponseMsgDTO responseDTO = producer.orderCommand(requestMsgDTO);
        return responseDTO.toJson();
    }

    public String getResponse(RequestDTO req) {
        return responseService.get(req.getRequestID());
    }
}
