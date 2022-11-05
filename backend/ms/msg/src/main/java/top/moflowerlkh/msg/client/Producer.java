package top.moflowerlkh.msg.client;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.moflowerlkh.msg.dto.RequestMsgDTO;
import top.moflowerlkh.msg.dto.ResponseMsgDTO;
import top.moflowerlkh.msg.dto.enums.ProcessStatusEnum;
import top.moflowerlkh.msg.service.jms.MsgSender;


@Slf4j
@Builder
@Service
public class Producer {
    @Autowired
    private MsgSender msgSender;

    // -- order command -- //

    public ResponseMsgDTO orderCommand(RequestMsgDTO req) {
        if (!validateReqOK(req)) {
            return ResponseMsgDTO.builder()
                    .status(ProcessStatusEnum.FILED_ILLEGAL)
                    .build();
        }

        try {
            msgSender.sendMsg(req);
        } catch (Exception e) {
            return ResponseMsgDTO.builder()
                    .status(ProcessStatusEnum.FAILED)
                    .build();
        }

        return ResponseMsgDTO.builder()
                .status(ProcessStatusEnum.SUCCESS)
                .build();
    }

    private boolean validateReqOK(RequestMsgDTO req) {
        return !StringUtils.isBlank(req.getRequestID());
    }
}
