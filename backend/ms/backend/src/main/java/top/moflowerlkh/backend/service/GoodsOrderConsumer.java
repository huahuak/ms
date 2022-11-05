package top.moflowerlkh.backend.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.moflowerlkh.backend.disruptor.ms.MsEventProducer;
import top.moflowerlkh.msg.dto.RequestMsgDTO;

@Slf4j
@Service
@RocketMQMessageListener(
        topic = "${custom.rocketmq.topic.orders}",
        consumerGroup = "${custom.rocketmq.group.consumer}"
)
public class GoodsOrderConsumer implements RocketMQListener<RequestMsgDTO> {

    @Autowired
    private MsEventProducer goodsOrderEventProducer;

    public void onMessage(RequestMsgDTO requestMsgDTO) {
        goodsOrderEventProducer.publishEvent(requestMsgDTO);
    }

}