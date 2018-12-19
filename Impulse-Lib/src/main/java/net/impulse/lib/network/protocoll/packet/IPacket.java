package net.impulse.lib.network.protocoll.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.Channel;

import java.io.IOException;

public interface IPacket{

    default void read(final ByteBufInputStream byteBuf) throws IOException{
    }

    default void write(final ByteBufOutputStream byteBuf) throws IOException {
    }

    default IPacket handle(final Channel channel) {
        return null;
    }

}
