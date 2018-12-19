package net.byteexception.impulse;

import io.netty.channel.Channel;
import lombok.Getter;
import net.byteexception.impulse.commands.CommandStop;
import net.byteexception.impulse.setup.ConfigService;
import net.impulse.lib.ImpulseCloud;
import net.impulse.lib.Version;
import net.impulse.lib.command.CommandProvider;
import net.impulse.lib.logging.LoggingProvider;
import net.impulse.lib.network.protocoll.ConnectableAdress;
import net.impulse.lib.network.protocoll.ProtocollClient;
import org.apache.log4j.Level;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImpulseWrapper implements ImpulseCloud{

    @Getter
    private LoggingProvider loggingProvider;

    @Getter
    private final CommandProvider commandProvider = new CommandProvider();

    @Getter
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @Getter
    private ProtocollClient protocollClient;
    /**
     * Instance of {@link Version}
     * TODO: Automatic version controller
     */
    @Getter
    private final Version version = new Version( "0.1" );

    @Getter
    private final ConfigService configService = new ConfigService();

    public void start(){

        this.loggingProvider = new LoggingProvider();
        this.loggingProvider.setPrority( Level.INFO );
        this.loggingProvider.log( "_____ _______  _____  _     _        _______ _______ _______         _____  _     _ ______ " );
        this.loggingProvider.log( "  |   |  |  | |_____] |     | |      |______ |______ |       |      |     | |     | |     \\" );
        this.loggingProvider.log( "__|__ |  |  | |       |_____| |_____ ______| |______ |_____  |_____ |_____| |_____| |_____/" );
        this.loggingProvider.log( String.format( "                                             Version %s by ByteExceptionYT        ", getVersion().getVersion() ) );

        registerCommands();
        this.commandProvider.runTask( executorService );

        this.configService.createDefaultConfig();

        try{
            this.protocollClient = new ProtocollClient( new ConnectableAdress( configService.getPort(), configService.getHost() ) );
        } catch (IOException e){
            e.printStackTrace();
        }

        registerPackets();

        Channel channel = this.protocollClient.bind( client ->{

        } );
    }

    private void registerPackets(){

    }

    private void registerCommands(){
        commandProvider.registerCommand( new CommandStop() );
    }

    public void stop(){

        System.exit( 0 );
    }

    public void reload(){

    }

}
