package top.moflowerlkh.backend.cache.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import top.moflowerlkh.backend.cache.DataCache;
import top.moflowerlkh.msg.dao.po.goods.Goods;
import top.moflowerlkh.msg.dao.repo.GoodsRepository;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Component
public class GoodsCache extends DataCache<Goods> {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private GoodsRepository goodsRepository;

    @Override
    @PostConstruct
    public void load() {
        /**
         *  @see Goods
         *      create table goods
         *      (
         *          id                 bigint auto_increment primary key,
         *          create_time        datetime(6) not null,
         *          modify_time        datetime(6) not null,
         *          goods_bank_account bigint      not null,
         *          goods_amount       bigint      not null,
         *          one_max_amount     bigint      not null,
         *          goods_price        bigint      not null,
         *          start_time         datetime(6) not null
         *      );
         */
        jdbcTemplate.query("select id, goods_bank_account, goods_amount, one_max_amount, goods_price, " +
                                "create_time, modify_time, start_time from goods",
                        (rs, row) -> {
                            Goods goods = new Goods();
                            goods.setId(rs.getLong(1));
                            goods.setBankAccountSN(rs.getLong(2));
                            goods.setGoodsAmount(rs.getLong(3));
                            goods.setOneMaxAmount(rs.getLong(4));
                            goods.setPrice(rs.getLong(5));
                            goods.setCreateDate(rs.getDate(6));
                            goods.setModifyDate(rs.getDate(7));
                            goods.setStartTime(rs.getTimestamp(8));
                            return goods;
                        })
                .forEach(item -> {
                    data.put(item.getId(), item);
                });
    }

    @Override
    protected Goods select(long id) {
        Optional<Goods> goods = goodsRepository.findById(id);
        if (!goods.isPresent()) {
            return null;
        }
        data.put(id, goods.get());
        return data.get(id);
    }

    @Override
    public void save(Goods goods) {
        dirty.add(goods.getId());
        data.put(goods.getId(), goods);
    }

    @Override
    public void sync() {
        dirty.forEach(k -> goodsRepository.save(data.get(k)));
        dirty.clear();
    }
}
