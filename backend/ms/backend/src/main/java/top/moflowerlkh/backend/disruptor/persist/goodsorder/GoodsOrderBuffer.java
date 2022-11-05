package top.moflowerlkh.backend.disruptor.persist.goodsorder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import top.moflowerlkh.backend.disruptor.persist.buffer.SQLCommandBuffer;
import top.moflowerlkh.msg.dao.po.goods.GoodsOrder;

import java.util.Date;

@Slf4j
@Component
public class GoodsOrderBuffer extends SQLCommandBuffer<GoodsOrder> {
    /**
     * -- auto-generated definition
     * create table goods_order
     * (
     * id              bigint       not null  primary key,
     * create_time     datetime(6)  not null,
     * modify_time     datetime(6)  not null,
     * bank_account_sn bigint       not null,
     * goods_id        bigint       not null,
     * goods_price     bigint       not null,
     * order_reuslt    varchar(255) not null
     * );
     */
    private static final String SQLStr = "insert into " +
            "goods_order(id, create_time, modify_time, bank_account_sn, goods_id, goods_price, order_reuslt)" +
            "values(null, ?, ?, ?, ?, ?, ?)";

    // todo 500 property
    public GoodsOrderBuffer(JdbcTemplate jdbcTemplate, @Value("1000") int capacity) {
        super(jdbcTemplate, capacity);
    }


    @Override
    protected void convertAndStore(GoodsOrder goodsOrder) {
        Date date = new Date();
        argsBuffer.add(new Object[]{
                date,
                date,
                goodsOrder.getBankAccountSN(),
                goodsOrder.getGoodsID(),
                goodsOrder.getGoodsPrice(),
                goodsOrder.getOrderResultEnum().name()
        });
    }

    @Override
    public void executeAndFlush() {
        try {
            mutex.lock();
            if (argsBuffer.size() > 0) {
                log.info("update goods order record -> {}", argsBuffer.size());
            }
            jdbcTemplate.batchUpdate(SQLStr, argsBuffer);
            argsBuffer.clear();
        } finally {
            mutex.unlock();
        }
    }
}
