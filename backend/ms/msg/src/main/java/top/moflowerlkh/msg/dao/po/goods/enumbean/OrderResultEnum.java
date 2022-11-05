package top.moflowerlkh.msg.dao.po.goods.enumbean;

import lombok.Getter;

/**
 * @see top.moflowerlkh.msg.dto.enums.ProcessStatusEnum
 * this show process status reason
 */
@Getter
public enum OrderResultEnum {
    SUCCESS(1, "订单完成"),
    FAILED(2, "订单失败"),
    BALANCE_NOT_ENOUGH(3, "余额不足"),
    GOODS_NOT_ENOUGH(4, "商品库存不足"),
    MAX_SHOPPING(5, "限制购买"),
    GOODS_NOT_FOUND(6, "商品未找到"),
    BANK_ACCOUNT_NOT_FOUND(7, "账号未找到"),
    ;

    int id;
    String description;

    OrderResultEnum(int id, String description) {
        this.id = id;
        this.description = description;
    }
}
