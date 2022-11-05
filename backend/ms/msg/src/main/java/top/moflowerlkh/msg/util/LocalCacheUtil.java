package top.moflowerlkh.msg.util;

import java.util.HashMap;
import java.util.function.Function;

public class LocalCacheUtil implements genericCache<Object, Object> {
    private final HashMap<Object, Object> hashMap = new HashMap<>();

    @Override
    public Object getOrElseSet(Object o, Function<Object, Object> f) {
        if (hashMap.containsKey(o)) {
            return load(o);
        } else {
            Object result = f.apply(o);
            store(o, result);
            return result;
        }
    }

    @Override
    public Object store(Object k, Object v) {
        return hashMap.put(k, v);
    }

    @Override
    public Object load(Object k) {
        return hashMap.get(k);
    }
}
