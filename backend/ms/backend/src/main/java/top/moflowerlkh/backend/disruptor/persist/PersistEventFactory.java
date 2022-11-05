package top.moflowerlkh.backend.disruptor.persist;

import com.lmax.disruptor.EventFactory;
import top.moflowerlkh.msg.dao.po.BasePO;

public class PersistEventFactory<T extends BasePO> implements EventFactory<PersistEvent<T>> {
    @Override
    public PersistEvent<T> newInstance() {
        return new PersistEvent<T>();
    }
}
