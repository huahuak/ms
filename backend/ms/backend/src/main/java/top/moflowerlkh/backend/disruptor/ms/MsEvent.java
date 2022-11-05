package top.moflowerlkh.backend.disruptor.ms;

import lombok.Getter;
import top.moflowerlkh.backend.disruptor.ArgEventInterface;
import top.moflowerlkh.backend.entity.enums.GoodsOrderStatusEnum;
import top.moflowerlkh.backend.service.GoodsOrderCommand;
import top.moflowerlkh.msg.dto.RequestMsgDTO;

@Getter
public class MsEvent extends GoodsOrderCommand implements ArgEventInterface<RequestMsgDTO> {

    @Override
    public void setArg(RequestMsgDTO requestMsgDTO) {
        setRequestMsgDTO(requestMsgDTO);
    }

    public void clear() {
        setRequestMsgDTO(null);
        setNewOrder(null);
        setUserOperation(null);
        setBankOperation(null);
        setNowGoodsOrderStatus(GoodsOrderStatusEnum.GOODS_ORDER_START);
    }

}
