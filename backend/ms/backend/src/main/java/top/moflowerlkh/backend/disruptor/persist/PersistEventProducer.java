package top.moflowerlkh.backend.disruptor.persist;

import com.lmax.disruptor.RingBuffer;
import top.moflowerlkh.backend.disruptor.EventProducer;
import top.moflowerlkh.msg.dao.po.BasePO;

public abstract class PersistEventProducer<T extends BasePO> extends EventProducer<PersistEvent<T>, T> {
    public PersistEventProducer(RingBuffer<PersistEvent<T>> ringBuffer) {
        super(ringBuffer);
    }

    public abstract Class<T> getTargetClass();

}
