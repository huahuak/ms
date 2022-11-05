package top.moflowerlkh.msg.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.moflowerlkh.msg.util.LocalCacheUtil;
import top.moflowerlkh.msg.util.genericCache;

@Configuration
public class CacheConfig {
    @Autowired
    private ConfigurableBeanFactory configurableBeanFactory;

    @Bean("localCache")
    public genericCache<Object, Object> cacheInterface() {
        return new LocalCacheUtil();
    }

    private static final String GOODS_CACHE = "goodsCache";
    private static final String BANK_ACCOUNT_CACHE = "bankAccountCache";
}
