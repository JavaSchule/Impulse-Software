package net.impulse.daemon;

import lombok.Getter;
import net.impulse.daemon.commands.CommandClear;
import net.impulse.daemon.commands.CommandHelp;
import net.impulse.daemon.commands.CommandStop;
import net.impulse.daemon.handler.TemplateHandler;
import net.impulse.daemon.packets.PacketHandshake;
import net.impulse.daemon.screen.ScreenHandler;
import net.impulse.daemon.setup.ConfigService;
import net.impulse.lib.ImpulseCloud;
import net.impulse.lib.Version;
import net.impulse.lib.command.CommandProvider;
import net.impulse.lib.database.MongoConnection;
import net.impulse.lib.logging.LoggingProvider;
import net.impulse.lib.network.protocoll.ConnectableAdress;
import net.impulse.lib.network.protocoll.ProtocollServer;
import net.impulse.lib.network.protocoll.registry.PacketRegistry;
import org.apache.log4j.Level;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Mike
 */
public class ImpulseDaemon implements ImpulseCloud{

    /**
     * Instance of {@link LoggingProvider}
     */
    @Getter
    private LoggingProvider loggingProvider;

    /**
     * Instance of {@link ImpulseCloud}
     */
    public static ImpulseDaemon instance;

    /**
     * Create a new public {@link ExecutorService} instance
     */
    @Getter
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * Instance of {@link CommandProvider}
     */
    @Getter
    private final CommandProvider commandProvider = new CommandProvider();

    /**
     * Instance of {@link ScreenHandler}
     */
    @Getter
    private final ScreenHandler screenHandler = new ScreenHandler();

    /**
     * Instance of {@link ConfigService}
     */
    @Getter
    private final ConfigService configService = new ConfigService();

    /**
     * Instance of {@link Version}
     * TODO: Automatic version controller
     */
    @Getter
    private final Version version = new Version( "0.1" );

    /**
     * Instance of {@link MongoConnection}
     */
    @Getter
    private final MongoConnection mongoConnection = new MongoConnection();

    /**
     * Instance of {@link TemplateHandler}
     */
    @Getter
    private final TemplateHandler templateHandler = new TemplateHandler();

    /**
     * Instance of {@link net.impulse.lib.network.protocoll.IProtocollServer}
     */
    private ProtocollServer protocollServer;

    /**
     * Service start logic
     */
    public void start(){

        instance = this;

        this.loggingProvider = new LoggingProvider();
        this.loggingProvider.setPrority( Level.INFO );
        this.loggingProvider.log( "_____ _______  _____  _     _        _______ _______ _______         _____  _     _ ______ " );
        this.loggingProvider.log( "  |   |  |  | |_____] |     | |      |______ |______ |       |      |     | |     | |     \\" );
        this.loggingProvider.log( "__|__ |  |  | |       |_____| |_____ ______| |______ |_____  |_____ |_____| |_____| |_____/" );
        this.loggingProvider.log( String.format( "                                             Version %s by ByteExceptionYT        ", getVersion().getVersion() ) );


        this.configService.createDefaultConfig();

        registerCommands();

        this.commandProvider.runTask( executorService );

        try{
            this.mongoConnection.connect( configService.getMongoDBHost(), configService.getMongoDBPort(), "cloud" );
        } catch (IOException e){
            e.printStackTrace();
        }

        try{
            this.protocollServer = new ProtocollServer( new ConnectableAdress( configService.getPort(), configService.getHost() ) );
        } catch (Exception ex){
            ex.printStackTrace();
        }

        registerPackets();

        this.templateHandler.loadTemplates();

        this.protocollServer.bind( this.executorService, () -> {

        }, channel -> {

        } );

    }

    /**
     * Register all packets for the server protocoll
     */
    private void registerPackets(){
        PacketRegistry.PacketDirection.OUT.addPacket( 0, PacketHandshake.class );
        PacketRegistry.PacketDirection.IN.addPacket( 0, PacketHandshake.class );
    }

    /**
     * Implements a new {@link net.impulse.lib.command.CommandExecutor} in the command system
     */
    private void registerCommands(){
        this.commandProvider.registerCommand( new CommandStop() );
        this.commandProvider.registerCommand( new CommandHelp() );
        this.commandProvider.registerCommand( new CommandClear() );

    }

    /**
     * Service stop logic
     */
    public void stop(){
        loggingProvider.log( "Starting shutdown sequence" );

        ImpulseDaemon.instance.getExecutorService().shutdownNow();

        loggingProvider.log( "Stopped Impulse Daemon" );
        System.exit( 0 );
    }

    /**
     * Disable and load the methods again
     */
    public void reload(){
        ImpulseDaemon.instance.getExecutorService().shutdownNow();
        start();
    }

}
