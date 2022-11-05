package top.moflowerlkh.backend.cache.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import top.moflowerlkh.backend.cache.DataCache;
import top.moflowerlkh.msg.dao.po.acount.BankAccount;
import top.moflowerlkh.msg.dao.repo.BankAccountRepository;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class BankAccountCache extends DataCache<BankAccount> {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Override
    @PostConstruct
    public void load() {
        jdbcTemplate.query("select id, balance, bank_account_sn, user_id, " +
                                "create_time, modify_time from bank_account",
                        (rs, row) -> {
                            BankAccount bankAccount = new BankAccount();
                            bankAccount.setId(rs.getLong(1));
                            bankAccount.setBalance(rs.getLong(2));
                            bankAccount.setAccountSN(rs.getLong(3));
                            bankAccount.setUserID(rs.getLong(4));
                            bankAccount.setCreateDate(rs.getDate(5));
                            bankAccount.setModifyDate(rs.getDate(6));
                            return bankAccount;
                        })
                .forEach(item -> {
                    data.put(item.getAccountSN(), item);
                });
    }

    @Override
    protected BankAccount select(long id) {
        Optional<BankAccount> bankAccount = bankAccountRepository.findByAccountSN(id);
        if (!bankAccount.isPresent()) {
            return null;
        }
        data.put(bankAccount.get().getAccountSN(), bankAccount.get());
        return data.get(id);
    }

    @Override
    public void save(BankAccount bankAccount) {
        dirty.add(bankAccount.getAccountSN());
        data.put(bankAccount.getAccountSN(), bankAccount);
    }


    /**
     * -- auto-generated definition
     * create table bank_account
     * (
     * id              bigint auto_increment  primary key,
     * create_time     datetime(6) null,
     * modify_time     datetime(6) null,
     * balance         bigint      not null,
     * bank_account_sn bigint      not null,
     * user_id         bigint      not null,
     * constraint UK_sufktt3h6rerqjo7kxrogmr30
     * unique (bank_account_sn)
     * );
     */
    private static final String SQL = "update bank_account " +
            "set balance = ? " +
            "where bank_account_sn = ?";


    @Override
    public void sync() {
        List<Object[]> args = dirty.stream().map(k -> {
            BankAccount bankAccount = data.get(k);
            return new Object[]{
                    bankAccount.getBalance(),
                    bankAccount.getAccountSN(),
            };
        }).collect(Collectors.toList());
        dirty.clear();
        jdbcTemplate.batchUpdate(SQL, args);
    }
}
