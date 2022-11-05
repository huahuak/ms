package top.moflowerlkh.backend.service;

import org.apache.skywalking.apm.toolkit.trace.Trace;

public interface Command {

    @Trace
    boolean checkHistoryRecord();

    void rollbackHistoryRecord();

    @Trace
    boolean decreaseGoodsAmount();

    void rollbackGoodsAmount();

    @Trace
    boolean transferBankBalance();

    @Trace
    void makeGoodsOrder();

    @Trace
    void makeResponse();

    void next();

    void exceptionHandler(Exception e);

    default void resolve() {
        try {
            /** @see top.moflowerlkh.backend.entity.enums.GoodsOrderStatusEnum.GOODS_ORDER_START */
            if (!checkHistoryRecord()) {
                return;
            }
            next();
            /** @see top.moflowerlkh.backend.entity.enums.GoodsOrderStatusEnum.AMOUNT_DECREMENT */
            if (!decreaseGoodsAmount()) {
                rollbackHistoryRecord();
                return;
            }
            next();
            /** @see top.moflowerlkh.backend.entity.enums.GoodsOrderStatusEnum.BANK_ACCOUNT_TRANSFER_FINISH */
            if (!transferBankBalance()) {
                rollbackGoodsAmount();
                return;
            }
            next();
            /** @see top.moflowerlkh.backend.entity.enums.GoodsOrderStatusEnum.GOODS_ORDER_FINISH */
            makeGoodsOrder();
        } catch (Exception e) {
            exceptionHandler(e);
        } finally {
            makeResponse();
        }
    }

}
