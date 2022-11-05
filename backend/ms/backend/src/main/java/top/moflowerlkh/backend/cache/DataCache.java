package top.moflowerlkh.backend.cache;

import top.moflowerlkh.msg.dao.po.BasePO;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

public abstract class DataCache<T extends BasePO> {
    protected final Map<Long, T> data;

    protected final Set<Long> dirty;

    public DataCache() {
        // 100 property
        data = new HashMap<>(100);
        dirty = new HashSet<>(100);
    }

    abstract public void load();

    abstract protected T select(long id);

    public Optional<T> find(long id) {
        T t = data.get(id);
        if (t == null) {
            t = select(id);
        }
        return Optional.ofNullable(t);
    }

    abstract public void save(T t);


    abstract public void sync();

    public void gc() {
        sync();
        dirty.forEach(data::remove);
        dirty.clear();
    }
}
