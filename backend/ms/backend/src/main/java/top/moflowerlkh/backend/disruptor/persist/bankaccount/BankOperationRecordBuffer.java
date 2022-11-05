package top.moflowerlkh.backend.disruptor.persist.bankaccount;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import top.moflowerlkh.backend.disruptor.persist.buffer.SQLCommandBuffer;
import top.moflowerlkh.msg.dao.po.acount.BankOperationRecord;

import java.util.Date;

@Slf4j
@Component
public class BankOperationRecordBuffer extends SQLCommandBuffer<BankOperationRecord> {
    /**
     * create table bank_op_record
     * (
     * id              bigint       not null *         primary key,
     * create_time     datetime(6)  not null,
     * modify_time     datetime(6)  not null,
     * bank_account_sn bigint       not null,
     * op_type         varchar(255) not null,
     * before_num      bigint       not null,
     * op_num          bigint       not null,
     * op_result_num   bigint       not null
     * );
     */
    private static final String SQLStr = "insert into bank_op_record (" +
            "id, create_time, modify_time, bank_account_sn, op_type, before_num, op_num, op_result_num) " +
            "values (null, ?, ?, ?, ?, ?, ?, ?)";

    // todo 500 property
    public BankOperationRecordBuffer(JdbcTemplate jdbcTemplate, @Value("1000") int capacity) {
        super(jdbcTemplate, capacity);
    }

    @Override
    public void convertAndStore(BankOperationRecord bankOperationRecord) {
        Date date = new Date();
        argsBuffer.add(new Object[]{
                date,
                date,
                bankOperationRecord.getBankAccountSN(),
                bankOperationRecord.getBankOperationType().name(),
                bankOperationRecord.getBeforeOperationNumber(),
                bankOperationRecord.getOperationNumer(),
                bankOperationRecord.getOperationResultNumber()
        });
    }

    @Override
    synchronized public void executeAndFlush() {
        try {
            mutex.lock();
            if (argsBuffer.size() > 0) {
                log.info("update bank operation record -> {}", argsBuffer.size());
            }
            jdbcTemplate.batchUpdate(SQLStr, argsBuffer);
            argsBuffer.clear();
        } finally {
            mutex.unlock();
        }
    }
}
