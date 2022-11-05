package top.moflowerlkh.backend.disruptor.ms;

import com.lmax.disruptor.RingBuffer;
import top.moflowerlkh.backend.disruptor.EventProducer;
import top.moflowerlkh.msg.dto.RequestMsgDTO;

public class MsEventProducer extends EventProducer<MsEvent, RequestMsgDTO> {
    public MsEventProducer(RingBuffer<MsEvent> ringBuffer) {
        super(ringBuffer);
    }
}
