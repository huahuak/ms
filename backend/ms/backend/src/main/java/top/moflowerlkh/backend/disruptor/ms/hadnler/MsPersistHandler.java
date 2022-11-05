package top.moflowerlkh.backend.disruptor.ms.hadnler;

import com.lmax.disruptor.EventHandler;
import lombok.Setter;
import org.apache.skywalking.apm.toolkit.trace.Tag;
import org.apache.skywalking.apm.toolkit.trace.Trace;
import org.hibernate.boot.jaxb.internal.FileXmlSource;
import top.moflowerlkh.backend.disruptor.config.PersistExecutorRouter;
import top.moflowerlkh.backend.disruptor.ms.MsEvent;
import top.moflowerlkh.msg.dao.po.BasePO;

import java.lang.reflect.Field;

@Setter
public class MsPersistHandler implements EventHandler<MsEvent> {
    private PersistExecutorRouter persistExecutorRouter;

    @Override
    @Trace
    @Tag(key = "method", value = "MsPersistHandler.onEvent")
    public void onEvent(MsEvent event, long sequence, boolean endOfBatch) throws Exception {
        for (Field field : event.getClass().getSuperclass().getDeclaredFields()) {
            if (BasePO.class.isAssignableFrom(field.getType())) {
                field.setAccessible(true);
                Object obj = field.get(event);
                if (obj == null) {
                    continue;
                }
                persistExecutorRouter.getExecutor(field.getType())
                        .ifPresent(exe -> exe.publishEvent(obj));
            }
        }
    }
}
