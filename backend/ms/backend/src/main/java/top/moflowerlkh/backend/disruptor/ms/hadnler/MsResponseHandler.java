package top.moflowerlkh.backend.disruptor.ms.hadnler;

import com.lmax.disruptor.EventHandler;
import lombok.Setter;
import top.moflowerlkh.backend.disruptor.ms.MsEvent;
import top.moflowerlkh.msg.service.ResponseService;

@Setter
public class MsResponseHandler implements EventHandler<MsEvent> {
    private ResponseService responseService;

    @Override
    public void onEvent(MsEvent event, long sequence, boolean endOfBatch) throws Exception {
        responseService.store(
                event.getRequestMsgDTO().getRequestID(),
                event.getProcessStatusEnum(),
                event.getOrderResultEnum()
        );
    }
}
