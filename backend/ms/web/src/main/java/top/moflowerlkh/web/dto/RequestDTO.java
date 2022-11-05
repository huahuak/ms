package top.moflowerlkh.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import top.moflowerlkh.msg.dto.RequestMsgDTO;
import top.moflowerlkh.web.common.Const;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
@Slf4j
@ApiModel
public class RequestDTO extends baseDTO {

    @JsonProperty(Const.USER_ID)
    @NotBlank(message = "userID can't empty")
    @ApiModelProperty(value = "用户标识，必须是 long 类型", required = true)
    private String userID;

    @JsonProperty(Const.GOODS_ID)
    @NotBlank(message = "goodsID can't empty")
    @ApiModelProperty(value = "商品标识，必须是 long 类型", required = true)
    private String goodsID;

    @JsonProperty(Const.BANK_ACCOUNT_ID)
    @NotBlank(message = "bankAccountID can't empty")
    @ApiModelProperty(value = "银行账号，必须是 long 类型", required = true)
    private String bankAccountID;

    @JsonProperty(Const.GOODS_ORDER_AMOUNT)
    @NotEmpty(message = "goodsOrderAmount can't empty")
    @ApiModelProperty(value = "购买数量, 必须大于 0，且为整数", required = true)
    private Long goodsOrderAmount;

    public RequestMsgDTO requestMsgDTO() {
        RequestMsgDTO ret = RequestMsgDTO.builder()
                .goodsID(Long.parseLong(getGoodsID()))
                .accountID(Long.parseLong(getBankAccountID()))
                .goodsOrderAmount(getGoodsOrderAmount())
                .build();
        ret.setRequestID(getRequestID());
        return ret;
    }
}
