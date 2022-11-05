package top.moflowerlkh.msg.util;

import java.util.function.Function;

public interface genericCache<K, V> {
    V getOrElseSet(K k, Function<K, V> f);

    V store(K k, V v);

    V load(K k);
}
