package top.moflowerlkh.msg.dto;

import com.google.gson.Gson;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import top.moflowerlkh.msg.dto.enums.ProcessStatusEnum;

@Getter
@Setter
@Builder
public class ResponseMsgDTO extends BaseDTO {
    private int id;
    private String desc;
    private String reason;

    private ProcessStatusEnum status;

    public String toJson() {
        this.setId(status.getId());
        this.setDesc(status.getDescription());
        return new Gson().toJson(this);
    }
}
