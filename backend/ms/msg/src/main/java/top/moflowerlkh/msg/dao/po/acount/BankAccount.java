package top.moflowerlkh.msg.dao.po.acount;

import lombok.*;
import org.springframework.web.bind.annotation.RestController;
import top.moflowerlkh.msg.dao.po.BasePO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Getter
@Setter
@Entity
@ToString
@Table(name = "bank_account")
public class BankAccount extends BasePO {

    /**
     * 银行账号标识，要求全球唯一。
     */
    @Column(name = "bank_account_sn", unique = true, nullable = false)
    private long accountSN;

    /**
     * 用户
     * user_id is relative to user_tb
     */
    @Column(name = "user_id", nullable = false)
    private long userID;

    /**
     * 银行余额
     */
    @Column(name = "balance", nullable = false)
    private long balance;

    @Transient
    public final ReentrantReadWriteLock rwMutex = new ReentrantReadWriteLock();

    public static BankAccount bySN(long bankAccountSN) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountSN(bankAccountSN);
        return bankAccount;
    }

}