package net.impulse.daemon.packets;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.Channel;
import net.impulse.lib.network.protocoll.packet.IPacket;

import java.io.IOException;

public class PacketHandshake implements IPacket{

    @Override
    public void write( ByteBufOutputStream byteBuf ){

    }

    @Override
    public void read( ByteBufInputStream byteBuf ){

    }

    @Override
    public IPacket handle( Channel channel ){
        return null;
    }

    public enum Type{
        WRAPPER, SPIGOT, BUNGEECORD;
    }

}
