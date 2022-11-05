package top.moflowerlkh.msg.dao.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import top.moflowerlkh.msg.dao.po.acount.BankOperationRecord;

public interface BankOperationRecordRepository extends JpaRepository<BankOperationRecord, Long> {
}