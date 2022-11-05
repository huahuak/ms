package top.moflowerlkh.backend.disruptor.persist;

import lombok.Getter;
import top.moflowerlkh.backend.disruptor.ArgEventInterface;
import top.moflowerlkh.msg.dao.po.BasePO;

@Getter
public class PersistEvent<T extends BasePO> implements ArgEventInterface<T> {
    private T arg;

    @Override
    public void setArg(T args) {
        this.arg = args;
    }
}
