package top.moflowerlkh.backend.disruptor.persist.bankaccount;

import com.lmax.disruptor.RingBuffer;
import top.moflowerlkh.backend.disruptor.persist.PersistEvent;
import top.moflowerlkh.backend.disruptor.persist.PersistEventProducer;
import top.moflowerlkh.msg.dao.po.acount.BankOperationRecord;

public class PersistBankAccountEventProducer extends PersistEventProducer<BankOperationRecord> {
    public PersistBankAccountEventProducer(RingBuffer<PersistEvent<BankOperationRecord>> ringBuffer) {
        super(ringBuffer);
    }

    @Override
    public Class<BankOperationRecord> getTargetClass() {
        return BankOperationRecord.class;
    }
}
