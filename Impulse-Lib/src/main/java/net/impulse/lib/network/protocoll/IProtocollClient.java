package net.impulse.lib.network.protocoll;

import io.netty.channel.Channel;

import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

public interface IProtocollClient{

    Channel bind(  Consumer<Channel> init );

    void close();

}
