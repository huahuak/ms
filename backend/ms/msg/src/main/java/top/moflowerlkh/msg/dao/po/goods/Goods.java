package top.moflowerlkh.msg.dao.po.goods;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import top.moflowerlkh.msg.dao.po.BasePO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
@Entity
@Table(name = "goods")
public class Goods extends BasePO {
    /**
     * 开始时间
     */
    @Column(nullable = false)
    private Timestamp startTime;

    /**
     * 每人限购额度
     */
    @Column(name = "one_max_amount", nullable = false)
    private long oneMaxAmount;

    /**
     * 商品库存
     */
    @Column(name = "goods_amount", nullable = false)
    private long goodsAmount;

    /**
     * 商品单价
     */
    @Column(name = "goods_price", nullable = false)
    private long price;

    /**
     * 银行收款账号
     */
    @Column(name = "goods_bank_account", nullable = false)
    private long bankAccountSN;

    public void setGoodsAmount(long newAmount) {
        if (newAmount < 0) {
            String err = "newAmount must larger than zero";
            throw new RuntimeException(err);
        }
        this.goodsAmount = newAmount;
    }

    public static Goods byID(long goodsID) {
        Goods goods = new Goods();
        goods.setId(goodsID);
        return goods;
    }
}
