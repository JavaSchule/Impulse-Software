package net.impulse.lib.network.protocoll.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.EmptyByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import net.impulse.lib.network.protocoll.packet.IPacket;
import net.impulse.lib.network.protocoll.registry.PacketRegistry;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder{

    protected void decode( final ChannelHandlerContext ctx, final ByteBuf byteBuf, final List<Object> output ) throws Exception{

        if ( byteBuf instanceof EmptyByteBuf ) return;

        final int id = byteBuf.readInt();
        final IPacket packet = PacketRegistry.getPacketById( id, PacketRegistry.PacketDirection.IN );
        if ( packet == null )
            throw new NullPointerException( "Could not find packet by id " + id + "!" );
        packet.read( new ByteBufInputStream( byteBuf ) );
        output.add( packet );
    }
}
