package base.wrapper;

import java.lang.reflect.Method;

public class PacketHandlerWrapper {


    private Object target;

    private Method method;

    public static PacketHandlerWrapper valueOf(Object target, Method method) {
        PacketHandlerWrapper packetHandlerWrapper = new PacketHandlerWrapper();
        packetHandlerWrapper.target = target;
        packetHandlerWrapper.method = method;
        packetHandlerWrapper.method.setAccessible(true);
        return packetHandlerWrapper;
    }

    public void invoke(Object... args) throws Exception {
        method.invoke(target, args);
    }
}
