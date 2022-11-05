package top.moflowerlkh.backend.disruptor.persist.goodsorder;

import com.lmax.disruptor.RingBuffer;
import top.moflowerlkh.backend.disruptor.persist.PersistEvent;
import top.moflowerlkh.backend.disruptor.persist.PersistEventProducer;
import top.moflowerlkh.msg.dao.po.goods.GoodsOrder;

public class PersistGoodsOrderEventProducer extends PersistEventProducer<GoodsOrder> {
    public PersistGoodsOrderEventProducer(RingBuffer<PersistEvent<GoodsOrder>> ringBuffer) {
        super(ringBuffer);
    }

    @Override
    public Class<GoodsOrder> getTargetClass() {
        return GoodsOrder.class;
    }
}
