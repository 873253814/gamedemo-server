import base.annotation.PacketHandler;
import base.wrapper.PacketHandlerWrapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.naming.InitialContext;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component
public class PacketHandlerService implements InitializingBean, ApplicationContextAware {

    private Map<Class<?>, PacketHandlerWrapper> packetHandlerWrapperMap;

    private ApplicationContext ctx;

    @Override
    public  void setApplicationContext(ApplicationContext var1) {
        ctx = var1;
    }

    @Override
    public void afterPropertiesSet() {
        Map<String, Object> beansWithAnnotation = ctx.getBeansWithAnnotation(PacketHandler.class);
        Map<Class<?>, PacketHandlerWrapper> packetMap = new HashMap<>();
        for (Object bean : beansWithAnnotation.values()) {
            registerPacketHandler(packetMap, bean, bean.getClass());
        }
        packetHandlerWrapperMap = packetMap;
    }

    public void registerPacketHandler(Map<Class<?>, PacketHandlerWrapper> packetHandlerWrapperMap, Object handler, Class<?> clazz) {
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            Class<?>[] parameterTypes = method.getParameterTypes();
            packetHandlerWrapperMap.put(parameterTypes[1], PacketHandlerWrapper.valueOf(handler, method));
        }
    }
}
