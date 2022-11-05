package top.moflowerlkh.backend.disruptor.persist;

import com.lmax.disruptor.EventHandler;
import top.moflowerlkh.msg.dao.po.BasePO;

public class PersistGcHandler<T extends BasePO> implements EventHandler<PersistEvent<T>> {
    @Override
    public void onEvent(PersistEvent<T> event, long sequence, boolean endOfBatch) throws Exception {
        event.setArg(null);
    }
}
