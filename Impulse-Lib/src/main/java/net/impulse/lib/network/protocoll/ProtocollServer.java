package net.impulse.lib.network.protocoll;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

public class ProtocollServer implements IProtocollServer{

    private final ConnectableAdress connectableAdress;

    private final boolean EPOLL = Epoll.isAvailable();

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    public ProtocollServer( ConnectableAdress connectableAdress ){
        this.connectableAdress = connectableAdress;
    }

    @Override
    public void bind( ExecutorService executorService, Runnable runnable, Consumer<Channel> init ){
        executorService.execute( () -> {
            this.bossGroup = this.EPOLL ? new EpollEventLoopGroup() : new NioEventLoopGroup();
            this.workerGroup = this.EPOLL ? new EpollEventLoopGroup() : new NioEventLoopGroup();

            final ChannelFuture future = new ServerBootstrap().group( this.bossGroup, this.workerGroup ).channel( this.EPOLL ? EpollServerSocketChannel.class : NioServerSocketChannel.class ).childHandler( new ChannelInitializer<Channel>(){
                @Override
                protected void initChannel( Channel channel ) throws Exception{
                    init.accept( channel );
                }
            } ).bind( this.connectableAdress.getPort() ).syncUninterruptibly();
            runnable.run();
            future.channel().closeFuture().syncUninterruptibly();
        } );
    }

    @Override
    public void close(){
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
