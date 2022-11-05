package top.moflowerlkh.msg.dao.po.acount.enumbean;


public enum BankOperationTypeEnum {
    IN(1, "进账"),
    OUT(2, "出账"),
    ;

    int id;
    String description;

    BankOperationTypeEnum(int id, String description) {
        this.id = id;
        this.description = description;
    }
}
