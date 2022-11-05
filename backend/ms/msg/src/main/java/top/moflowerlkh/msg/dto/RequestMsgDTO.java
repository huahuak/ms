package top.moflowerlkh.msg.dto;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestMsgDTO extends BaseDTO {
    private long goodsID;
    private long goodsOrderAmount;
    private long accountID;
}
