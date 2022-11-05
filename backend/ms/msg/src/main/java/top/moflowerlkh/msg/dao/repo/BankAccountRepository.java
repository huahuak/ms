package top.moflowerlkh.msg.dao.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import top.moflowerlkh.msg.dao.po.acount.BankAccount;

import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    Optional<BankAccount> findByAccountSN(long sn);
}