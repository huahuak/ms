package top.moflowerlkh.backend.entity.enums;


public enum GoodsOrderStatusEnum {
    GOODS_ORDER_START {
        @Override
        public GoodsOrderStatusEnum convertNextStatus() {
            return AMOUNT_DECREMENT;
        }
    },

    AMOUNT_DECREMENT {
        @Override
        public GoodsOrderStatusEnum convertNextStatus() {
            return BANK_ACCOUNT_TRANSFER_FINISH;
        }
    },

    BANK_ACCOUNT_TRANSFER_FINISH {
        @Override
        public GoodsOrderStatusEnum convertNextStatus() {
            return GOODS_ORDER_FINISH;
        }
    },

    GOODS_ORDER_FINISH,
    ;

    public GoodsOrderStatusEnum convertNextStatus() {
        throw new RuntimeException("unsupported operation!");
    }
}
