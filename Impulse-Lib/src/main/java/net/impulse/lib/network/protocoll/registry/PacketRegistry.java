package net.impulse.lib.network.protocoll.registry;

import lombok.Getter;
import net.impulse.lib.network.protocoll.packet.IPacket;

import java.util.HashMap;
import java.util.Map;

public class PacketRegistry{

    public static IPacket getPacketById( final int id, final PacketDirection direction ) throws IllegalAccessException, InstantiationException{
        return direction.getPackets().get( id ).newInstance();
    }

    public static int getIdByPacket( final IPacket packet, final PacketDirection direction ){
        return direction.getPackets().entrySet().stream().filter( entry -> entry.getValue() == packet.getClass() ).map( Map.Entry::getKey ).findFirst().orElse( -1 );
    }

    public enum PacketDirection{
        IN,
        OUT;

        @Getter
        private final HashMap<Integer, Class<? extends IPacket>> packets = new HashMap<>();

        public void addPacket( final int id, final Class<? extends IPacket> packet ){
            this.packets.put( id, packet );
        }
    }
}

