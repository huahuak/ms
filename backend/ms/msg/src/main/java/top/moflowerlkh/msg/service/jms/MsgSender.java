package top.moflowerlkh.msg.service.jms;

import top.moflowerlkh.msg.dto.RequestMsgDTO;

public interface MsgSender {
    void sendMsg(RequestMsgDTO req);
}
