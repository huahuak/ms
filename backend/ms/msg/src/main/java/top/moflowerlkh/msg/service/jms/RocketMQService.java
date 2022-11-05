package top.moflowerlkh.msg.service.jms;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.moflowerlkh.msg.common.properties.RocketMQTopicConst;
import top.moflowerlkh.msg.dto.RequestMsgDTO;

@Slf4j
@Service
public class RocketMQService implements MsgSender {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private RocketMQTopicConst rocketMQTopicConst;

    public void sendMsg(RequestMsgDTO req) {
        rocketMQTemplate.convertAndSend(rocketMQTopicConst.getOrders(), req);
    }
}
