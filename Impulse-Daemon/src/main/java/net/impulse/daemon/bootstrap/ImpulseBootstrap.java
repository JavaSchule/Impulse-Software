package net.impulse.daemon.bootstrap;

import net.impulse.daemon.ImpulseDaemon;

/**
 * @author Mike
 */
public class ImpulseBootstrap{

    /**
     * Create a new instance of the impulse bootstrap to load the bytecode
     * @param args
     */
    public static void main( String[] args ){
        new ImpulseBootstrap();
    }

    /**
     * Load the start logic from {@link ImpulseDaemon}
     */
    private ImpulseBootstrap(){
        final ImpulseDaemon impulseDaemon = new ImpulseDaemon();
        impulseDaemon.start();
    }
}
