package top.moflowerlkh.backend.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.moflowerlkh.backend.cache.impl.BankAccountCache;
import top.moflowerlkh.backend.cache.impl.GoodsCache;
import top.moflowerlkh.msg.dao.po.acount.BankAccount;
import top.moflowerlkh.msg.dao.po.goods.Goods;
import top.moflowerlkh.msg.dto.RequestMsgDTO;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class UserOrderRecordCache {
    // fixme memory leak need time to release
    private static Map<Long, Integer> record = new HashMap<>();

    @Autowired
    private GoodsCache goodsCache;

    @Autowired
    private BankAccountCache bankAccountCache;

    // -- order query -- //
    @Trace
    public boolean checkOrderOK(RequestMsgDTO req) {
        Optional<Goods> goods = goodsCache.find(req.getGoodsID());
        if (!goods.isPresent()) {
            return false;
        }
        Optional<BankAccount> bankAccount = bankAccountCache.find(req.getAccountID());
        if (!bankAccount.isPresent()) {
            return false;
        }

        // goods order exists
        long goodsOneMaxAmount = goods.get().getOneMaxAmount();
        // user goods amount means user has got the amount of goods
        long wantAmount = Optional.ofNullable(record.get(bankAccount.get().getUserID()))
                .orElse(0) + req.getGoodsOrderAmount();
        if (wantAmount > goodsOneMaxAmount) {
            log.warn("request id is {}, goods one max amount is {}, user goods amount history is {}, now want {}!",
                    req.getRequestID(), goodsOneMaxAmount,
                    wantAmount - req.getGoodsOrderAmount(), req.getGoodsOrderAmount());
            return false;
        }

        record.put(bankAccount.get().getUserID(), (int) wantAmount);
        return true;
    }

    public void rollBackRecordOrder(RequestMsgDTO req) {
        long key = bankAccountCache.find(req.getAccountID()).get().getUserID();
        record.put(key, (int) (record.get(key) - req.getGoodsOrderAmount()));
    }
}
