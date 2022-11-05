package top.moflowerlkh.web.vo;

import lombok.Builder;
import lombok.Getter;
import org.slf4j.MDC;
import top.moflowerlkh.web.common.Const;
import top.moflowerlkh.web.vo.enumbean.CodeEnum;

import java.util.Date;

@Builder
@Getter
public class UIResult {
    // 返回 code
    private CodeEnum code;
    // 返回 请求 ID
    private String RequestID;
    // 提示消息
    private String msg;
    // payload
    private Object data;
    // response date
    private String responseTime;

    public static UIResult Success(String msg, Object data) {
        return getBuilder()
                .code(CodeEnum.SUCCESS)
                .msg(msg)
                .data(data)
                .build();
    }

    public static UIResult failed(String msg) {
        return getBuilder()
                .code(CodeEnum.FAILED)
                .msg(msg)
                .build();

    }

    private static UIResultBuilder getBuilder() {
        return UIResult.builder()
                .RequestID(MDC.get(Const.TRACE_ID))
                .responseTime(new Date().toString());
    }
}
