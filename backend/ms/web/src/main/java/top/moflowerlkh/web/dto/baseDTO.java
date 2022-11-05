package top.moflowerlkh.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import top.moflowerlkh.web.common.Const;

@Getter
@Setter
@ToString
public class baseDTO {
    @JsonProperty(Const.TRACE_ID)
    private String requestID;
}
