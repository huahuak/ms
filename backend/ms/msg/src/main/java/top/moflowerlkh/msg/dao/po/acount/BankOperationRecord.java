package top.moflowerlkh.msg.dao.po.acount;

import lombok.Getter;
import lombok.Setter;
import top.moflowerlkh.msg.dao.po.BasePO;
import top.moflowerlkh.msg.dao.po.acount.enumbean.BankOperationTypeEnum;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "bank_op_record")
public class BankOperationRecord extends BasePO {
    /**
     * 操作账号
     */
    @Column(name = "bank_account_sn", nullable = false)
    private long bankAccountSN;

    /**
     * 操作类型
     * IN 进账
     * OUT 出账
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "op_type", nullable = false)
    private BankOperationTypeEnum bankOperationType;

    /**
     * 操作数
     * 均为非负数
     */
    @Column(name = "op_num", nullable = false)
    private long operationNumer;

    /**
     * 未操作前数
     * 原来余额，未操作余额
     */
    @Column(name = "before_num", nullable = false)
    private long beforeOperationNumber;

    /**
     * 操作结果数
     * 流水后余额
     */
    @Column(name = "op_result_num", nullable = false)
    private long operationResultNumber;

    public static BankOperationRecord IN(BankAccount bankAccount, long operationNumer) {
        BankOperationRecord re = new BankOperationRecord();
        re.setBankAccountSN(bankAccount.getAccountSN());
        re.setBankOperationType(BankOperationTypeEnum.IN);
        re.setOperationNumer(operationNumer);
        re.setBeforeOperationNumber(bankAccount.getBalance());
        re.setOperationResultNumber(bankAccount.getBalance() + operationNumer);
        return re;
    }

    public static BankOperationRecord OUT(BankAccount bankAccount, long operationNumer) {
        BankOperationRecord re = new BankOperationRecord();
        re.setBankAccountSN(bankAccount.getAccountSN());
        re.setBankOperationType(BankOperationTypeEnum.OUT);
        re.setOperationNumer(operationNumer);
        re.setBeforeOperationNumber(bankAccount.getBalance());
        re.setOperationResultNumber(bankAccount.getBalance() - operationNumer);
        return re;
    }
}
