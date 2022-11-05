package top.moflowerlkh.backend.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextUtil implements ApplicationContextAware {
    private static ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtil.ctx = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        return (T) ApplicationContextUtil.ctx.getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return (T) ApplicationContextUtil.ctx.getBean(name, clazz);
    }
}
