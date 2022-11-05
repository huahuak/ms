package top.moflowerlkh.msg.common.enumbean;

import lombok.Getter;

@Getter
public class BooleanResult {
    private final boolean success;
    private final String msg;

    public BooleanResult(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public static BooleanResult success(String msg) {
        return new BooleanResult(true, msg);
    }

    public static BooleanResult failed(String msg) {
        return new BooleanResult(false, msg);
    }
}
