package net.impulse.daemon.handler;

import net.impulse.daemon.ImpulseDaemon;
import net.impulse.lib.utils.Template;

public class WrapperHandler{

    /**
     * Start a server with a {@link Template}
     * @param template
     */
    public void startServer( Template template ){

    }

    /**
     * Start all server from cache
     */
    public void startAllServer(){
        ImpulseDaemon.instance.getTemplateHandler().getTemplateList().forEach( this::startServer );
    }
}
