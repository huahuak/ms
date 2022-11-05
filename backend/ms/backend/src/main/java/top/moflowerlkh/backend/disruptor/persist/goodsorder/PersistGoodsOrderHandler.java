package top.moflowerlkh.backend.disruptor.persist.goodsorder;


import com.lmax.disruptor.EventHandler;
import lombok.Setter;
import top.moflowerlkh.backend.disruptor.persist.PersistEvent;
import top.moflowerlkh.msg.dao.po.goods.GoodsOrder;

@Setter
public class PersistGoodsOrderHandler implements EventHandler<PersistEvent<GoodsOrder>> {
    private GoodsOrderBuffer goodsOrderBuffer;

    @Override
    public void onEvent(PersistEvent<GoodsOrder> event, long sequence, boolean endOfBatch) throws Exception {
        goodsOrderBuffer.save(event.getArg());
    }
}
