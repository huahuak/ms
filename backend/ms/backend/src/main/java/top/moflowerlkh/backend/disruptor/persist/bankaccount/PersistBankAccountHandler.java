package top.moflowerlkh.backend.disruptor.persist.bankaccount;

import com.lmax.disruptor.EventHandler;
import lombok.Setter;
import top.moflowerlkh.backend.disruptor.persist.PersistEvent;
import top.moflowerlkh.msg.dao.po.acount.BankOperationRecord;

@Setter
public class PersistBankAccountHandler implements EventHandler<PersistEvent<BankOperationRecord>> {
    private BankOperationRecordBuffer bankOperationRecordBuffer;

    @Override
    public void onEvent(PersistEvent<BankOperationRecord> event, long sequence, boolean endOfBatch) throws Exception {
        bankOperationRecordBuffer.save(event.getArg());
    }
}
