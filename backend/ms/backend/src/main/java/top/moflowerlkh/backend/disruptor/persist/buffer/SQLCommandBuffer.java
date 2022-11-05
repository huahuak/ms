package top.moflowerlkh.backend.disruptor.persist.buffer;

import org.springframework.jdbc.core.JdbcTemplate;
import top.moflowerlkh.msg.dao.po.BasePO;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public abstract class SQLCommandBuffer<T extends BasePO> {
    protected JdbcTemplate jdbcTemplate;
    protected final List<Object[]> argsBuffer;
    protected final int capacity;
    protected final ReentrantLock mutex;

    public SQLCommandBuffer(JdbcTemplate jdbcTemplate, int capacity) {
        this.jdbcTemplate = jdbcTemplate;
        this.argsBuffer = new ArrayList<>(capacity);
        this.capacity = capacity;
        this.mutex = new ReentrantLock();
    }

    protected abstract void convertAndStore(T t);

    public void save(T t) {
        try {
            mutex.lock();
            if (argsBuffer.size() >= capacity) {
                executeAndFlush();
            }
            convertAndStore(t);
        } finally {
            mutex.unlock();
        }
    }

    abstract public void executeAndFlush();
}
