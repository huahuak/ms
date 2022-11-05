package top.moflowerlkh.backend.disruptor.ms.hadnler;


import com.lmax.disruptor.EventHandler;
import top.moflowerlkh.backend.disruptor.ms.MsEvent;

public class MsGcHandler implements EventHandler<MsEvent> {
    @Override
    public void onEvent(MsEvent event, long sequence, boolean endOfBatch) throws Exception {
        event.clear();
    }

}
