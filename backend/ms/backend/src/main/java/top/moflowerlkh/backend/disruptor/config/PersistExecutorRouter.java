package top.moflowerlkh.backend.disruptor.config;

import org.springframework.stereotype.Component;
import top.moflowerlkh.backend.disruptor.persist.PersistEventProducer;
import top.moflowerlkh.msg.dao.po.BasePO;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class PersistExecutorRouter {
    private Map<Class<? extends BasePO>, PersistEventProducer<? extends BasePO>> routerMap;

    public PersistExecutorRouter() {
        //todo 2 property
        routerMap = new HashMap<>(2);
    }


    public Optional<PersistEventProducer> getExecutor(Class clazz) {
        return Optional.ofNullable(routerMap.get(clazz));
    }

    public void registerExecutor(PersistEventProducer<? extends BasePO> producer) {
        routerMap.put(producer.getTargetClass(), producer);
    }
}
