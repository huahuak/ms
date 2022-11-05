package top.moflowerlkh.backend.service;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import top.moflowerlkh.backend.cache.UserOrderRecordCache;
import top.moflowerlkh.backend.cache.impl.BankAccountCache;
import top.moflowerlkh.backend.cache.impl.GoodsCache;
import top.moflowerlkh.backend.entity.enums.GoodsOrderStatusEnum;
import top.moflowerlkh.msg.dao.po.acount.BankAccount;
import top.moflowerlkh.msg.dao.po.acount.BankOperationRecord;
import top.moflowerlkh.msg.dao.po.goods.Goods;
import top.moflowerlkh.msg.dao.po.goods.GoodsOrder;
import top.moflowerlkh.msg.dao.po.goods.enumbean.OrderResultEnum;
import top.moflowerlkh.msg.dto.RequestMsgDTO;
import top.moflowerlkh.msg.dto.enums.ProcessStatusEnum;

import java.util.Optional;

@Slf4j
@Setter
@Getter
public class GoodsOrderCommand implements Command {
    // -- self -- //
    private GoodsOrderStatusEnum nowGoodsOrderStatus;

    private RequestMsgDTO requestMsgDTO;

    // -- response -- //
    private ProcessStatusEnum processStatusEnum;

    private OrderResultEnum orderResultEnum;

    // -- persist -- //
    private GoodsOrder newOrder;

    private BankOperationRecord userOperation;

    private BankOperationRecord bankOperation;

    // -- spring -- //
    private GoodsCache goodsCache;

    private BankAccountCache bankAccountCache;

    private UserOrderRecordCache userOrderRecordCache;

    @Override
    public boolean checkHistoryRecord() {
        if (!userOrderRecordCache.checkOrderOK(requestMsgDTO)){
            orderResultEnum = OrderResultEnum.MAX_SHOPPING;
            return false;
        }
        return true;
    }

    @Override
    public void rollbackHistoryRecord() {
        userOrderRecordCache.rollBackRecordOrder(requestMsgDTO);
    }

    @Override
    public void makeGoodsOrder() {
        Goods goods = goodsCache.find(requestMsgDTO.getGoodsID()).orElseThrow(
                () -> new RuntimeException("no goods -> " + requestMsgDTO.getRequestID()));
        long price = requestMsgDTO.getGoodsOrderAmount() * goods.getPrice();
        newOrder = GoodsOrder.New(goods.getId(), price, requestMsgDTO.getAccountID(), OrderResultEnum.SUCCESS);
    }


    @Override
    public boolean transferBankBalance() {
        Goods goods = goodsCache.find(requestMsgDTO.getGoodsID()).orElseThrow(
                () -> new RuntimeException("no goods -> " + requestMsgDTO.getRequestID()));

        Optional<BankAccount> op = bankAccountCache.find(requestMsgDTO.getAccountID());
        Optional<BankAccount> op2 = bankAccountCache.find(goods.getBankAccountSN());
        if (!op.isPresent() || !op2.isPresent()) {
            orderResultEnum = OrderResultEnum.BANK_ACCOUNT_NOT_FOUND;
            return false;
        }

        BankAccount user = op.get();
        BankAccount bank = op2.get();


        long price = requestMsgDTO.getGoodsOrderAmount() * goods.getPrice();
        long userBalance = user.getBalance() - price;
        if (userBalance < 0) {
            orderResultEnum = OrderResultEnum.BALANCE_NOT_ENOUGH;
            return false;
        }
        long bankBalance = bank.getBalance() + price;

        // generate bank account operation record
        if (price > 0) {
            userOperation = BankOperationRecord.OUT(user, price);
            bankOperation = BankOperationRecord.IN(bank, price);
        } else if (price < 0) {
            userOperation = BankOperationRecord.IN(user, price * -1);
            bankOperation = BankOperationRecord.OUT(bank, price * -1);
        }

        user.setBalance(userBalance);
        bank.setBalance(bankBalance);

        bankAccountCache.save(user);
        bankAccountCache.save(bank);

        return true;
    }


    @Override
    public boolean decreaseGoodsAmount() {
        Optional<Goods> op = goodsCache.find(requestMsgDTO.getGoodsID());
        if (!op.isPresent()) {
            orderResultEnum = OrderResultEnum.GOODS_NOT_FOUND;
            return false;
        }
        Goods goods = op.get();

        long result = goods.getGoodsAmount() - requestMsgDTO.getGoodsOrderAmount();
        if (result < 0) {
            orderResultEnum = OrderResultEnum.GOODS_NOT_ENOUGH;
            return false;
        }

        goods.setGoodsAmount(result);
        goodsCache.save(goods);
        return true;
    }

    @Override
    public void rollbackGoodsAmount() {
        rollbackHistoryRecord();
        goodsCache.find(requestMsgDTO.getGoodsID()).ifPresent(goods -> {
            long result = goods.getGoodsAmount() + requestMsgDTO.getGoodsOrderAmount();
            goods.setGoodsAmount(result);
            goodsCache.save(goods);
        });
    }


    @Override
    public void makeResponse() {
        switch (nowGoodsOrderStatus) {
            case GOODS_ORDER_FINISH:
                processStatusEnum = ProcessStatusEnum.SUCCESS;
                orderResultEnum = OrderResultEnum.SUCCESS;
                break;
            default: {
                processStatusEnum = ProcessStatusEnum.FAILED;
                if (orderResultEnum == null) {
                    orderResultEnum = OrderResultEnum.FAILED;
                }
            }
        }
    }

    @Override
    public void next() {
        nowGoodsOrderStatus = nowGoodsOrderStatus.convertNextStatus();
    }

    @Override
    public void exceptionHandler(Exception e) {
        log.warn("end status -> {}, and goods order found exception -> {}", nowGoodsOrderStatus, e);
    }
}
