package site.wetsion.framework.mitosis.core;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author wetsion
 * @date 2022-05-11 01:42
 */
@Component
public class ComponentTaker implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ComponentTaker.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> tClass) {
        return applicationContext.getBean(tClass);
    }

    public static <T> T getBean(String name, Class<T> type) {
        return applicationContext.getBean(name, type);
    }

    public static <T> T getBean(String name) throws BeansException {
        @SuppressWarnings("unchecked")
        T result = (T) applicationContext.getBean(name);
        return result;
    }


    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }
}
