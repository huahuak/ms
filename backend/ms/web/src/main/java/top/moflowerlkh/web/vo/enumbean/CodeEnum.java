package top.moflowerlkh.web.vo.enumbean;

public enum CodeEnum {

    SUCCESS(200, "success"),
    FAILED(400, "failed"),
    ;

    int code;
    String desc;

    CodeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
