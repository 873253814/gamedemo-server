package base.packet;

import base.annotation.Packet;

public abstract class AbstractPacket {

    private short commandId;

    private long createTime = System.currentTimeMillis();

    public AbstractPacket() {
        Packet annotation = this.getClass().getAnnotation(Packet.class);
        if (annotation != null) {
            this.setCommandId(annotation.commandId()[0]);
        }
    }

    public short getCommandId() {
        return commandId;
    }

    public void setCommandId(short commandId) {
        this.commandId = commandId;
    }
}
