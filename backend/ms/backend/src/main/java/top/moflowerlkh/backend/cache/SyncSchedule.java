package top.moflowerlkh.backend.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import top.moflowerlkh.backend.cache.impl.BankAccountCache;
import top.moflowerlkh.backend.cache.impl.GoodsCache;
import top.moflowerlkh.backend.disruptor.persist.bankaccount.BankOperationRecordBuffer;
import top.moflowerlkh.backend.disruptor.persist.goodsorder.GoodsOrderBuffer;

@Slf4j
@EnableScheduling
@Configuration
public class SyncSchedule {

    @Autowired
    private GoodsCache goodsCache;

    @Autowired
    private BankAccountCache bankAccountCache;

    @Autowired
    private GoodsOrderBuffer goodsOrderBuffer;

    @Autowired
    private BankOperationRecordBuffer bankOperationRecordBuffer;

    @Scheduled(fixedDelay = 5000, initialDelay = 1000)
    public void goodsSync() {
        goodsCache.sync();
    }

    @Scheduled(fixedDelay = 5000, initialDelay = 2000)
    public void bankAccountSync() {
        bankAccountCache.sync();
    }

    @Scheduled(fixedDelay = 10000, initialDelay = 3000)
    public void goodsOrderSync() {
        goodsOrderBuffer.executeAndFlush();
    }

    @Scheduled(fixedDelay = 10000, initialDelay = 4000)
    public void bankOperationRecordSync() {
        bankOperationRecordBuffer.executeAndFlush();
    }

}
