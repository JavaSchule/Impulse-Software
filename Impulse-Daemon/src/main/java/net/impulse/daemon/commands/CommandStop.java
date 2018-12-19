package net.impulse.daemon.commands;

import net.impulse.daemon.ImpulseDaemon;
import net.impulse.lib.command.CommandExecutor;

/**
 * @author Mike
 * Command to stop the current service
 */
public class CommandStop implements CommandExecutor{
    public void execute( String[] cmd ){
        if ( cmd[0].equalsIgnoreCase( "stop" ) ){
            ImpulseDaemon.instance.stop();
        }
    }

    /**
     * Get a short description of the command
     * @return
     */
    public String getUsage(){
        return "Stop - Close all Tasks";
    }
}
