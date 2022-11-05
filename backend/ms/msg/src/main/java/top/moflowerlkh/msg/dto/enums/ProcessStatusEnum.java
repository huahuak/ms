package top.moflowerlkh.msg.dto.enums;


import lombok.Getter;

@Getter
public enum ProcessStatusEnum {

    DOING(0, "正在处理"),
    SUCCESS(1, "请求成功"),
    FAILED(2, "请求失败"),
    FINISH(3, "处理完成"),
    FILED_ILLEGAL(4, "字段非法"),
    ;

    int id;
    String description;

    ProcessStatusEnum(int id, String description) {
        this.id = id;
        this.description = description;
    }
}
