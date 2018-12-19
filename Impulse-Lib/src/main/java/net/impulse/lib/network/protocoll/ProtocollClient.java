package net.impulse.lib.network.protocoll;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.util.function.Consumer;

public class ProtocollClient implements IProtocollClient{

    private EventLoopGroup workerGroup;

    private final ConnectableAdress connectableAdress;

    private final boolean EPOLL = Epoll.isAvailable();

    public ProtocollClient( ConnectableAdress connectableAdress ){
        this.connectableAdress = connectableAdress;
    }


    @Override
    public Channel bind( Consumer<Channel> init ){
        this.workerGroup = this.EPOLL ? new EpollEventLoopGroup() : new NioEventLoopGroup();

        final ChannelFuture future = new Bootstrap()
                .group( this.workerGroup )
                .channel( this.EPOLL ? EpollSocketChannel.class : NioSocketChannel.class )
                .handler( new ChannelInitializer<Channel>(){
                    @Override
                    protected void initChannel( Channel channel ) throws Exception{
                        init.accept( channel );
                    }
                } ).connect( new InetSocketAddress( this.connectableAdress.getHost(), this.connectableAdress.getPort() ) ).syncUninterruptibly();
        return future.channel();
    }

    @Override
    public void close(){
        workerGroup.shutdownGracefully();
    }
}
