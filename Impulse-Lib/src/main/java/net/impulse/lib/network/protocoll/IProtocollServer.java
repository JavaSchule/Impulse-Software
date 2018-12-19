package net.impulse.lib.network.protocoll;

import io.netty.channel.Channel;

import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

public interface IProtocollServer{

    void bind( ExecutorService executorService, Runnable runnable, Consumer<Channel> init );

    void close();

}
