package top.moflowerlkh.backend.disruptor.ms;

import com.lmax.disruptor.EventFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Component;
import top.moflowerlkh.backend.cache.UserOrderRecordCache;
import top.moflowerlkh.backend.cache.impl.BankAccountCache;
import top.moflowerlkh.backend.cache.impl.GoodsCache;
import top.moflowerlkh.backend.entity.enums.GoodsOrderStatusEnum;

@Component
public class MsEventFactory implements EventFactory<MsEvent> {
    private final GoodsCache goodsCache;

    private final BankAccountCache bankAccountCache;

    private final UserOrderRecordCache userOrderRecordCache;

    public MsEventFactory(GoodsCache goodsCache, BankAccountCache bankAccountCache, UserOrderRecordCache userOrderRecordCache) {
        this.goodsCache = goodsCache;
        this.bankAccountCache = bankAccountCache;
        this.userOrderRecordCache = userOrderRecordCache;
    }

    @Override
    public MsEvent newInstance() {
        MsEvent goodsOrderEvent = new MsEvent();
        goodsOrderEvent.setGoodsCache(goodsCache);
        goodsOrderEvent.setBankAccountCache(bankAccountCache);
        goodsOrderEvent.setUserOrderRecordCache(userOrderRecordCache);
        goodsOrderEvent.setNowGoodsOrderStatus(GoodsOrderStatusEnum.GOODS_ORDER_START);
        return goodsOrderEvent;
    }
}
